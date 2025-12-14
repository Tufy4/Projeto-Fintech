package edu.ifsp.banco.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Investimento;
import edu.ifsp.banco.modelo.Movimentacoes;
import edu.ifsp.banco.modelo.enums.StatusInvestimento;
import edu.ifsp.banco.modelo.enums.StatusMovimentacao;
import edu.ifsp.banco.modelo.enums.TipoMovimentacao;
import edu.ifsp.banco.persistencia.ContaDAO;
import edu.ifsp.banco.persistencia.InvestimentoDAO;
import edu.ifsp.banco.persistencia.MovimentacaoDAO;
import edu.ifsp.banco.persistencia.DataAccessException;

public class InvestimentoSERVICE {

	private InvestimentoDAO investimentoDAO;
	private ContaDAO contaDAO;
	private MovimentacaoDAO movimentacaoDAO;

	public InvestimentoSERVICE() {
		this.investimentoDAO = new InvestimentoDAO();
		this.contaDAO = new ContaDAO();
		this.movimentacaoDAO = new MovimentacaoDAO();
	}

	public void montarInvestimento(int numeroConta, String tipo, BigDecimal valor) throws DataAccessException {

		Conta conta = contaDAO.buscarPorNumero(numeroConta);

		if (conta == null) {
			throw new DataAccessException("Conta não encontrada");
		}

		if (valor.compareTo(BigDecimal.ZERO) <= 0) {
			throw new DataAccessException("Valor inválido para investimento");
		}

		if (conta.getSaldo().compareTo(valor) < 0) {
			throw new DataAccessException("Saldo insuficiente");
		}

		BigDecimal saldoAnterior = conta.getSaldo();
		BigDecimal novoSaldo = saldoAnterior.subtract(valor);

		contaDAO.atualizarSaldo(conta.getId(), novoSaldo.doubleValue());
		conta.setSaldo(novoSaldo);

		Investimento inv = new Investimento(0, conta.getId(), tipo, StatusInvestimento.ATIVO, valor,
				new Timestamp(System.currentTimeMillis()), null);
		investimentoDAO.inserir(inv);

		Movimentacoes mov = new Movimentacoes(0, conta.getId(), 0, valor, saldoAnterior, novoSaldo,
				TipoMovimentacao.INVESTIMENTO, new Timestamp(System.currentTimeMillis()), "Aplicação em " + tipo,
				StatusMovimentacao.CONCLUIDA);

		movimentacaoDAO.inserir(mov);
	}

	public void encerrarInvestimento(int idInvestimento) throws DataAccessException {

		Investimento inv = investimentoDAO.buscarPorId(idInvestimento);

		if (inv == null) {
			throw new DataAccessException("Investimento não encontrado");
		}

		if (inv.getStatus() == StatusInvestimento.ENCERRADO) {
			throw new DataAccessException("Investimento já encerrado");
		}

		BigDecimal valorDevolvido = inv.getValorInvestido();

		Conta conta = contaDAO.buscarPorId(inv.getIdConta());

		if (conta == null) {
			throw new DataAccessException("Conta vinculada ao investimento não encontrada");
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
	}

	public List<Investimento> listarPorConta(int idConta) throws DataAccessException {
		return investimentoDAO.listarPorConta(idConta);
	}

	public Investimento buscarPorId(int id) throws DataAccessException {
		return investimentoDAO.buscarPorId(id);
	}
}