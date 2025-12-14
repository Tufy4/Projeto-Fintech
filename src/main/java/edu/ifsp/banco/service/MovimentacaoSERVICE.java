package edu.ifsp.banco.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Movimentacoes;
import edu.ifsp.banco.modelo.enums.StatusMovimentacao;
import edu.ifsp.banco.modelo.enums.TipoMovimentacao;
import edu.ifsp.banco.persistencia.ContaDAO;
import edu.ifsp.banco.persistencia.DataAccessException;
import edu.ifsp.banco.persistencia.MovimentacaoDAO;

public class MovimentacaoSERVICE {

	private ContaDAO contaDAO = new ContaDAO();
	private MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO();

	public Conta depositar(int numeroConta, BigDecimal valor) throws DataAccessException {
		Conta conta = contaDAO.buscarPorNumero(numeroConta);
		if (conta == null) {
			throw new DataAccessException("Conta não encontrada");
		}

		BigDecimal saldoAnterior = conta.getSaldo();
		BigDecimal novoSaldo = saldoAnterior.add(valor);

		contaDAO.atualizarSaldo(conta.getId(), novoSaldo.doubleValue());
		conta.setSaldo(novoSaldo);

		Movimentacoes mov = new Movimentacoes(0, conta.getId(), conta.getId(), valor, saldoAnterior, novoSaldo,
				TipoMovimentacao.DEPOSITO, new Timestamp(System.currentTimeMillis()), "Depósito via App",
				StatusMovimentacao.CONCLUIDA);

		movimentacaoDAO.inserir(mov);

		return conta;
	}

	public void realizarTransferencia(Conta contaOrigem, int numeroContaDestino, BigDecimal valor)
			throws DataAccessException {

		if (valor.compareTo(BigDecimal.ZERO) <= 0) {
			throw new DataAccessException("Valor deve ser maior que zero.");
		}
		if (contaOrigem.getSaldo().compareTo(valor) < 0) {
			throw new DataAccessException("Saldo insuficiente.");
		}

		Conta contaDestino = contaDAO.buscarPorNumero(numeroContaDestino);
		if (contaDestino == null) {
			throw new DataAccessException("Conta de destino não encontrada.");
		}
		if (contaOrigem.getId() == contaDestino.getId()) {
			throw new DataAccessException("Não é possível transferir para a mesma conta.");
		}

		BigDecimal saldoAnteriorOrigem = contaOrigem.getSaldo();
		BigDecimal novoSaldoOrigem = saldoAnteriorOrigem.subtract(valor);
		BigDecimal saldoAnteriorDestino = contaDestino.getSaldo();
		BigDecimal novoSaldoDestino = saldoAnteriorDestino.add(valor);

		contaDAO.atualizarSaldo(contaOrigem.getId(), novoSaldoOrigem.doubleValue());
		contaDAO.atualizarSaldo(contaDestino.getId(), novoSaldoDestino.doubleValue());

		Timestamp dataAgora = new Timestamp(System.currentTimeMillis());

		Movimentacoes movSaida = new Movimentacoes(0, contaOrigem.getId(), contaDestino.getId(), valor,
				saldoAnteriorOrigem, novoSaldoOrigem, TipoMovimentacao.TRANSFERENCIA_ENVIADA, dataAgora,
				"Transferência enviada", StatusMovimentacao.CONCLUIDA);

		movimentacaoDAO.inserir(movSaida);

		Movimentacoes movEntrada = new Movimentacoes(0, contaOrigem.getId(), contaDestino.getId(), valor,
				saldoAnteriorDestino, novoSaldoDestino, TipoMovimentacao.TRANSFERENCIA_RECEBIDA, dataAgora,
				"Transferência recebida", StatusMovimentacao.CONCLUIDA);

		movimentacaoDAO.inserir(movEntrada);
	}

	public List<Movimentacoes> consultarExtrato(int idConta) throws DataAccessException {
		try {
			return movimentacaoDAO.listarMovimentacoes(idConta);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public List<Movimentacoes> consultarExtrato(int idConta, String inicioStr, String fimStr)
			throws DataAccessException {
		try {
			java.sql.Date dataInicio = null;
			java.sql.Date dataFim = null;

			if (inicioStr != null && !inicioStr.isEmpty()) {
				dataInicio = java.sql.Date.valueOf(inicioStr);
			}
			if (fimStr != null && !fimStr.isEmpty()) {
				dataFim = java.sql.Date.valueOf(fimStr);
			}

			if (dataInicio == null && dataFim == null) {
				return movimentacaoDAO.listarMovimentacoes(idConta);
			} else {
				return movimentacaoDAO.listarMovimentacoes(idConta, dataInicio, dataFim);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}