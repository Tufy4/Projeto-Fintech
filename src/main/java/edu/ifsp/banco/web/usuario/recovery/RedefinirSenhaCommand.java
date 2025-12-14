package edu.ifsp.banco.web.usuario.recovery;

import java.io.IOException;

import edu.ifsp.banco.service.UsuarioSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RedefinirSenhaCommand implements Command {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String novaSenha = req.getParameter("senha");
			String token = req.getParameter("token");
			int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));

			UsuarioSERVICE service = new UsuarioSERVICE();

			service.redefinirSenha(idUsuario, token, novaSenha);

			req.setAttribute("msg", "Senha alterada com sucesso! Faça login.");
			req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("erro", "Erro ao redefinir senha: " + e.getMessage());
			try {
				req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
			} catch (ServletException | IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}