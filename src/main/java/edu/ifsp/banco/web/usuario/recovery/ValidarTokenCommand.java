package edu.ifsp.banco.web.usuario.recovery;

import java.io.IOException;

import edu.ifsp.banco.service.UsuarioSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ValidarTokenCommand implements Command {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		String token = req.getParameter("token");
		UsuarioSERVICE service = new UsuarioSERVICE();

		try {
			int idUsuario = service.validarTokenRecuperacao(token);

			if (idUsuario != -1) {
				req.setAttribute("tokenValido", token);
				req.setAttribute("idUsuario", idUsuario);
				req.getRequestDispatcher("/auth/nova_senha.jsp").forward(req, resp);
			} else {
				req.setAttribute("erro", "Link expirado ou inválido.");
				req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("erro", "Erro técnico ao validar token.");
			try {
				req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
			} catch (ServletException | IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}