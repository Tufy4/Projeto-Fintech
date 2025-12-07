package edu.ifsp.banco.login;

import java.io.IOException;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.modelo.enums.TipoUsuario;
import edu.ifsp.banco.persistencia.ContaDAO;
import edu.ifsp.banco.service.UsuarioSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userEmail = request.getParameter("user");
        String password = request.getParameter("password");
        
        UsuarioSERVICE service = new UsuarioSERVICE();

        try {
            Usuario usuario = service.login(userEmail, password);
            ContaDAO dao = new ContaDAO();
            
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogado", usuario);
            
            System.out.println("Login efetuado: " + usuario.getEmail() + " - " + usuario.getPerfil());
            
            Conta conta = dao.buscarPorIdUsuario(usuario.getId());
            session.setAttribute("contaLogado", conta);
           
            
            if(usuario.getPerfil().equals(TipoUsuario.GERENTE)) {
            	response.sendRedirect("app/admin/home.jsp");
            }
            else {
            	response.sendRedirect("app?command=dashboardCliente");
            }

        } catch (Exception e) {
            System.out.println("Falha no login: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        }
    }
}