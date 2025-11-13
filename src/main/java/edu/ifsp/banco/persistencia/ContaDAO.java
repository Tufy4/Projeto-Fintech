package edu.ifsp.banco.persistencia;

import edu.ifsp.banco.modelo.Conta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO {
	
	

    public void inserir(Conta conta) throws DataAccessException {
        String sql = "INSERT INTO conta (id, usuarioId, agencia, numero_conta, saldo, tipo, status, DataCriacao) "
                   + "VALUES (SEQ_CONTAS.NEXTVAL, ?, ?, SEQ_NUM_CONTA.NEXTVAL, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, conta.getUsuarioId());              
            stmt.setInt(2, conta.getAgencia());                
            stmt.setBigDecimal(3, conta.getSaldo());           
            stmt.setString(4, conta.getTipo().getValor());     
            stmt.setString(5, conta.getStatus().getValor());   
            stmt.setTimestamp(6, conta.getDataCriacao());     

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Erro ao inserir conta");
        }
    }
    
    
    

    public Conta buscarPorNumero(int numeroConta) throws DataAccessException {
        String sql = "SELECT * FROM conta WHERE numero_conta = ?";
        Conta conta = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numeroConta);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                conta = new Conta(
                    rs.getInt("id"),
                    rs.getInt("usuarioId"),
                    rs.getInt("agencia"),
                    rs.getInt("numero_conta"),
                    rs.getBigDecimal("saldo"),
                    Conta.tiposConta.valueOf(rs.getString("tipo")),
                    Conta.statusConta.valueOf(rs.getString("status")),
                    rs.getTimestamp("DataCriacao")
                );
            }

        } catch (SQLException e) {
            throw new DataAccessException("Erro ao buscar conta");
        }

        return conta;
    }
    
    
    
    

    public List<Conta> listar() throws DataAccessException {
        List<Conta> contas = new ArrayList<>();
        String sql = "SELECT * FROM contas";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Conta c = new Conta(
                    rs.getInt("id"),
                    rs.getInt("usuarioId"),
                    rs.getInt("agencia"),
                    rs.getInt("numero_conta"),
                    rs.getBigDecimal("saldo"),
                    Conta.tiposConta.valueOf(rs.getString("tipo")),
                    Conta.statusConta.valueOf(rs.getString("status")),
                    rs.getTimestamp("DataCriacao")
                );
                contas.add(c);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Erro ao listar contas");
        }

        return contas;
    }

    
    
    
    public void atualizarSaldo(int idConta, double novoSaldo) throws DataAccessException {
        String sql = "UPDATE conta SET saldo = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, novoSaldo);
            stmt.setInt(2, idConta);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Erro ao atualizar saldo");
        }
    }
}
