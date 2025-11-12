package edu.ifsp.banco.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.ifsp.banco.modelo.Usuario;

public class UsuarioDAO {
	
    public void inserir(Usuario usuario) {
        String sql = """
            INSERT INTO USUARIOS (ID, NOME, EMAIL, SENHA, TELEFONE, ENDERECO, PERFIL, STATUS)
            VALUES (SEQ_USUARIOS.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getTelefone());
            ps.setString(5, usuario.getEndereco());
            ps.setString(6, usuario.getPerfil().getValor());
            ps.setString(7, usuario.getStatus().getValor());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao inserir usuário.");
        }
    }


    public void atualizar(Usuario usuario) {
        String sql = """
            UPDATE USUARIOS
               SET NOME = ?, EMAIL = ?, SENHA = ?, TELEFONE = ?, ENDERECO = ?, 
                   PERFIL = ?, STATUS = ?, DATA_ULTIMA_ATUALIZACAO = CURRENT_TIMESTAMP
             WHERE ID = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getTelefone());
            ps.setString(5, usuario.getEndereco());
            ps.setString(6, usuario.getPerfil().getValor());
            ps.setString(7, usuario.getStatus().getValor());
            ps.setInt(8, usuario.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao atualizar usuário.");
        }
    }


    public void excluir(int id) {
        String sql = "DELETE FROM USUARIOS WHERE ID = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao excluir usuário.");
        }
    }
}
