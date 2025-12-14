package edu.ifsp.banco.login;

import java.io.IOException;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.service.ContaSERVICE;
import edu.ifsp.banco.service.UsuarioSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userEmail = request.getParameter("user");
		String password = request.getParameter("password");

		UsuarioSERVICE usuarioService = new UsuarioSERVICE();
		ContaSERVICE contaService = new ContaSERVICE();

		try {
			Usuario usuario = usuarioService.login(userEmail, password);

			HttpSession session = request.getSession();
			session.setAttribute("usuarioLogado", usuario);

			List<Conta> contas = contaService.buscarContasPorUsuario(usuario.getId());

			if (contas.isEmpty()) {
				throw new Exception("Usuário sem contas ativas. Contate o suporte.");
			}

			request.setAttribute("listaContas", contas);
			request.getRequestDispatcher("/auth/selecao_conta.jsp").forward(request, response);

		} catch (Exception e) {
			request.setAttribute("erro", e.getMessage());
			request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
		}
	}
}