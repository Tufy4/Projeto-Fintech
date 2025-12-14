package edu.ifsp.banco.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import edu.ifsp.banco.modelo.Movimentacoes;

public class MovimentacaoDAO {

	private Connection connection;

	public MovimentacaoDAO(Connection connection) {
		this.connection = connection;
	}

	public void inserir(Movimentacoes mov) throws DataAccessException {
		String sql = """
				   INSERT INTO MOVIMENTACOES
				   (ID, CONTA_ORIGEM_ID, CONTA_DESTINO_ID, VALOR, SALDO_ANTERIOR, SALDO_POSTERIOR, TIPO, DATA_TRANSACAO, DESCRICAO, STATUS)
				   VALUES (SEQ_MOVIMENTACOES.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {

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
			ps.setBigDecimal(4, mov.getSaldoAnterior());
			ps.setBigDecimal(5, mov.getSaldoPosterior());
			ps.setString(6, mov.getTipo().toString());
			ps.setTimestamp(7, mov.getDataTransacao());
			ps.setString(8, mov.getDescricao());
			ps.setString(9, mov.getStatus().toString());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Erro ao inserir Movimentação: " + e.getMessage());
		}
	}

	public Movimentacoes buscarPorId(int id) throws DataAccessException {
		String sql = """
				   SELECT ID, CONTA_ORIGEM_ID, CONTA_DESTINO_ID, VALOR,
				          SALDO_ANTERIOR, SALDO_POSTERIOR,
				          TIPO, DATA_TRANSACAO, DESCRICAO, STATUS
				   FROM MOVIMENTACOES
				   WHERE ID = ?
				""";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapResultSetToMovimentacao(rs);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Erro ao buscar movimentação por ID.");
		}

		return null;
	}

	public ArrayList<Movimentacoes> listarMovimentacoes(int idConta, java.sql.Date dataInicio, java.sql.Date dataFim)
			throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT m.ID, m.CONTA_ORIGEM_ID, m.CONTA_DESTINO_ID, m.VALOR, ");
		sql.append("       m.SALDO_ANTERIOR, m.SALDO_POSTERIOR, ");
		sql.append("       m.TIPO, m.DATA_TRANSACAO, m.DESCRICAO, m.STATUS ");
		sql.append("FROM MOVIMENTACOES m ");
		sql.append("WHERE ( ");
		sql.append("  (m.CONTA_ORIGEM_ID = ? AND m.TIPO <> 'TRANSFERENCIA_RECEBIDA') ");
		sql.append("  OR ");
		sql.append("  (m.CONTA_DESTINO_ID = ? AND m.TIPO <> 'TRANSFERENCIA_ENVIADA') ");
		sql.append(") ");

		if (dataInicio != null) {
			sql.append("AND TRUNC(m.DATA_TRANSACAO) >= ? ");
		}
		if (dataFim != null) {
			sql.append("AND TRUNC(m.DATA_TRANSACAO) <= ? ");
		}

		sql.append("ORDER BY m.DATA_TRANSACAO DESC");

		ArrayList<Movimentacoes> movimentacoesList = new ArrayList<>();

		try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {

			int index = 1;
			ps.setInt(index++, idConta);
			ps.setInt(index++, idConta);

			if (dataInicio != null) {
				ps.setDate(index++, dataInicio);
			}
			if (dataFim != null) {
				ps.setDate(index++, dataFim);
			}

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					movimentacoesList.add(mapResultSetToMovimentacao(rs));
				}
			}

		} catch (SQLException e) {
			throw new SQLException("Erro ao listar movimentações com filtro.", e);
		}

		return movimentacoesList;
	}

	public ArrayList<Movimentacoes> listarMovimentacoes(int idConta) throws SQLException {
		return listarMovimentacoes(idConta, null, null);
	}

	public void AtualizarStatus(String StatusMovimentacao, int idMovimentacao) throws DataAccessException {
		String sql = """
				   UPDATE MOVIMENTACOES
				   SET STATUS = ?
				   WHERE ID = ?
				""";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, StatusMovimentacao);
			ps.setInt(2, idMovimentacao);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException("Erro ao atualizar Movimentação.");
		}
	}

	private Movimentacoes mapResultSetToMovimentacao(ResultSet rs) throws SQLException {
		Movimentacoes mov = new Movimentacoes();
		mov.setId(rs.getInt("ID"));
		mov.setContaOrigemId(rs.getInt("CONTA_ORIGEM_ID"));
		mov.setContaDestinoId(rs.getInt("CONTA_DESTINO_ID"));
		mov.setValor(rs.getBigDecimal("VALOR"));
		mov.setSaldoAnterior(rs.getBigDecimal("SALDO_ANTERIOR"));
		mov.setSaldoPosterior(rs.getBigDecimal("SALDO_POSTERIOR"));
		mov.setTipo(edu.ifsp.banco.modelo.enums.TipoMovimentacao.valueOf(rs.getString("TIPO")));
		mov.setStatus(edu.ifsp.banco.modelo.enums.StatusMovimentacao.valueOf(rs.getString("STATUS")));
		mov.setDataTransacao(rs.getTimestamp("DATA_TRANSACAO"));
		mov.setDescricao(rs.getString("DESCRICAO"));

		return mov;
	}
}