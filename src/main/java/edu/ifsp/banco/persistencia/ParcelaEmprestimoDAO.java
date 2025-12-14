package edu.ifsp.banco.persistencia;

import edu.ifsp.banco.modelo.ParcelaEmprestimo;
import edu.ifsp.banco.modelo.enums.StatusParcela;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParcelaEmprestimoDAO {

	public void inserir(ParcelaEmprestimo parcela) throws DataAccessException {
		String sql = "INSERT INTO parcelas_emprestimo (id, emprestimo_id, numero_parcela, valor_parcela, "
				+ "valor_amortizacao, valor_juros, data_vencimento, status) "
				+ "VALUES (SEQ_PARCELAS.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			configurarStatementInsercao(stmt, parcela);
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException("Erro ao inserir parcela: " + e.getMessage());
		}
	}

	public void inserirLote(List<ParcelaEmprestimo> parcelas) throws DataAccessException {
		String sql = "INSERT INTO parcelas_emprestimo (id, emprestimo_id, numero_parcela, valor_parcela, "
				+ "valor_amortizacao, valor_juros, data_vencimento, status) "
				+ "VALUES (seq_parcelas_emprestimo.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			conn.setAutoCommit(false);
			for (ParcelaEmprestimo parcela : parcelas) {
				configurarStatementInsercao(stmt, parcela);
				stmt.addBatch();
			}
			stmt.executeBatch();
			conn.commit();

		} catch (SQLException e) {
			throw new DataAccessException("Erro ao inserir lote de parcelas: " + e.getMessage());
		}
	}

	public List<ParcelaEmprestimo> listarPorEmprestimo(int emprestimoId) throws DataAccessException {
		List<ParcelaEmprestimo> lista = new ArrayList<>();
		String sql = "SELECT * FROM parcelas_emprestimo WHERE emprestimo_id = ? ORDER BY numero_parcela ASC";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, emprestimoId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				lista.add(mapResultSetToParcela(rs));
			}

		} catch (SQLException ex) {
			throw new DataAccessException("Erro ao listar parcelas do empréstimo");
		}

		return lista;
	}

	public ParcelaEmprestimo buscarPorId(int id) throws DataAccessException {
		String sql = "SELECT * FROM parcelas_emprestimo WHERE id = ?";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return mapResultSetToParcela(rs);
			}

		} catch (SQLException ex) {
			throw new DataAccessException("Erro ao buscar parcela");
		}
		return null;
	}

	public void pagarParcela(int id) throws DataAccessException {
		String sql = "UPDATE parcelas_emprestimo SET status = ?, data_pagamento = ? WHERE id = ?";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, StatusParcela.PAGO.name());
			stmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
			stmt.setInt(3, id);
			stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new DataAccessException("Erro ao pagar parcela");
		}
	}

	public void atualizarValores(ParcelaEmprestimo parcela) throws DataAccessException {
		String sql = "UPDATE parcelas_emprestimo SET valor_parcela = ?, valor_amortizacao = ?, valor_juros = ? "
				+ "WHERE id = ?";
		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setBigDecimal(1, parcela.getValorParcela());
			stmt.setBigDecimal(2, parcela.getValorAmortizacao());
			stmt.setBigDecimal(3, parcela.getValorJuros());
			stmt.setInt(4, parcela.getId());
			stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new DataAccessException("Erro ao atualizar valores da parcela (Recálculo SAC)");
		}
	}

	private void configurarStatementInsercao(PreparedStatement stmt, ParcelaEmprestimo p) throws SQLException {
		stmt.setInt(1, p.getEmprestimoId());
		stmt.setInt(2, p.getNumeroParcela());
		stmt.setBigDecimal(3, p.getValorParcela());
		stmt.setBigDecimal(4, p.getValorAmortizacao());
		stmt.setBigDecimal(5, p.getValorJuros());
		stmt.setDate(6, java.sql.Date.valueOf(p.getDataVencimento()));
		stmt.setString(7, p.getStatus().name());
	}

	private ParcelaEmprestimo mapResultSetToParcela(ResultSet rs) throws SQLException {
		ParcelaEmprestimo p = new ParcelaEmprestimo(rs.getInt("id"), rs.getInt("emprestimo_id"),
				rs.getInt("numero_parcela"), rs.getBigDecimal("valor_parcela"),
				rs.getDate("data_vencimento").toLocalDate(), StatusParcela.valueOf(rs.getString("status")));

		p.setValorAmortizacao(rs.getBigDecimal("valor_amortizacao"));
		p.setValorJuros(rs.getBigDecimal("valor_juros"));

		Date dataPagamento = rs.getDate("data_pagamento");
		if (dataPagamento != null) {
			p.setDataPagamento(dataPagamento.toLocalDate());
		}

		return p;
	}
}