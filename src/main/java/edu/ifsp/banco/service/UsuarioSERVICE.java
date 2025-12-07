package edu.ifsp.banco.service;

import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.modelo.enums.StatusUsuario;
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

	public Usuario login(String email, String senha) throws Exception {
		UsuarioDAO dao = new UsuarioDAO();

		if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
			throw new Exception("Email e senha são obrigatórios.");
		}

		Usuario usuario;
		try {
			usuario = dao.buscarPorEmail(email);
		} catch (DataAccessException e) {
			throw new Exception("Erro técnico ao consultar usuário: " + e.getMessage());
		}

		if (usuario == null) {
			throw new Exception("Usuário ou senha incorretos.");
		}

		if (!usuario.getSenha().equals(senha)) {
			throw new Exception("Usuário ou senha incorretos.");
		}

		if (usuario.getStatus() != StatusUsuario.ATIVO) {
			throw new Exception(
					"Login não permitido: Usuário " + usuario.getStatus() + " Busque seu gerente de conta!");
		}

		return usuario;
	}

}
