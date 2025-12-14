package edu.ifsp.banco.web.usuario.recovery;

import java.io.IOException;
import edu.ifsp.banco.persistencia.TokenDAO;
import edu.ifsp.banco.persistencia.UsuarioDAO;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RedefinirSenhaCommand implements Command {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		String novaSenha = req.getParameter("senha");
		String token = req.getParameter("token");
		int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));
		try {
			TokenDAO tokenDAO = new TokenDAO();
			if (tokenDAO.buscarUsuarioPorTokenValido(token) == idUsuario) {
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				usuarioDAO.atualizarSenha(idUsuario, novaSenha);
				tokenDAO.invalidarToken(token);
				req.setAttribute("msg", "Senha alterada com sucesso! Faça login.");
				req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
			} else {
				req.setAttribute("erro", "Erro na validação do token.");
				req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
			}
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
