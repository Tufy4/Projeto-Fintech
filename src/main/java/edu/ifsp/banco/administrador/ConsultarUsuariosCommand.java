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

public class ConsultarUsuariosCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AdministradorSERVICE service = new AdministradorSERVICE();
        HttpSession session = request.getSession(false); // garante que existe

        try {
            List<Usuario> bloqueados = service.consulta(); // pega a lista
            request.setAttribute("ListaBloqueados", bloqueados);
            RequestDispatcher rd = request.getRequestDispatcher("app/admin/listarUsuarios.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        }
    }
}
