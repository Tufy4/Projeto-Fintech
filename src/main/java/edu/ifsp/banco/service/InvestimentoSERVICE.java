	package edu.ifsp.banco.service;
	
	import java.math.BigDecimal;
	import java.sql.Timestamp;
	import java.util.List;
	
	import edu.ifsp.banco.modelo.Conta;
	import edu.ifsp.banco.modelo.Investimento;
	import edu.ifsp.banco.modelo.enums.StatusInvestimento;
	import edu.ifsp.banco.persistencia.ContaDAO;
	import edu.ifsp.banco.persistencia.InvestimentoDAO;
	import edu.ifsp.banco.persistencia.DataAccessException;
	
	public class InvestimentoSERVICE {
	
		private InvestimentoDAO investimentoDAO;
		private ContaDAO contaDAO;
	
		public InvestimentoSERVICE() {
			this.investimentoDAO = new InvestimentoDAO();
			this.contaDAO = new ContaDAO();
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
	
			BigDecimal novoSaldo = conta.getSaldo().subtract(valor);
			contaDAO.atualizarSaldo(conta.getId(), novoSaldo.doubleValue());
	
			Investimento inv = new Investimento(0, conta.getId(), tipo, StatusInvestimento.ATIVO, valor,
					new Timestamp(System.currentTimeMillis()), null);
	
			investimentoDAO.inserir(inv);
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
	
			Conta conta = contaDAO.buscarPorNumero(inv.getIdConta());
	
			BigDecimal novoSaldo = conta.getSaldo().add(valorDevolvido);
			contaDAO.atualizarSaldo(conta.getId(), novoSaldo.doubleValue());
	
			investimentoDAO.encerrar(idInvestimento, new Timestamp(System.currentTimeMillis()));
		}
	
		public List<Investimento> listarPorConta(int idConta) throws DataAccessException {
			return investimentoDAO.listarPorConta(idConta);
		}
	
		public Investimento buscarPorId(int id) throws DataAccessException {
			return investimentoDAO.buscarPorId(id);
		}
	}
