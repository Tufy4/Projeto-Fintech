package edu.ifsp.banco.persistencia;

import java.sql.*;
import java.time.LocalDateTime;

public class TokenDAO {

	public void salvarToken(int usuarioId, String token) throws DataAccessException {
		String sql = "INSERT INTO tokens_recuperacao (id, usuario_id, token, data_expiracao) VALUES (seq_tokens.NEXTVAL, ?, ?, ?)";
		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, usuarioId);
			stmt.setString(2, token);
			// Expira em 2 min
			stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now().plusMinutes(2)));
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException("Erro ao salvar token");
		}
	}

	public int buscarUsuarioPorTokenValido(String token) throws DataAccessException {
		String sql = "SELECT usuario_id FROM tokens_recuperacao WHERE token = ? AND utilizado = 0 AND data_expiracao > CURRENT_TIMESTAMP";
		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, token);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getInt("usuario_id");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // Não encontrado ou expirado
	}

	public void invalidarToken(String token) {
		String sql = "UPDATE tokens_recuperacao SET utilizado = 1 WHERE token = ?";
		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, token);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}