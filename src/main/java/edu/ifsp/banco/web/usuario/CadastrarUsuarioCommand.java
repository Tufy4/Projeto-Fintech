package edu.ifsp.banco.web.usuario;

import java.io.IOException;

import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.service.UsuarioSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CadastrarUsuarioCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd;

		String nome = request.getParameter("name");
		String email = request.getParameter("email");
		String senha = request.getParameter("password");
		String telefone = request.getParameter("telefone");
		String endereco = request.getParameter("endereco");

		Usuario usuario = new Usuario(nome, email, senha, telefone, endereco);
		UsuarioSERVICE service = new UsuarioSERVICE();

		try {
			service.criarConta(usuario);
			rd = request.getRequestDispatcher("index.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			rd = request.getRequestDispatcher("erro.jsp");
		}

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
