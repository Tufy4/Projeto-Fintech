package edu.ifsp.banco.web.usuario.recovery;

import java.io.IOException;
import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.persistencia.TokenDAO;
import edu.ifsp.banco.persistencia.UsuarioDAO;
import edu.ifsp.banco.service.EmailService;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SolicitarRecuperacaoCommand implements Command {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		String email = req.getParameter("email");

		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = usuarioDAO.buscarPorEmail(email);

		if (usuario != null) {
			String token = java.util.UUID.randomUUID().toString();
			TokenDAO tokenDAO = new TokenDAO();
			tokenDAO.salvarToken(usuario.getId(), token);

			EmailService emailService = new EmailService();
			emailService.enviarEmailRecuperacao(usuario.getEmail(), token);
		}

		req.setAttribute("msg", "Se o e-mail existir, um link foi enviado.");
		try {
			req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
