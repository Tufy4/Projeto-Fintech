package edu.ifsp.banco.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.ifsp.banco.modelo.Usuario;

public class UsuarioDAO {
	
	public void inserir(Usuario usuario) throws Exception {
	    // Comando SQL com a sequência para gerar o ID
	    String sql = """
	        INSERT INTO USUARIOS (ID, NOME, EMAIL, SENHA, TELEFONE, ENDERECO)
	        VALUES (SEQ_USUARIOS.NEXTVAL, ?, ?, ?, ?, ?)
	    """;

	    // Verificar se todos os campos estão válidos
	    if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
	        throw new IllegalArgumentException("Nome não pode ser vazio.");
	    }
	    if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
	        throw new IllegalArgumentException("Email não pode ser vazio.");
	    }
	    if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
	        throw new IllegalArgumentException("Senha não pode ser vazia.");
	    }

	    // Tente conectar e executar o comando SQL
	    try (Connection conn = ConnectionFactory.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        // Defina os parâmetros da query
	        ps.setString(1, usuario.getNome());
	        ps.setString(2, usuario.getEmail());
	        ps.setString(3, usuario.getSenha());
	        ps.setString(4, usuario.getTelefone());
	        ps.setString(5, usuario.getEndereco());

	        // Executar a inserção no banco de dados
	        ps.executeUpdate();
	        
	    } catch (SQLException e) {
	        // Imprimir a causa do erro no log
	        e.printStackTrace();
	        // Rethrow da exceção com a mensagem original
	        throw new Exception("Erro ao inserir usuário: " + e.getMessage(), e);
	    }
	}




    public void atualizar(Usuario usuario) {
        String sql = """
            UPDATE USUARIOS
               SET NOME = ?, EMAIL = ?, SENHA = ?, TELEFONE = ?, ENDERECO = ?, DATA_ULTIMA_ATUALIZACAO = CURRENT_TIMESTAMP
             WHERE ID = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
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

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao excluir usuário.");
        }
    }
}
