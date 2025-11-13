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
	
	 public void inserir(Movimentacoes movimentacao,Conta contaOrigem, Conta contaDestino, BigDecimal valorTransitado) {
	        String sql = """
	            INSERT INTO movimentacao (ID, CONTA_ORIGEM, CONTA_DESTINO, VALOR, TIPO, DATA_TRANSACAO, DESCRICAO, STATUS)
	            VALUES (SEQ_MOVIMENTACOES.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)
	        """;

	        try (Connection conn = ConnectionFactory.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setInt(1, contaOrigem.getId());
	            ps.setInt(2, contaDestino.getId());
	            ps.setBigDecimal(3, valorTransitado);
	            ps.setString(4, movimentacao.getTipo());
	            ps.setTimestamp(5, movimentacao.getDataTransacao());
	            ps.setString(6, movimentacao.getDescricao());
	            ps.setString(7, movimentacao.getStatus());
	            ps.executeUpdate();
	            
	        } catch (SQLException e) {
	            throw new DataAccessException("Erro ao inserir Movimentação.");
	        }
	    }
	 
	 
	 public ArrayList<Movimentacoes> listarMovimentacoes(int idConta) throws SQLException {
		    String sql = """
		        SELECT m.ID, m.CONTA_ORIGEM, m.CONTA_DESTINO, m.VALOR, m.TIPO, m.DATA_TRANSACAO, m.DESCRICAO, m.STATUS
		        FROM movimentacao m
		        WHERE m.CONTA_ORIGEM = ? OR m.CONTA_DESTINO = ?
		        ORDER BY m.DATA_TRANSACAO DESC
		    """;

		    ArrayList<Movimentacoes> movimentacoesList = new ArrayList<>();

		    try (Connection conn = ConnectionFactory.getConnection();
		         PreparedStatement ps = conn.prepareStatement(sql)) {

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

	 
	 
		
		 public void AtualizarStatus(String StatusMovimentacao,int idMovimentacao) {
			 String sql = """
			            UPDATE movimentacao
			            SET STATUS = ?
			            WHERE ID = ?
			        """;

		        try (Connection conn = ConnectionFactory.getConnection();
		             PreparedStatement ps = conn.prepareStatement(sql)) {

		            ps.setInt(1, idMovimentacao);
		            ps.setString(2, StatusMovimentacao);
		          
		            
		            ps.executeUpdate();
		            
		        } catch (SQLException e) {
		            throw new DataAccessException("Erro ao atualizar Movimentação.");
		        }
		 }
		 
}


