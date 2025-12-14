package edu.ifsp.banco.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.persistencia.ConnectionSingleton;
import edu.ifsp.banco.persistencia.ContaDAO;

public class ContaSERVICE {

	public void criarConta(Conta conta) throws Exception {
		if (conta == null) {
			throw new Exception("Conta inválida");
		}

		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			ContaDAO dao = new ContaDAO(conn);
			dao.inserir(conta);
		} catch (Exception e) {
			throw new Exception("Erro ao criar conta: " + e.getMessage());
		}
	}

	public Conta buscarPorNumero(int numeroConta) throws Exception {
		if (numeroConta <= 0) {
			throw new Exception("Número de conta inválido");
		}

		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			ContaDAO dao = new ContaDAO(conn);
			return dao.buscarPorNumero(numeroConta);
		} catch (Exception e) {
			throw new Exception("Erro ao buscar conta: " + e.getMessage());
		}
	}

	public List<Conta> buscarContasPorUsuario(int idUsuario) throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			ContaDAO dao = new ContaDAO(conn);
			return dao.buscarPorIdUsuario(idUsuario);
		} catch (Exception e) {
			throw new Exception("Erro ao buscar contas do usuário: " + e.getMessage());
		}
	}

	public Conta buscarPorId(int idConta) throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			ContaDAO dao = new ContaDAO(conn);
			return dao.buscarPorId(idConta);
		} catch (Exception e) {
			throw new Exception("Erro ao buscar conta específica: " + e.getMessage());
		}
	}

	public List<Conta> listarContas() throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			ContaDAO dao = new ContaDAO(conn);
			return dao.listar();
		} catch (Exception e) {
			throw new Exception("Erro ao listar contas: " + e.getMessage());
		}
	}

	public void atualizarSaldo(int idConta, BigDecimal novoSaldo) throws Exception {

		if (idConta <= 0) {
			throw new Exception("Conta inválida");
		}

		if (novoSaldo == null) {
			throw new Exception("Saldo inválido");
		}

		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			ContaDAO dao = new ContaDAO(conn);
			dao.atualizarSaldo(idConta, novoSaldo.doubleValue());
		} catch (Exception e) {
			throw new Exception("Erro ao atualizar saldo: " + e.getMessage());
		}
	}

	public int obterTotalContas() throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			ContaDAO dao = new ContaDAO(conn);
			return dao.contarTotal();
		} catch (Exception e) {
			return 0;
		}
	}
}