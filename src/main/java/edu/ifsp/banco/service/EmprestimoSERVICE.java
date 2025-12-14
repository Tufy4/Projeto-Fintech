package edu.ifsp.banco.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Emprestimo;
import edu.ifsp.banco.modelo.Movimentacoes;
import edu.ifsp.banco.modelo.ParcelaEmprestimo;
import edu.ifsp.banco.modelo.enums.StatusEmprestimo;
import edu.ifsp.banco.modelo.enums.StatusMovimentacao;
import edu.ifsp.banco.modelo.enums.StatusParcela;
import edu.ifsp.banco.modelo.enums.TipoMovimentacao;
import edu.ifsp.banco.persistencia.ConnectionSingleton;
import edu.ifsp.banco.persistencia.ContaDAO;
import edu.ifsp.banco.persistencia.DataAccessException;
import edu.ifsp.banco.persistencia.EmprestimoDAO;
import edu.ifsp.banco.persistencia.MovimentacaoDAO;
import edu.ifsp.banco.persistencia.ParcelaEmprestimoDAO;

public class EmprestimoSERVICE {

	public List<ParcelaEmprestimo> simularCondicoes(BigDecimal valor, int parcelas, BigDecimal taxaJuros) {
		Emprestimo temp = new Emprestimo(0, 0, valor, taxaJuros, parcelas, StatusEmprestimo.SIMULADO, null);
		return calcularCronogramaSAC(temp);
	}

	public List<Emprestimo> buscarPorConta(int idConta) throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			EmprestimoDAO emprestimoDAO = new EmprestimoDAO(conn);
			ParcelaEmprestimoDAO parcelaDAO = new ParcelaEmprestimoDAO(conn);

			List<Emprestimo> emprestimos = emprestimoDAO.listarPorConta(idConta);
			for (Emprestimo emp : emprestimos) {
				List<ParcelaEmprestimo> parcelas = parcelaDAO.listarPorEmprestimo(emp.getId());
				emp.setListaParcelas(parcelas);
			}
			return emprestimos;
		} catch (Exception e) {
			throw new Exception("Erro ao buscar empréstimos do cliente.");
		}
	}

	public void solicitarEmprestimo(Emprestimo emp) throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			validarDadosIniciais(emp, conn);

			EmprestimoDAO emprestimoDAO = new EmprestimoDAO(conn);

			emp.setStatus(StatusEmprestimo.SOLICITADO);
			emp.setData_solicitacao(new Timestamp(System.currentTimeMillis()));

			int idGerado = emprestimoDAO.inserir(emp);
			emp.setId(idGerado);
		} catch (Exception e) {
			throw new Exception("Erro ao registrar solicitação: " + e.getMessage());
		}
	}

	public void aprovarEmprestimo(int idEmprestimo) throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			try {
				EmprestimoDAO emprestimoDAO = new EmprestimoDAO(conn);
				ContaDAO contaDAO = new ContaDAO(conn);
				MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO(conn);
				ParcelaEmprestimoDAO parcelaDAO = new ParcelaEmprestimoDAO(conn);

				Emprestimo emp = emprestimoDAO.buscarPorId(idEmprestimo);
				if (emp == null)
					throw new Exception("Empréstimo não encontrado.");

				Conta conta = contaDAO.buscarPorId(emp.getConta_id());
				if (emp.getStatus() != StatusEmprestimo.SOLICITADO) {
					throw new Exception("Apenas empréstimos com status SOLICITADO podem ser aprovados.");
				}

				List<ParcelaEmprestimo> cronogramaDefinitivo = calcularCronogramaSAC(emp);

				BigDecimal saldoAnterior = conta.getSaldo();
				BigDecimal novoSaldo = saldoAnterior.add(emp.getValor_emprestimo());

				Movimentacoes mov = new Movimentacoes(0, 0, conta.getId(), emp.getValor_emprestimo(), saldoAnterior,
						novoSaldo, TipoMovimentacao.EMPRESTIMO, new Timestamp(System.currentTimeMillis()),
						"Empréstimo Bancário Aprovado", StatusMovimentacao.CONCLUIDA);

				movimentacaoDAO.inserir(mov);
				emprestimoDAO.aprovarEmprestimo(idEmprestimo);
				parcelaDAO.inserirLote(cronogramaDefinitivo);
				contaDAO.atualizarSaldo(conta.getId(), novoSaldo.doubleValue());

				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				throw new Exception("Erro técnico ao processar aprovação: " + e.getMessage());
			}
		}
	}

	public void pagarParcela(int idParcela) throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			try {
				ParcelaEmprestimoDAO parcelaDAO = new ParcelaEmprestimoDAO(conn);
				EmprestimoDAO emprestimoDAO = new EmprestimoDAO(conn);
				ContaDAO contaDAO = new ContaDAO(conn);
				MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO(conn);

				ParcelaEmprestimo parcela = parcelaDAO.buscarPorId(idParcela);
				if (parcela == null)
					throw new Exception("Parcela não encontrada.");
				if (parcela.getStatus() == StatusParcela.PAGO)
					throw new Exception("Esta parcela já foi paga.");

				Emprestimo emprestimo = emprestimoDAO.buscarPorId(parcela.getEmprestimoId());
				Conta conta = contaDAO.buscarPorId(emprestimo.getConta_id());
				BigDecimal valorPagamento = parcela.getValorParcela();

				if (conta.getSaldo().compareTo(valorPagamento) < 0) {
					throw new Exception("Saldo insuficiente para pagar esta parcela.");
				}

				BigDecimal saldoAnterior = conta.getSaldo();
				BigDecimal novoSaldo = saldoAnterior.subtract(valorPagamento);

				conta.setSaldo(novoSaldo);
				contaDAO.atualizarSaldo(conta.getId(), novoSaldo.doubleValue());

				Movimentacoes mov = new Movimentacoes();
				mov.setContaOrigemId(conta.getId());
				mov.setContaDestinoId(0);
				mov.setValor(valorPagamento);
				mov.setSaldoAnterior(saldoAnterior);
				mov.setSaldoPosterior(novoSaldo);
				mov.setTipo(TipoMovimentacao.SAQUE);
				mov.setDescricao(
						"Pagamento Empréstimo #" + emprestimo.getId() + " - Parc. " + parcela.getNumeroParcela());
				mov.setDataTransacao(new Timestamp(System.currentTimeMillis()));
				mov.setStatus(StatusMovimentacao.CONCLUIDA);

				movimentacaoDAO.inserir(mov);

				parcelaDAO.pagarParcela(idParcela);
				emprestimoDAO.registrarPagamento(parcela.getEmprestimoId(), new Timestamp(System.currentTimeMillis()));

				verificarQuitacao(parcela.getEmprestimoId(), emprestimoDAO, parcelaDAO);

				conn.commit();

			} catch (Exception e) {
				conn.rollback();
				throw new Exception("Erro técnico ao processar pagamento, aplicando rollback: " + e.getMessage());
			}
		}
	}

	public void rejeitarEmprestimo(int idEmprestimo) throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			EmprestimoDAO emprestimoDAO = new EmprestimoDAO(conn);
			emprestimoDAO.atualizarStatus(idEmprestimo, StatusEmprestimo.REJEITADO);
		} catch (Exception e) {
			throw new Exception("Erro ao rejeitar empréstimo.");
		}
	}

	public BigDecimal buscarTaxaPadrao() {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			EmprestimoDAO emprestimoDAO = new EmprestimoDAO(conn);
			return emprestimoDAO.buscarTaxaEmprestimo();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Emprestimo> listarPorStatus(StatusEmprestimo status) throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			EmprestimoDAO emprestimoDAO = new EmprestimoDAO(conn);
			return emprestimoDAO.listarPorStatus(status);
		} catch (Exception e) {
			throw new Exception("Erro ao listar empréstimos por status: " + e.getMessage());
		}
	}

	public int obterQuantidadeSolicitacoes() throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			EmprestimoDAO emprestimoDAO = new EmprestimoDAO(conn);
			return emprestimoDAO.contarSolicitados();
		} catch (Exception e) {
			return 0;
		}
	}

	private List<ParcelaEmprestimo> calcularCronogramaSAC(Emprestimo emp) {
		List<ParcelaEmprestimo> parcelas = new ArrayList<>();

		BigDecimal saldoDevedor = emp.getValor_emprestimo();
		BigDecimal taxaMensal = emp.getTaxa_juros_mensal().divide(new BigDecimal("100"));

		BigDecimal amortizacaoFixa = emp.getValor_emprestimo().divide(new BigDecimal(emp.getParcelas()), 2,
				RoundingMode.HALF_EVEN);

		LocalDate dataBase = LocalDate.now().plusMonths(1);

		for (int i = 1; i <= emp.getParcelas(); i++) {
			BigDecimal jurosMes = saldoDevedor.multiply(taxaMensal).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal valorParcelaTotal = amortizacaoFixa.add(jurosMes);

			ParcelaEmprestimo p = new ParcelaEmprestimo(0, emp.getId(), i, valorParcelaTotal,
					dataBase.plusMonths(i - 1), StatusParcela.PENDENTE);

			p.setValorAmortizacao(amortizacaoFixa);
			p.setValorJuros(jurosMes);

			parcelas.add(p);
			saldoDevedor = saldoDevedor.subtract(amortizacaoFixa);
		}

		return parcelas;
	}

	private void verificarQuitacao(int emprestimoId, EmprestimoDAO emprestimoDAO, ParcelaEmprestimoDAO parcelaDAO)
			throws DataAccessException {
		List<ParcelaEmprestimo> todasParcelas = parcelaDAO.listarPorEmprestimo(emprestimoId);
		boolean todasPagas = true;

		for (ParcelaEmprestimo p : todasParcelas) {
			if (p.getStatus() != StatusParcela.PAGO) {
				todasPagas = false;
				break;
			}
		}

		if (todasPagas) {
			emprestimoDAO.atualizarStatus(emprestimoId, StatusEmprestimo.QUITADO);
		} else {
			Emprestimo emp = emprestimoDAO.buscarPorId(emprestimoId);
			if (emp.getStatus() == StatusEmprestimo.APROVADO) {
				emprestimoDAO.atualizarStatus(emprestimoId, StatusEmprestimo.EM_ANDAMENTO);
			}
		}
	}

	private void validarDadosIniciais(Emprestimo emp, Connection conn) throws Exception {
		if (emp.getValor_emprestimo() == null || emp.getValor_emprestimo().compareTo(BigDecimal.ZERO) <= 0) {
			throw new Exception("Valor do empréstimo inválido.");
		}
		if (emp.getTaxa_juros_mensal() == null || emp.getTaxa_juros_mensal().compareTo(BigDecimal.ZERO) <= 0) {
			throw new Exception("Taxa de juros inválida.");
		}
		if (emp.getParcelas() < 1) {
			throw new Exception("Número de parcelas deve ser positivo.");
		}

		ContaDAO contaDAO = new ContaDAO(conn);
		if (contaDAO.buscarPorId(emp.getConta_id()) == null) {
			throw new Exception("Conta vinculada não encontrada.");
		}
	}
}