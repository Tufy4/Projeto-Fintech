package edu.ifsp.banco.administrador;

import java.io.IOException;
import java.util.List;

import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.service.AdministradorSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LiberarUsuarioCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AdministradorSERVICE service = new AdministradorSERVICE();
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            service.liberar(id);
            response.sendRedirect(request.getContextPath() + "/app/admin/home.jsp");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        }
    }
}
