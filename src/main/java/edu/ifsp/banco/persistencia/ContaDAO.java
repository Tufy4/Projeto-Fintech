package edu.ifsp.banco.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.enums.StatusConta;
import edu.ifsp.banco.modelo.enums.TiposConta;

public class ContaDAO {

	public void inserir(Conta conta) throws DataAccessException {
		String sql = "INSERT INTO CONTAS (ID, USUARIO_ID, NUMERO_CONTA, TIPO, STATUS) "
				+ "VALUES (seq_contas.NEXTVAL, ?, seq_num_conta.NEXTVAL, ?, 'BLOQUEADA')";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, conta.getUsuarioId());
			stmt.setString(2, conta.getTipo().getValor());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Erro ao inserir conta: " + e.getMessage());
		}
	}

	public Conta buscarPorNumero(int numeroConta) throws DataAccessException {
		String sql = "SELECT * FROM CONTAS WHERE NUMERO_CONTA = ?";
		Conta conta = null;

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, numeroConta);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				conta = new Conta(rs.getInt("ID"), rs.getInt("USUARIO_ID"), rs.getInt("AGENCIA"),
						rs.getInt("NUMERO_CONTA"), rs.getBigDecimal("SALDO"), TiposConta.valueOf(rs.getString("TIPO")),
						StatusConta.valueOf(rs.getString("STATUS")), rs.getTimestamp("DATA_CRIACAO"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Erro ao buscar conta");
		}

		return conta;
	}

	public Conta buscarPorId(int idConta) throws DataAccessException {
		String sql = "SELECT * FROM CONTAS WHERE id = ?";
		Conta conta = null;

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, idConta);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				conta = new Conta(rs.getInt("ID"), rs.getInt("USUARIO_ID"), rs.getInt("AGENCIA"),
						rs.getInt("NUMERO_CONTA"), rs.getBigDecimal("SALDO"), TiposConta.valueOf(rs.getString("TIPO")),
						StatusConta.valueOf(rs.getString("STATUS")), rs.getTimestamp("DATA_CRIACAO"));
			}

			System.out.println("conta encontrada: " + conta.getId());

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Erro ao buscar conta");
		}

		return conta;
	}

	public List<Conta> listar() throws DataAccessException {
		List<Conta> contas = new ArrayList<>();
		String sql = "SELECT * FROM CONTAS";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Conta c = new Conta(rs.getInt("ID"), rs.getInt("USUARIO_ID"), rs.getInt("AGENCIA"),
						rs.getInt("NUMERO_CONTA"), rs.getBigDecimal("SALDO"), TiposConta.valueOf(rs.getString("TIPO")),
						StatusConta.valueOf(rs.getString("STATUS")), rs.getTimestamp("DATA_CRIACAO"));
				contas.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Erro ao listar contas");
		}

		return contas;
	}

	public void atualizarSaldo(int idConta, double novoSaldo) throws DataAccessException {
		String sql = "UPDATE CONTAS SET SALDO = ? WHERE ID = ?";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setDouble(1, novoSaldo);
			stmt.setInt(2, idConta);
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException("Erro ao atualizar saldo");
		}
	}

	public Conta buscarPorIdUsuario(int idUser) throws DataAccessException {
		String sql = "SELECT * FROM CONTAS WHERE USUARIO_ID = ?";
		Conta conta = null;

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, idUser);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				conta = new Conta(rs.getInt("ID"), rs.getInt("USUARIO_ID"), rs.getInt("AGENCIA"),
						rs.getInt("NUMERO_CONTA"), rs.getBigDecimal("SALDO"), TiposConta.valueOf(rs.getString("TIPO")),
						StatusConta.valueOf(rs.getString("STATUS")), rs.getTimestamp("DATA_CRIACAO"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Erro ao buscar conta");
		}

		return conta;
	}
}