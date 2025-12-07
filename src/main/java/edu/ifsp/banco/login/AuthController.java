package edu.ifsp.banco.login;

import java.io.IOException;
import edu.ifsp.banco.web.Command;
import edu.ifsp.banco.web.PageNotFound;
import edu.ifsp.banco.web.ViewCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/Login", "/logout" })
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Command cmd = switch (request.getServletPath()) {
		case "/Login" -> new ViewCommand("forward:/index.html"); // Página de login
		default -> PageNotFound.getInstance();
		};

		cmd.execute(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Command cmd = switch (request.getServletPath()) {
		case "/Login" -> new LoginCommand(); // Processa o login
		default -> PageNotFound.getInstance(); // Página não encontrada
		};

		cmd.execute(request, response);
	}
}
