package edu.ifsp.banco.persistencia;

import java.sql.*;
import java.time.LocalDateTime;

public class TokenDAO {

	private Connection connection;

	public TokenDAO(Connection connection) {
		this.connection = connection;
	}

	public void salvarToken(int usuarioId, String token) throws DataAccessException {
		String sql = "INSERT INTO tokens_recuperacao (id, usuario_id, token, data_expiracao) VALUES (seq_tokens.NEXTVAL, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, usuarioId);
			stmt.setString(2, token);
			// Expira em 2 minutos
			stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now().plusMinutes(2)));
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException("Erro ao salvar token: " + e.getMessage());
		}
	}

	public int buscarUsuarioPorTokenValido(String token) throws DataAccessException {
		String sql = "SELECT usuario_id FROM tokens_recuperacao WHERE token = ? AND utilizado = 0 AND data_expiracao > CURRENT_TIMESTAMP";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setString(1, token);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getInt("usuario_id");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // Não encontrado ou expirado
	}

	public void invalidarToken(String token) throws DataAccessException {
		String sql = "UPDATE tokens_recuperacao SET utilizado = 1 WHERE token = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, token);
			stmt.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException("Erro ao invalidar token: " + e.getMessage());
		}
	}
}