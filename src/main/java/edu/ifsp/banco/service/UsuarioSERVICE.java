package edu.ifsp.banco.service;

import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.persistencia.DataAccessException;
import edu.ifsp.banco.persistencia.UsuarioDAO;

public class UsuarioSERVICE {

	public void criarConta(Usuario user) throws Exception {
		UsuarioDAO dao = new UsuarioDAO();
		if (user == null) {
			throw new Exception("Usuario inválida");
		}

		try {
			dao.inserir(user);
		} catch (DataAccessException e) {
			throw new Exception("Erro ao criar usuario: " + e.getMessage());
		}
	}

	public void AtualizarConta(Usuario user) throws Exception {
		UsuarioDAO dao = new UsuarioDAO();
		if (user == null) {
			throw new Exception("Usuario inválida");
		}

		try {
			dao.atualizar(user);
		} catch (DataAccessException e) {
			throw new Exception("Erro ao criar usuario: " + e.getMessage());
		}
	}

}
