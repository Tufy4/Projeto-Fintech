package edu.ifsp.banco.web.usuario;

import java.io.IOException;

import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.persistencia.UsuarioDAO;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DadosUsuarioCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		UsuarioDAO dao = new UsuarioDAO();
		Usuario user = dao.buscarPorId(id);

		HttpSession session = request.getSession(false);
		session.setAttribute("userEditar", user);
		response.sendRedirect("app/usuarios/EditarUser.jsp");
	}
}