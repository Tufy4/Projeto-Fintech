package edu.ifsp.banco.web.usuario;

import java.io.IOException;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.modelo.enums.TiposConta;
import edu.ifsp.banco.persistencia.ContaDAO;
import edu.ifsp.banco.persistencia.UsuarioDAO;
import edu.ifsp.banco.service.ContaSERVICE;
import edu.ifsp.banco.service.UsuarioSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AtualizarUsuarioCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		ContaDAO dao = new ContaDAO();
		Conta conta = dao.buscarPorIdUsuario(id);

		response.sendRedirect("app/admin/home.jsp");

	}
}