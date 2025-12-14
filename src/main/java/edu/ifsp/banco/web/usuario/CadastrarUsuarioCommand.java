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

		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String telefone = request.getParameter("telefone");
		String endereco = request.getParameter("endereco");

		Usuario usuario = new Usuario(nome, email, senha, telefone, endereco);
		UsuarioSERVICE service = new UsuarioSERVICE();

		try {
			service.cadastrarNovoUsuario(usuario);

			request.setAttribute("msg", "Cadastro realizado com sucesso! Faça login.");
			rd = request.getRequestDispatcher("/auth/login.jsp");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("erro", e.getMessage());
			rd = request.getRequestDispatcher("/auth/cadastro.jsp");
		}

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}