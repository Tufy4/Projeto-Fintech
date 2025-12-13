package edu.ifsp.banco.web.usuario.recovery;

import java.io.IOException;
import edu.ifsp.banco.persistencia.TokenDAO;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ValidarTokenCommand implements Command {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		String token = req.getParameter("token");
		TokenDAO dao = new TokenDAO();

		int idUsuario = dao.buscarUsuarioPorTokenValido(token);
		try {
			if (idUsuario != -1) {
				req.setAttribute("tokenValido", token);
				req.setAttribute("idUsuario", idUsuario);

				req.getRequestDispatcher("/auth/nova_senha.jsp").forward(req, resp);
			} else {
				req.setAttribute("erro", "Link expirado ou inválido.");
				req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
