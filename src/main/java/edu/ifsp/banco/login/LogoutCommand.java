package edu.ifsp.banco.login;

import java.io.IOException;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutCommand implements Command {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Obtém a sessão atual (não cria uma nova se não existir)
		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate(); // encerra a sessão
		}

		// Redireciona para a página de login ou home
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
}
