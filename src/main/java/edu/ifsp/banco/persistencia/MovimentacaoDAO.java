package edu.ifsp.banco.persistencia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Movimentacoes;

public class MovimentacaoDAO {

	public void inserir(Movimentacoes mov, Conta contaOrigem, Conta contaDestino, BigDecimal valor) {
		String sql = """
				    INSERT INTO MOVIMENTACOES
				    (ID, CONTA_ORIGEM_ID, CONTA_DESTINO_ID, VALOR, TIPO, DATA_TRANSACAO, DESCRICAO, STATUS)
				    VALUES (SEQ_MOVIMENTACOES.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)
				""";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			if (contaOrigem == null) {
				ps.setInt(1, 1);
			} else {
				ps.setInt(1, contaOrigem.getId());
			}

			ps.setInt(2, contaDestino.getId());
			ps.setBigDecimal(3, valor);
			ps.setString(4, "DEPOSITO");
			ps.setTimestamp(5, mov.getDataTransacao());
			ps.setString(6, mov.getDescricao());
			ps.setString(7, "CONCLUIDA");
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException("Erro ao inserir Movimentação.");
		}
	}

	public Movimentacoes buscarPorId(int id) {
		String sql = """
				    SELECT ID, CONTA_ORIGEM, CONTA_DESTINO, VALOR, TIPO, DATA_TRANSACAO, DESCRICAO, STATUS
				    FROM MOVIMENTACOES
				    WHERE ID = ?
				""";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Movimentacoes mov = new Movimentacoes();
					mov.setId(rs.getInt("ID"));
					mov.setContaOrigemId(rs.getInt("CONTA_ORIGEM"));
					mov.setContaDestinoId(rs.getInt("CONTA_DESTINO"));
					mov.setValor(rs.getBigDecimal("VALOR"));
					mov.setTipo(rs.getString("TIPO"));
					mov.setDataTransacao(rs.getTimestamp("DATA_TRANSACAO"));
					mov.setDescricao(rs.getString("DESCRICAO"));
					mov.setStatus(rs.getString("STATUS"));
					return mov;
				}
			}

		} catch (SQLException e) {
			throw new DataAccessException("Erro ao buscar movimentação por ID.");
		}

		return null;
	}

	public ArrayList<Movimentacoes> listarMovimentacoes(int idConta) throws SQLException {
		String sql = """
				    SELECT m.ID, m.CONTA_ORIGEM, m.CONTA_DESTINO, m.VALOR, m.TIPO, m.DATA_TRANSACAO, m.DESCRICAO, m.STATUS
				    FROM MOVIMENTACOES m
				    WHERE m.CONTA_ORIGEM = ? OR m.CONTA_DESTINO = ?
				    ORDER BY m.DATA_TRANSACAO DESC
				""";

		ArrayList<Movimentacoes> movimentacoesList = new ArrayList<>();

		try (Connection conn = ConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, idConta);
			ps.setInt(2, idConta);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Movimentacoes movimentacao = new Movimentacoes();
					movimentacao.setId(rs.getInt("ID"));
					movimentacao.setContaOrigemId(rs.getInt("CONTA_ORIGEM"));
					movimentacao.setContaDestinoId(rs.getInt("CONTA_DESTINO"));
					movimentacao.setValor(rs.getBigDecimal("VALOR"));
					movimentacao.setTipo(rs.getString("TIPO"));
					movimentacao.setDataTransacao(rs.getTimestamp("DATA_TRANSACAO"));
					movimentacao.setDescricao(rs.getString("DESCRICAO"));
					movimentacao.setStatus(rs.getString("STATUS"));

					movimentacoesList.add(movimentacao);
				}
			}

		} catch (SQLException e) {
			throw new SQLException("Erro ao listar movimentações.", e);
		}

		return movimentacoesList;
	}

	public void AtualizarStatus(String StatusMovimentacao, int idMovimentacao) {
		String sql = """
				    UPDATE MOVIMENTACOES
				    SET STATUS = ?
				    WHERE ID = ?
				""";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, StatusMovimentacao);
			ps.setInt(2, idMovimentacao);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException("Erro ao atualizar Movimentação.");
		}
	}

}
