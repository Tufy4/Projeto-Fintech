package edu.ifsp.banco.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.modelo.enums.StatusUsuario;
import edu.ifsp.banco.modelo.enums.TipoUsuario;
import edu.ifsp.banco.modelo.enums.TiposConta;

public class AdministradorDAO {
	public List<Usuario> listarUsuariosBloqueados() {
		List<Usuario> usuarios = new ArrayList<>();

		String sql = "SELECT ID, NOME, EMAIL, TELEFONE, ENDERECO, PERFIL, STATUS FROM USUARIOS WHERE STATUS = 'BLOQUEADO'";

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
				u.setPerfil(TipoUsuario.valueOf(rs.getString("PERFIL")));
				u.setStatus(StatusUsuario.valueOf(rs.getString("STATUS")));

				usuarios.add(u);
				System.out.println(u);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return usuarios;
	}

	public Usuario ProcurarUsuarioId(int id) {
		Usuario u = new Usuario();
		;
		String sql = "SELECT ID, NOME, EMAIL, TELEFONE, ENDERECO, PERFIL, STATUS FROM USUARIOS WHERE ID = ?";

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);

				ResultSet rs = stmt.executeQuery()) {
			stmt.setInt(1, id);
			if (rs.next()) {
				u.setId(rs.getInt("ID"));
				u.setNome(rs.getString("NOME"));
				u.setEmail(rs.getString("EMAIL"));
				u.setTelefone(rs.getString("TELEFONE"));
				u.setEndereco(rs.getString("ENDERECO"));
				u.setPerfil(TipoUsuario.valueOf(rs.getString("PERFIL")));
				u.setStatus(StatusUsuario.valueOf(rs.getString("STATUS")));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return u;
	}

	public boolean liberarUsuario(int id) {
		String sql = "UPDATE USUARIOS SET STATUS = 'ATIVO', DATA_ULTIMA_ATUALIZACAO = CURRENT_TIMESTAMP WHERE ID = ? AND STATUS = 'BLOQUEADO'";
		int linhasAfetadas = 0;

		try (Connection conn = ConnectionSingleton.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);

			linhasAfetadas = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return linhasAfetadas > 0;
	}

}
