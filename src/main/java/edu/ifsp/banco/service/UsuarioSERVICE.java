package edu.ifsp.banco.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.modelo.enums.StatusUsuario;
import edu.ifsp.banco.modelo.enums.TiposConta;
import edu.ifsp.banco.persistencia.ConnectionSingleton;
import edu.ifsp.banco.persistencia.ContaDAO;
import edu.ifsp.banco.persistencia.DataAccessException;
import edu.ifsp.banco.persistencia.TokenDAO;
import edu.ifsp.banco.persistencia.UsuarioDAO;

public class UsuarioSERVICE {

	public void cadastrarNovoUsuario(Usuario user) throws Exception {
		if (user == null) {
			throw new Exception("Usuário inválido");
		}

		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			try {
				UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
				ContaDAO contaDAO = new ContaDAO(conn);

				usuarioDAO.inserir(user);

				Usuario userSalvo = usuarioDAO.buscarPorEmail(user.getEmail());

				if (userSalvo == null) {
					throw new Exception("Erro fatal: Usuário não encontrado após inserção.");
				}

				Conta conta = new Conta();
				conta.setUsuarioId(userSalvo.getId());
				conta.setTipo(TiposConta.CLIENTE);
				contaDAO.inserir(conta);

				conn.commit();

			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				throw new Exception("Erro no cadastro (rollback realizadoo): " + e.getMessage());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Erro de conexao com o banco de dados.");
		}
	}

	public void atualizarDados(Usuario user, int id) throws Exception {
		if (user == null)
			throw new Exception("Dados inválidos");

		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			try {
				UsuarioDAO dao = new UsuarioDAO(conn);
				dao.atualizar(user, id);
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				throw new Exception("Erro ao atualizar usuário: " + e.getMessage());
			}
		}
	}

	public Usuario login(String email, String senha) throws Exception {
		if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
			throw new Exception("Email e senha são obrigatórios.");
		}

		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			UsuarioDAO dao = new UsuarioDAO(conn);
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
				throw new Exception("Login não permitido: Usuário " + usuario.getStatus());
			}

			return usuario;
		}
	}

	public Usuario buscarPorId(int id) throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			UsuarioDAO dao = new UsuarioDAO(conn);
			return dao.buscarPorId(id);
		}
	}

	public void solicitarRecuperacaoSenha(String email) throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
			TokenDAO tokenDAO = new TokenDAO(conn);

			Usuario usuario = usuarioDAO.buscarPorEmail(email);

			if (usuario != null) {
				String token = UUID.randomUUID().toString();
				tokenDAO.salvarToken(usuario.getId(), token);

				EmailService emailService = new EmailService();
				emailService.enviarEmailRecuperacao(usuario.getEmail(), token);
			}
		}
	}

	public int validarTokenRecuperacao(String token) throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			TokenDAO tokenDAO = new TokenDAO(conn);
			return tokenDAO.buscarUsuarioPorTokenValido(token);
		}
	}

	public void redefinirSenha(int idUsuario, String token, String novaSenha) throws Exception {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			try {
				TokenDAO tokenDAO = new TokenDAO(conn);
				UsuarioDAO usuarioDAO = new UsuarioDAO(conn);

				int idValidado = tokenDAO.buscarUsuarioPorTokenValido(token);

				if (idValidado != idUsuario) {
					throw new Exception("Token inválido ou expirado durante o processamento.");
				}

				usuarioDAO.atualizarSenha(idUsuario, novaSenha);
				tokenDAO.invalidarToken(token);

				conn.commit();

			} catch (Exception e) {
				conn.rollback();
				throw e;
			}
		}
	}
}