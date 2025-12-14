package edu.ifsp.banco.web.usuario.recovery;

import java.io.IOException;

import edu.ifsp.banco.service.UsuarioSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SolicitarRecuperacaoCommand implements Command {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		String email = req.getParameter("email");
		UsuarioSERVICE service = new UsuarioSERVICE();

		try {
			service.solicitarRecuperacaoSenha(email);

			req.setAttribute("msg", "Se o e-mail existir, um link foi enviado.");
			req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("erro", "Erro ao processar solicitação. Tente novamente.");
			try {
				req.getRequestDispatcher("/auth/recuperar_senha.jsp").forward(req, resp);
			} catch (ServletException | IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}