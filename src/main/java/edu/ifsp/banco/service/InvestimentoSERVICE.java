package edu.ifsp.banco.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Investimento;
import edu.ifsp.banco.modelo.Movimentacoes;
import edu.ifsp.banco.modelo.enums.StatusInvestimento;
import edu.ifsp.banco.modelo.enums.StatusMovimentacao;
import edu.ifsp.banco.modelo.enums.TipoMovimentacao;
import edu.ifsp.banco.persistencia.ConnectionSingleton;
import edu.ifsp.banco.persistencia.ContaDAO;
import edu.ifsp.banco.persistencia.InvestimentoDAO;
import edu.ifsp.banco.persistencia.MovimentacaoDAO;

public class InvestimentoSERVICE {

	public void montarInvestimento(int numeroConta, String tipo, BigDecimal valor) throws Exception {

		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			try {
				ContaDAO contaDAO = new ContaDAO(conn);
				InvestimentoDAO investimentoDAO = new InvestimentoDAO(conn);
				MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO(conn);

				Conta conta = contaDAO.buscarPorNumero(numeroConta);

				if (conta == null) {
					throw new Exception("Conta não encontrada");
				}

				if (valor.compareTo(BigDecimal.ZERO) <= 0) {
					throw new Exception("Valor inválido para investimento");
				}

				if (conta.getSaldo().compareTo(valor) < 0) {
					throw new Exception("Saldo insuficiente");
				}

				BigDecimal saldoAnterior = conta.getSaldo();
				BigDecimal novoSaldo = saldoAnterior.subtract(valor);

				contaDAO.atualizarSaldo(conta.getId(), novoSaldo.doubleValue());
				conta.setSaldo(novoSaldo);

				Investimento inv = new Investimento(0, conta.getId(), tipo, StatusInvestimento.ATIVO, valor,
						new Timestamp(System.currentTimeMillis()), null);

				investimentoDAO.inserir(inv);

				Movimentacoes mov = new Movimentacoes(0, conta.getId(), 0, valor, saldoAnterior, novoSaldo,
						TipoMovimentacao.INVESTIMENTO, new Timestamp(System.currentTimeMillis()),
						"Aplicação em " + tipo, StatusMovimentacao.CONCLUIDA);

				movimentacaoDAO.inserir(mov);

				conn.commit();

			} catch (Exception e) {
				conn.rollback();
				throw new Exception("Erro ao realizar investimento: " + e.getMessage());
			}
		}
	}

	public void encerrarInvestimento(int idInvestimento) throws Exception {

		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			try {
				InvestimentoDAO investimentoDAO = new InvestimentoDAO(conn);
				ContaDAO contaDAO = new ContaDAO(conn);
				MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO(conn);

				Investimento inv = investimentoDAO.buscarPorId(idInvestimento);

				if (inv == null) {
					throw new Exception("Investimento não encontrado");
				}

				if (inv.getStatus() == StatusInvestimento.ENCERRADO) {
					throw new Exception("Investimento já encerrado");
				}

				BigDecimal valorDevolvido = inv.getValorInvestido();

				Conta conta = contaDAO.buscarPorId(inv.getIdConta());

				if (conta == null) {
					throw new Exception("Conta vinculada ao investimento não encontrada");
				}

				BigDecimal saldoAnterior = conta.getSaldo();
				BigDecimal novoSaldo = saldoAnterior.add(valorDevolvido);

				contaDAO.atualizarSaldo(conta.getId(), novoSaldo.doubleValue());
				conta.setSaldo(novoSaldo);

				investimentoDAO.encerrar(idInvestimento, new Timestamp(System.currentTimeMillis()));

				Movimentacoes mov = new Movimentacoes(0, 0, conta.getId(), valorDevolvido, saldoAnterior, novoSaldo,
						TipoMovimentacao.INVESTIMENTO, new Timestamp(System.currentTimeMillis()),
						"Resgate: " + inv.getTipoInvestimento(), StatusMovimentacao.CONCLUIDA);

				movimentacaoDAO.inserir(mov);

				conn.commit();

			} catch (Exception e) {
				conn.rollback();
				throw new Exception("Erro ao encerrar investimento: " + e.getMessage());
			}
		}
	}

	public List<Investimento> listarPorConta(int idConta) throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			InvestimentoDAO investimentoDAO = new InvestimentoDAO(conn);
			return investimentoDAO.listarPorConta(idConta);
		} catch (Exception e) {
			throw new Exception("Erro ao listar investimentos: " + e.getMessage());
		}
	}

	public Investimento buscarPorId(int id) throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			InvestimentoDAO investimentoDAO = new InvestimentoDAO(conn);
			return investimentoDAO.buscarPorId(id);
		} catch (Exception e) {
			throw new Exception("Erro ao buscar investimento: " + e.getMessage());
		}
	}
}