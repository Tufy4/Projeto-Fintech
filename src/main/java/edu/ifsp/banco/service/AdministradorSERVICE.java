package edu.ifsp.banco.service;

import java.sql.Connection;
import java.util.List;

import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.persistencia.AdministradorDAO;
import edu.ifsp.banco.persistencia.ConnectionSingleton;

public class AdministradorSERVICE {

	public List<Usuario> consulta() throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			AdministradorDAO dao = new AdministradorDAO(conn);
			List<Usuario> usuarios = dao.listarUsuariosBloqueados();

			if (usuarios.isEmpty()) {
				throw new Exception("Não tem usuarios bloqueados");
			}
			return usuarios;
		}
	}

	public void liberar(int id) throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			AdministradorDAO dao = new AdministradorDAO(conn);
			if (!dao.liberarUsuario(id)) {
				throw new Exception("Deu problema na liberação do usuario");
			}
		}
	}

	public int obterQuantidadeUsuariosBloqueados() {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			AdministradorDAO dao = new AdministradorDAO(conn);
			return dao.contarBloqueados();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}