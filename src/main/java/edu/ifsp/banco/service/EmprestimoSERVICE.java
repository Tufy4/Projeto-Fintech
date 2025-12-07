package edu.ifsp.banco.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Movimentacoes;
import edu.ifsp.banco.modelo.Emprestimo;
import edu.ifsp.banco.modelo.ParcelaEmprestimo;
import edu.ifsp.banco.modelo.enums.StatusEmprestimo;
import edu.ifsp.banco.modelo.enums.StatusMovimentacao;
import edu.ifsp.banco.modelo.enums.StatusParcela;
import edu.ifsp.banco.modelo.enums.TipoMovimentacao;
import edu.ifsp.banco.persistencia.ContaDAO;
import edu.ifsp.banco.persistencia.DataAccessException;
import edu.ifsp.banco.persistencia.EmprestimoDAO;
import edu.ifsp.banco.persistencia.MovimentacaoDAO;
import edu.ifsp.banco.persistencia.ParcelaEmprestimoDAO;

public class EmprestimoSERVICE {

	private EmprestimoDAO emprestimoDAO;
	private ParcelaEmprestimoDAO parcelaDAO;
	private ContaDAO contaDAO;
	private MovimentacaoDAO movimentacaoDAO;

	public EmprestimoSERVICE() {
		this.emprestimoDAO = new EmprestimoDAO();
		this.parcelaDAO = new ParcelaEmprestimoDAO();
		this.contaDAO = new ContaDAO();
		this.movimentacaoDAO = new MovimentacaoDAO();
	}

	public List<ParcelaEmprestimo> simularCondicoes(BigDecimal valor, int parcelas, BigDecimal taxaJuros) {
		Emprestimo temp = new Emprestimo(0, 0, valor, taxaJuros, parcelas, StatusEmprestimo.SIMULADO, null);
		return calcularCronogramaSAC(temp);
	}

	public List<Emprestimo> buscarPorConta(int idConta) throws Exception {
		try {
			List<Emprestimo> emprestimos = emprestimoDAO.listarPorConta(idConta);
			for (Emprestimo emp : emprestimos) {
				List<ParcelaEmprestimo> parcelas = parcelaDAO.listarPorEmprestimo(emp.getId());
				emp.setListaParcelas(parcelas);
			}
			return emprestimos;
		} catch (DataAccessException e) {
			throw new Exception("Erro ao buscar empréstimos do cliente.");
		}
	}

	public void solicitarEmprestimo(Emprestimo emp) throws Exception {
		validarDadosIniciais(emp);

		emp.setStatus(StatusEmprestimo.SOLICITADO);

		emp.setData_solicitacao(new Timestamp(System.currentTimeMillis()));

		try {
			int idGerado = emprestimoDAO.inserir(emp);
			emp.setId(idGerado);
		} catch (DataAccessException e) {
			throw new Exception("Erro ao registrar solicitação: " + e.getMessage());
		}
	}

	public void aprovarEmprestimo(int idEmprestimo) throws Exception {
		Emprestimo emp = emprestimoDAO.buscarPorId(idEmprestimo);

		if (emp == null)
			throw new Exception("Empréstimo não encontrado.");

		if (emp.getStatus() != StatusEmprestimo.SOLICITADO) {
			throw new Exception("Apenas empréstimos com status SOLICITADO podem ser aprovados.");
		}

		List<ParcelaEmprestimo> cronogramaDefinitivo = calcularCronogramaSAC(emp);

		try {
			emprestimoDAO.aprovarEmprestimo(idEmprestimo);
			parcelaDAO.inserirLote(cronogramaDefinitivo);
		} catch (DataAccessException e) {
			throw new Exception("Erro técnico ao processar aprovação: " + e.getMessage());
		}
	}

	public void rejeitarEmprestimo(int idEmprestimo) throws Exception {
		try {
			emprestimoDAO.atualizarStatus(idEmprestimo, StatusEmprestimo.REJEITADO);
		} catch (DataAccessException e) {
			throw new Exception("Erro ao rejeitar empréstimo.");
		}
	}

	public void pagarParcela(int idParcela) throws Exception {
		ParcelaEmprestimo parcela = parcelaDAO.buscarPorId(idParcela);
		if (parcela == null)
			throw new Exception("Parcela não encontrada.");

		if (parcela.getStatus() == StatusParcela.PAGO)
			throw new Exception("Esta parcela já foi paga.");

		Emprestimo emprestimo = emprestimoDAO.buscarPorId(parcela.getEmprestimoId());
		if (emprestimo == null)
			throw new Exception("Empréstimo vinculado não encontrado.");

		Conta conta = contaDAO.buscarPorId(emprestimo.getConta_id());
		if (conta == null)
			throw new Exception("Conta do cliente não encontrada.");

		BigDecimal valorPagamento = parcela.getValorParcela();
		if (conta.getSaldo().compareTo(valorPagamento) < 0) {
			throw new Exception("Saldo insuficiente para pagar esta parcela.");
		}

		try {
			BigDecimal novoSaldo = conta.getSaldo().subtract(valorPagamento);
			conta.setSaldo(novoSaldo);
			contaDAO.atualizarSaldo(conta.getId(), novoSaldo.doubleValue());

			Movimentacoes mov = new Movimentacoes();
			mov.setContaOrigemId(conta.getId());
			mov.setValor(valorPagamento);
			mov.setTipo(TipoMovimentacao.SAQUE);
			mov.setDescricao("Pagamento Empréstimo #" + emprestimo.getId() + " - Parc. " + parcela.getNumeroParcela());
			mov.setDataTransacao(new Timestamp(System.currentTimeMillis()));
			mov.setStatus(StatusMovimentacao.CONCLUIDA);

			movimentacaoDAO.inserir(mov);

			parcelaDAO.pagarParcela(idParcela);

			emprestimoDAO.registrarPagamento(parcela.getEmprestimoId(), new Timestamp(System.currentTimeMillis()));
			verificarQuitacao(parcela.getEmprestimoId());

		} catch (DataAccessException e) {
			throw new Exception("Erro técnico ao processar pagamento: " + e.getMessage());
		}
	}

	public BigDecimal buscarTaxaPadrao() {
		BigDecimal taxa = emprestimoDAO.buscarTaxaEmprestimo();
		return taxa;
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

	private void verificarQuitacao(int emprestimoId) throws DataAccessException {
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

	public List<Emprestimo> listarPorStatus(StatusEmprestimo status) throws Exception {
		try {
			return emprestimoDAO.listarPorStatus(status);
		} catch (DataAccessException e) {
			throw new Exception("Erro ao listar empréstimos por status: " + e.getMessage());
		}
	}

	private void validarDadosIniciais(Emprestimo emp) throws Exception {
		if (emp.getValor_emprestimo() == null || emp.getValor_emprestimo().compareTo(BigDecimal.ZERO) <= 0) {
			throw new Exception("Valor do empréstimo inválido.");
		}
		if (emp.getTaxa_juros_mensal() == null || emp.getTaxa_juros_mensal().compareTo(BigDecimal.ZERO) <= 0) {
			throw new Exception("Taxa de juros inválida.");
		}
		if (emp.getParcelas() < 1) {
			throw new Exception("Nsúmero de parcelas deve ser positivo.");
		}
		System.out.println("Lógica exata: " + contaDAO.buscarPorId(emp.getConta_id()));
		if (contaDAO.buscarPorId(emp.getConta_id()) == null) {
			throw new Exception("Conta vinculada não encontrada.");
		}
	}
}