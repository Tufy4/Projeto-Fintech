package edu.ifsp.banco.web.usuario;

import java.io.IOException;

import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.service.UsuarioSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DadosUsuarioCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		try {
			int id = Integer.parseInt(request.getParameter("id"));

			UsuarioSERVICE service = new UsuarioSERVICE();
			Usuario user = service.buscarPorId(id);

			request.setAttribute("userEditar", user);

			request.getRequestDispatcher("app/usuarios/EditarUser.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("erro", "Erro ao carregar dados: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/app?command=dashboardCliente");
		}
	}
}