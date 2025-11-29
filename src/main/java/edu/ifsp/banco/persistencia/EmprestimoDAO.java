package edu.ifsp.banco.persistencia;

import edu.ifsp.banco.modelo.Emprestimo;
import edu.ifsp.banco.modelo.enums.StatusEmprestimo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

	public void inserir(Emprestimo emprestimo) throws DataAccessException {
		String sql = "INSERT INTO emprestimos (id, conta_id, valor_emprestimo, juros, parcelas, status, "
				+ "data_solicitacao, data_aprovacao, data_ultimo_pagamento) "
				+ "VALUES (SEQ_EMPRESTIMOS.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, emprestimo.getConta_id());
			stmt.setBigDecimal(2, emprestimo.getValor_emprestimo());
			stmt.setBigDecimal(3, emprestimo.getJuros());
			stmt.setInt(4, emprestimo.getParcelas());
			stmt.setString(5, emprestimo.getStatus().getValor());
			stmt.setTimestamp(6, emprestimo.getData_solicitacao());
			stmt.setTimestamp(7, emprestimo.getData_aprovacao());
			stmt.setTimestamp(8, emprestimo.getData_ultimo_pagamento());

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException("Erro ao inserir empréstimo");
		}
	}

	public Emprestimo buscarPorId(int id) throws DataAccessException {
		String sql = "SELECT * FROM emprestimos WHERE id = ?";
		Emprestimo emp = null;

		try (Connection conn = ConnectionSingleton.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				emp = new Emprestimo(rs.getInt("id"), rs.getInt("conta_id"), rs.getBigDecimal("valor_emprestimo"),
						rs.getBigDecimal("juros"), rs.getInt("parcelas"),
						StatusEmprestimo.valueOf(rs.getString("status")), rs.getTimestamp("data_solicitacao"),
						rs.getTimestamp("data_aprovacao"), rs.getTimestamp("data_ultimo_pagamento"));
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
				Emprestimo e = new Emprestimo(rs.getInt("id"), rs.getInt("conta_id"),
						rs.getBigDecimal("valor_emprestimo"), rs.getBigDecimal("juros"), rs.getInt("parcelas"),
						StatusEmprestimo.valueOf(rs.getString("status")), rs.getTimestamp("data_solicitacao"),
						rs.getTimestamp("data_aprovacao"), rs.getTimestamp("data_ultimo_pagamento"));
				lista.add(e);
			}

		} catch (SQLException ex) {
			throw new DataAccessException("Erro ao listar empréstimos");
		}

		return lista;
	}

	public void atualizarStatus(int id, StatusEmprestimo novoStatus) throws DataAccessException {
		String sql = "UPDATE emprestimos SET status = ? WHERE id = ?";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, novoStatus.getValor());
			stmt.setInt(2, id);
			stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new DataAccessException("Erro ao atualizar status do empréstimo");
		}
	}

	// vamos usar para registrar quando o cliente pagou a parcela do emprestimo
	public void registrarPagamento(int id, Timestamp dataPagamento) throws DataAccessException {
		String sql = "UPDATE emprestimos SET data_ultimo_pagamento = ? WHERE id = ?";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setTimestamp(1, dataPagamento);
			stmt.setInt(2, id);
			stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new DataAccessException("Erro ao registrar pagamento do empréstimo");
		}
	}
}
