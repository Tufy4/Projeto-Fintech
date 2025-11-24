package edu.ifsp.banco.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.modelo.enums.StatusUsuario;
import edu.ifsp.banco.modelo.enums.TipoUsuario;

public class UsuarioDAO {
	
	public Usuario buscarPorEmail(String email) throws DataAccessException {
        String sql = "SELECT * FROM USUARIOS WHERE EMAIL = ?";
        
        try (Connection conn = ConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TipoUsuario perfil = TipoUsuario.valueOf(rs.getString("PERFIL"));
                    StatusUsuario status = StatusUsuario.valueOf(rs.getString("STATUS"));

                    Usuario usuario = new Usuario(
                        rs.getString("NOME"),
                        rs.getString("EMAIL"),
                        rs.getString("SENHA"),
                        rs.getString("TELEFONE"),
                        rs.getString("ENDERECO"),
                        perfil,
                        status
                    );
                    
                    usuario.setId(rs.getInt("ID"));
                    usuario.setDataCriacao(rs.getTimestamp("DATA_CRIACAO"));
                    usuario.setDataAtualizacao(rs.getTimestamp("DATA_ULTIMA_ATUALIZACAO"));
                    
                    return usuario;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Erro ao buscar usuário por email.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new DataAccessException("Erro de consistência: Enum inválido no banco de dados.");
        }
        return null;
    }
	
	public void inserir(Usuario usuario) throws Exception {
	    
	    String sql = """
	        INSERT INTO USUARIOS (ID, NOME, EMAIL, SENHA, TELEFONE, ENDERECO)
	        VALUES (SEQ_USUARIOS.NEXTVAL, ?, ?, ?, ?, ?)
	    """;

	    
	    if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
	        throw new IllegalArgumentException("Nome não pode ser vazio.");
	    }
	    if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
	        throw new IllegalArgumentException("Email não pode ser vazio.");
	    }
	    if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
	        throw new IllegalArgumentException("Senha não pode ser vazia.");
	    }

	   
	    try (Connection conn = ConnectionSingleton.getInstance().getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        
	        ps.setString(1, usuario.getNome());
	        ps.setString(2, usuario.getEmail());
	        ps.setString(3, usuario.getSenha());
	        ps.setString(4, usuario.getTelefone());
	        ps.setString(5, usuario.getEndereco());

	        
	        ps.executeUpdate();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Erro ao inserir usuário: " + e.getMessage(), e);
	    }
	}




    public void atualizar(Usuario usuario) {
        String sql = """
            UPDATE USUARIOS
               SET NOME = ?, EMAIL = ?, SENHA = ?, TELEFONE = ?, ENDERECO = ?, DATA_ULTIMA_ATUALIZACAO = CURRENT_TIMESTAMP
             WHERE ID = ?
        """;

        try (Connection conn = ConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getTelefone());
            ps.setString(5, usuario.getEndereco());

            ps.setInt(8, usuario.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao atualizar usuário.");
        }
    }


    public void excluir(int id) {
        String sql = "DELETE FROM USUARIOS WHERE ID = ?";

        try (Connection conn = ConnectionSingleton.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao excluir usuário.");
        }
    }
}
