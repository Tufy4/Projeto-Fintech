package edu.ifsp.banco.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import edu.ifsp.banco.modelo.Movimentacoes;

public class MovimentacaoDAO {

	public void inserir(Movimentacoes mov) throws DataAccessException {
		String sql = """
				   INSERT INTO MOVIMENTACOES
				   (ID, CONTA_ORIGEM_ID, CONTA_DESTINO_ID, VALOR, TIPO, DATA_TRANSACAO, DESCRICAO, STATUS)
				   VALUES (SEQ_MOVIMENTACOES.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)
				""";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			if (mov.getContaOrigemId() == 0) {
				ps.setNull(1, Types.INTEGER);
			} else {
				ps.setInt(1, mov.getContaOrigemId());
			}

			if (mov.getContaDestinoId() == 0) {
				ps.setNull(2, Types.INTEGER);
			} else {
				ps.setInt(2, mov.getContaDestinoId());
			}

			ps.setBigDecimal(3, mov.getValor());
			ps.setString(4, mov.getTipo().toString());
			ps.setTimestamp(5, mov.getDataTransacao());
			ps.setString(6, mov.getDescricao());
			ps.setString(7, mov.getStatus().toString());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Erro ao inserir Movimentação: " + e.getMessage());
		}
	}

	public Movimentacoes buscarPorId(int id) throws DataAccessException {
		String sql = """
				   SELECT ID, CONTA_ORIGEM_ID, CONTA_DESTINO_ID, VALOR, TIPO, DATA_TRANSACAO, DESCRICAO, STATUS
				   FROM MOVIMENTACOES
				   WHERE ID = ?
				""";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Movimentacoes mov = new Movimentacoes();
					mov.setId(rs.getInt("ID"));
					mov.setContaOrigemId(rs.getInt("CONTA_ORIGEM_ID"));
					mov.setContaDestinoId(rs.getInt("CONTA_DESTINO_ID"));
					mov.setValor(rs.getBigDecimal("VALOR"));

					mov.setTipo(edu.ifsp.banco.modelo.enums.TipoMovimentacao.valueOf(rs.getString("TIPO")));
					mov.setStatus(edu.ifsp.banco.modelo.enums.StatusMovimentacao.valueOf(rs.getString("STATUS")));

					mov.setDataTransacao(rs.getTimestamp("DATA_TRANSACAO"));
					mov.setDescricao(rs.getString("DESCRICAO"));

					return mov;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Erro ao buscar movimentação por ID.");
		}

		return null;
	}

	public ArrayList<Movimentacoes> listarMovimentacoes(int idConta) throws SQLException {
		String sql = """
				   SELECT m.ID, m.CONTA_ORIGEM_ID, m.CONTA_DESTINO_ID, m.VALOR, m.TIPO, m.DATA_TRANSACAO, m.DESCRICAO, m.STATUS
				   FROM MOVIMENTACOES m
				   WHERE m.CONTA_ORIGEM_ID = ? OR m.CONTA_DESTINO_ID = ?
				   ORDER BY m.DATA_TRANSACAO DESC
				""";

		ArrayList<Movimentacoes> movimentacoesList = new ArrayList<>();

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, idConta);
			ps.setInt(2, idConta);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Movimentacoes mov = new Movimentacoes();
					mov.setId(rs.getInt("ID"));
					mov.setContaOrigemId(rs.getInt("CONTA_ORIGEM_ID"));
					mov.setContaDestinoId(rs.getInt("CONTA_DESTINO_ID"));
					mov.setValor(rs.getBigDecimal("VALOR"));

					mov.setTipo(edu.ifsp.banco.modelo.enums.TipoMovimentacao.valueOf(rs.getString("TIPO")));
					mov.setStatus(edu.ifsp.banco.modelo.enums.StatusMovimentacao.valueOf(rs.getString("STATUS")));

					mov.setDataTransacao(rs.getTimestamp("DATA_TRANSACAO"));
					mov.setDescricao(rs.getString("DESCRICAO"));

					movimentacoesList.add(mov);
				}
			}

		} catch (SQLException e) {
			throw new SQLException("Erro ao listar movimentações.", e);
		}

		return movimentacoesList;
	}

	public void AtualizarStatus(String StatusMovimentacao, int idMovimentacao) throws DataAccessException {
		String sql = """
				   UPDATE MOVIMENTACOES
				   SET STATUS = ?
				   WHERE ID = ?
				""";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, StatusMovimentacao);
			ps.setInt(2, idMovimentacao);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException("Erro ao atualizar Movimentação.");
		}
	}
}