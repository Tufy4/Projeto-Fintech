package edu.ifsp.banco.administrador;

import java.io.IOException;
import edu.ifsp.banco.service.AdministradorSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LiberarUsuarioCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idParam = request.getParameter("id");

		if (idParam == null || idParam.isEmpty()) {
			response.sendRedirect("app?command=dashboardAdmin");
			return;
		}

		try {
			int id = Integer.parseInt(idParam);
			AdministradorSERVICE service = new AdministradorSERVICE();

			service.liberar(id);

			response.sendRedirect("app?command=dashboardAdmin");

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("app?command=dashboardAdmin");
		}
	}
}