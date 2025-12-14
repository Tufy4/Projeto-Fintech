package edu.ifsp.banco.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.modelo.enums.StatusUsuario;

public class AdministradorDAO {

	public List<Usuario> listarUsuariosBloqueados() {
		List<Usuario> usuarios = new ArrayList<>();
		String sql = "SELECT ID, NOME, EMAIL, TELEFONE, ENDERECO, STATUS FROM USUARIOS WHERE STATUS = 'BLOQUEADO'";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Usuario u = new Usuario();
				u.setId(rs.getInt("ID"));
				u.setNome(rs.getString("NOME"));
				u.setEmail(rs.getString("EMAIL"));
				u.setTelefone(rs.getString("TELEFONE"));
				u.setEndereco(rs.getString("ENDERECO"));
				u.setStatus(StatusUsuario.valueOf(rs.getString("STATUS")));
				usuarios.add(u);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuarios;
	}

	public Usuario ProcurarUsuarioId(int id) {
		Usuario u = null;
		String sql = "SELECT ID, NOME, EMAIL, TELEFONE, ENDERECO, STATUS FROM USUARIOS WHERE ID = ?";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					u = new Usuario();
					u.setId(rs.getInt("ID"));
					u.setNome(rs.getString("NOME"));
					u.setEmail(rs.getString("EMAIL"));
					u.setTelefone(rs.getString("TELEFONE"));
					u.setEndereco(rs.getString("ENDERECO"));
					u.setStatus(StatusUsuario.valueOf(rs.getString("STATUS")));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return u;
	}

	public boolean liberarUsuario(int id) {
		String sql = "UPDATE USUARIOS SET STATUS = 'ATIVO', DATA_ULTIMA_ATUALIZACAO = CURRENT_TIMESTAMP WHERE ID = ? AND STATUS = 'BLOQUEADO'";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int contarBloqueados() {
		String sql = "SELECT COUNT(*) FROM USUARIOS WHERE STATUS = 'BLOQUEADO'";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception e) {
			System.err.println("Erro ao contar usuários bloqueados: " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
}