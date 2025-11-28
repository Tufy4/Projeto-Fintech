package edu.ifsp.banco.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Movimentacoes;
import edu.ifsp.banco.modelo.enums.StatusMovimentacao;
import edu.ifsp.banco.persistencia.ContaDAO;
import edu.ifsp.banco.persistencia.MovimentacaoDAO;
import edu.ifsp.banco.persistencia.DataAccessException;

public class MovimentacaoSERVICE {

	private MovimentacaoDAO movimentacaoDAO;
	private ContaDAO contaDAO;

	public MovimentacaoSERVICE() {
		this.movimentacaoDAO = new MovimentacaoDAO();
		this.contaDAO = new ContaDAO();
	}

	public void depositar(int idContaDestino, BigDecimal valor) throws Exception {

		if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
			throw new Exception("Valor inválido.");
		}

		Conta destino = contaDAO.buscarPorNumero(idContaDestino);

		if (destino == null) {
			throw new Exception("Conta destino inexistente");
		}

		destino.setSaldo(destino.getSaldo().add(valor));
		contaDAO.atualizarSaldo(destino.getId(), destino.getSaldo().doubleValue());

		Movimentacoes mov = new Movimentacoes();
		mov.setContaOrigemId(0); // depósito não tem conta origem
		mov.setContaDestinoId(idContaDestino);
		mov.setValor(valor);
		mov.setDescricao("Depósito bancário");
//		mov.setTipo("DEPOSITO");
//		mov.setStatus("CONCLUIDA");
		mov.setDataTransacao(new Timestamp(System.currentTimeMillis()));

		movimentacaoDAO.inserir(mov, null, destino, valor);
	}

	public Movimentacoes buscarPorId(int idMov) throws Exception {

		if (idMov <= 0) {
			throw new Exception("ID inválido.");
		}

		Movimentacoes mov = movimentacaoDAO.buscarPorId(idMov);

		if (mov == null) {
			throw new Exception("Movimentação não encontrada.");
		}

		return mov;
	}

	public void registrarMovimentacao(Movimentacoes mov, int idContaOrigem, int idContaDestino, BigDecimal valor)
			throws Exception {

		if (mov == null) {
			throw new Exception("Mov	imentação inválida");
		}

		if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
			throw new Exception("Valor da movimentação deve ser positivo");
		}

		Conta origem = contaDAO.buscarPorNumero(idContaOrigem);
		Conta destino = contaDAO.buscarPorNumero(idContaDestino);

		if (origem == null) {
			throw new Exception("Conta de origem inexistente");
		}

		if (destino == null) {
			throw new Exception("Conta de destino inexistente");
		}

		if (origem.getSaldo().compareTo(valor) < 0) {
			throw new Exception("Saldo insuficiente");
		}

		origem.setSaldo(origem.getSaldo().subtract(valor));
		destino.setSaldo(destino.getSaldo().add(valor));

		try {
			contaDAO.atualizarSaldo(origem.getId(), origem.getSaldo().doubleValue());
			contaDAO.atualizarSaldo(destino.getId(), destino.getSaldo().doubleValue());

			mov.setStatus(StatusMovimentacao.CONCLUIDA.getValor());

			movimentacaoDAO.inserir(mov, origem, destino, valor);

		} catch (DataAccessException e) {
			throw new Exception("Erro ao registrar movimentação: " + e.getMessage());
		}
	}

	public List<Movimentacoes> listarMovimentacoes(int idConta) throws Exception {
		if (idConta <= 0) {
			throw new Exception("ID da conta inválido");
		}

		try {
			return movimentacaoDAO.listarMovimentacoes(idConta);
		} catch (SQLException e) {
			throw new Exception("Erro ao listar movimentações: " + e.getMessage());
		}
	}

	public void alterarStatusMovimentacao(int idMov, StatusMovimentacao novoStatus) throws Exception {

		if (idMov <= 0) {
			throw new Exception("ID da movimentação inválido");
		}

		if (novoStatus == null) {
			throw new Exception("Status inválido");
		}

		try {
			movimentacaoDAO.AtualizarStatus(novoStatus.getValor(), idMov);
		} catch (DataAccessException e) {
			throw new Exception("Erro ao atualizar status da movimentação");
		}
	}

}
