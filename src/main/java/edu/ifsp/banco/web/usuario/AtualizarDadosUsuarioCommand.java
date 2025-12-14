package edu.ifsp.banco.web.usuario;

import java.io.IOException;

import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.service.UsuarioSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AtualizarDadosUsuarioCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String nome = request.getParameter("nome");
		String endereco = request.getParameter("endereco");
		String telefone = request.getParameter("telefone");
		int id = Integer.parseInt(request.getParameter("id"));

		if (isNullOrEmpty(email) || isNullOrEmpty(password) || isNullOrEmpty(nome)) {
			request.getSession().setAttribute("erroAtualizar", "Email, nome e senha são obrigatórios.");
			response.sendRedirect(request.getContextPath() + "/app/usuarios/EditarUser.jsp?id=" + id);
			return;
		}

		Usuario user = new Usuario();
		user.setEmail(email);
		user.setSenha(password);
		user.setNome(nome);
		user.setEndereco(endereco);
		user.setTelefone(telefone);

		UsuarioSERVICE service = new UsuarioSERVICE();

		try {
			service.AtualizarConta(user, id);
		} catch (Exception e) {
			request.getSession().setAttribute("erroAtualizar", "Erro ao atualizar os dados.");
			response.sendRedirect(request.getContextPath() + "/app/usuarios/EditarUser.jsp?id=" + id);
			return;
		}

		HttpSession session = request.getSession(false);
		session.setAttribute("usuarioLogado", user);

		response.sendRedirect(request.getContextPath() + "/app?command=dashboardCliente");
	}

	private boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}
}
