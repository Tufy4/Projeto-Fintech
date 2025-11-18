package edu.ifsp.banco.web;

import java.io.IOException;

import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.service.UsuarioSERVICE;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Cadastrar")
public class CadastrarUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String name = request.getParameter("name");
		  String email = request.getParameter("email");
          String password = request.getParameter("password");
          String telefone = request.getParameter("telefone");
          String endereco = request.getParameter("endereco");
          
          Usuario user = new Usuario( name, email, password, telefone, endereco);
          UsuarioSERVICE Service = new UsuarioSERVICE();
          try {
			Service.criarConta(user);
			response.sendRedirect("index.jsp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
       
   

}
