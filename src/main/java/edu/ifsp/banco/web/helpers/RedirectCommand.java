package edu.ifsp.banco.web.helpers;

import java.io.IOException;

import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.service.UsuarioSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RedirectCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd;

		String url = request.getParameter("url");

		rd = request.getRequestDispatcher(url);

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
