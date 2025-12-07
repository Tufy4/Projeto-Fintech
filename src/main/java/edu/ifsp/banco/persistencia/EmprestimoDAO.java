package edu.ifsp.banco.persistencia;

import edu.ifsp.banco.modelo.Emprestimo;
import edu.ifsp.banco.modelo.enums.StatusEmprestimo;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

	public int inserir(Emprestimo emprestimo) throws DataAccessException {
		String sql = "INSERT INTO emprestimos (id, conta_id, valor_emprestimo, taxa_juros_mensal, parcelas, status, "
				+ "data_solicitacao, data_aprovacao, data_ultimo_pagamento) "
				+ "VALUES (SEQ_EMPRESTIMOS.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";

		String[] generatedColumns = { "ID" };

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, generatedColumns)) {

			stmt.setInt(1, emprestimo.getConta_id());
			stmt.setBigDecimal(2, emprestimo.getValor_emprestimo());
			stmt.setBigDecimal(3, emprestimo.getTaxa_juros_mensal());
			stmt.setInt(4, emprestimo.getParcelas());
			stmt.setString(5, emprestimo.getStatus().name());
			stmt.setTimestamp(6, emprestimo.getData_solicitacao());
			stmt.setTimestamp(7, emprestimo.getData_aprovacao());
			stmt.setTimestamp(8, emprestimo.getData_ultimo_pagamento());

			int affectedRows = stmt.executeUpdate();

			if (affectedRows == 0) {
				throw new DataAccessException("A criação do empréstimo falhou, nenhuma linha afetada.");
			}

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				} else {
					throw new DataAccessException("A criação do empréstimo falhou, nenhum ID obtido.");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Erro ao inserir empréstimo: " + e.getMessage());
		}
	}

	public Emprestimo buscarPorId(int id) throws DataAccessException {
		String sql = "SELECT * FROM emprestimos WHERE id = ?";
		Emprestimo emp = null;

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				emp = mapResultSetToEmprestimo(rs);
			}

		} catch (SQLException ex) {
			throw new DataAccessException("Erro ao buscar empréstimo");
		}

		return emp;
	}

	public List<Emprestimo> listar() throws DataAccessException {
		List<Emprestimo> lista = new ArrayList<>();
		String sql = "SELECT * FROM emprestimos";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				lista.add(mapResultSetToEmprestimo(rs));
			}

		} catch (SQLException ex) {
			throw new DataAccessException("Erro ao listar empréstimos");
		}

		return lista;
	}

	public List<Emprestimo> listarPorStatus(StatusEmprestimo status) throws DataAccessException {
		List<Emprestimo> lista = new ArrayList<>();
		String sql = "SELECT * FROM emprestimos WHERE status = ? ORDER BY data_solicitacao ASC";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, status.name());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				lista.add(mapResultSetToEmprestimo(rs));
			}

		} catch (SQLException ex) {
			throw new DataAccessException("Erro ao listar empréstimos por status: " + ex.getMessage());
		}

		return lista;
	}

	public void atualizarStatus(int id, StatusEmprestimo novoStatus) throws DataAccessException {
		String sql = "UPDATE emprestimos SET status = ? WHERE id = ?";
		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, novoStatus.getValor());
			stmt.setInt(2, id);
			stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new DataAccessException("Erro ao atualizar status: " + ex.getMessage());
		}
	}

	public void aprovarEmprestimo(int id) throws DataAccessException {
		String sql = "UPDATE emprestimos SET status = ?, data_aprovacao = ? WHERE id = ?";
		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, StatusEmprestimo.APROVADO.getValor());
			stmt.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
			stmt.setInt(3, id);
			stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new DataAccessException("Erro ao aprovar empréstimo");
		}
	}

	public BigDecimal buscarTaxaEmprestimo() {
		String sql = "SELECT TAXA_JUROS_PADRAO FROM config_emprestimo ORDER BY id DESC FETCH FIRST 1 ROW ONLY";
		BigDecimal taxa = null;

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				taxa = rs.getBigDecimal("TAXA_JUROS_PADRAO");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new DataAccessException("Erro ao buscar taxa de juros");
		}

		return taxa;
	}

	public void registrarPagamento(int id, Timestamp dataPagamento) throws DataAccessException {
		String sql = "UPDATE emprestimos SET data_ultimo_pagamento = ? WHERE id = ?";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setTimestamp(1, dataPagamento);
			stmt.setInt(2, id);
			stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new DataAccessException("Erro ao registrar pagamento do empréstimo");
		}
	}

	private Emprestimo mapResultSetToEmprestimo(ResultSet rs) throws SQLException {
		Emprestimo emp = new Emprestimo(rs.getInt("id"), rs.getInt("conta_id"), rs.getBigDecimal("valor_emprestimo"),
				rs.getBigDecimal("taxa_juros_mensal"), rs.getInt("parcelas"),
				StatusEmprestimo.valueOf(rs.getString("status")), rs.getTimestamp("data_solicitacao"));
		emp.setData_aprovacao(rs.getTimestamp("data_aprovacao"));
		emp.setData_ultimo_pagamento(rs.getTimestamp("data_ultimo_pagamento"));
		return emp;
	}
}