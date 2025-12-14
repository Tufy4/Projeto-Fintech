package edu.ifsp.banco.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.enums.TiposConta;

public class ContaDAO {

	private Connection connection;

	public ContaDAO(Connection connection) {
		this.connection = connection;
	}

	public void inserir(Conta conta) throws DataAccessException {
		String sql = "INSERT INTO CONTAS (ID, USUARIO_ID, NUMERO_CONTA, TIPO) "
				+ "VALUES (seq_contas.NEXTVAL, ?, seq_num_conta.NEXTVAL, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

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

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, numeroConta);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				conta = new Conta(rs.getInt("ID"), rs.getInt("USUARIO_ID"), rs.getInt("AGENCIA"),
						rs.getInt("NUMERO_CONTA"), rs.getBigDecimal("SALDO"), TiposConta.valueOf(rs.getString("TIPO")),
						rs.getTimestamp("DATA_CRIACAO"));
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

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, idConta);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				conta = new Conta(rs.getInt("ID"), rs.getInt("USUARIO_ID"), rs.getInt("AGENCIA"),
						rs.getInt("NUMERO_CONTA"), rs.getBigDecimal("SALDO"), TiposConta.valueOf(rs.getString("TIPO")),
						rs.getTimestamp("DATA_CRIACAO"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Erro ao buscar conta");
		}

		return conta;
	}

	public List<Conta> listar() throws DataAccessException {
		List<Conta> contas = new ArrayList<>();
		String sql = "SELECT * FROM CONTAS";

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Conta c = new Conta(rs.getInt("ID"), rs.getInt("USUARIO_ID"), rs.getInt("AGENCIA"),
						rs.getInt("NUMERO_CONTA"), rs.getBigDecimal("SALDO"), TiposConta.valueOf(rs.getString("TIPO")),
						rs.getTimestamp("DATA_CRIACAO"));
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

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setDouble(1, novoSaldo);
			stmt.setInt(2, idConta);
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException("Erro ao atualizar saldo");
		}
	}

	public List<Conta> buscarPorIdUsuario(int idUser) throws DataAccessException {
		List<Conta> contas = new ArrayList<>();
		String sql = "SELECT * FROM CONTAS WHERE USUARIO_ID = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, idUser);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Conta conta = new Conta(rs.getInt("ID"), rs.getInt("USUARIO_ID"), rs.getInt("AGENCIA"),
						rs.getInt("NUMERO_CONTA"), rs.getBigDecimal("SALDO"), TiposConta.valueOf(rs.getString("TIPO")),
						rs.getTimestamp("DATA_CRIACAO"));
				contas.add(conta);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Erro ao buscar contas do usuário");
		}

		return contas;
	}

	public int contarTotal() throws DataAccessException {
		String sql = "SELECT COUNT(*) AS TOTAL FROM CONTAS";
		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				return rs.getInt("TOTAL");
			}
		} catch (SQLException e) {
			throw new DataAccessException("Erro ao contar contas: " + e.getMessage());
		}
		return 0;
	}
}