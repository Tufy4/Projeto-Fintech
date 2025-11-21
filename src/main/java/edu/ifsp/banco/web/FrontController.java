package edu.ifsp.banco.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.ifsp.banco.web.movimentacao.DepositoCommand;
import edu.ifsp.banco.web.usuario.CadastrarUsuarioCommand;
import edu.ifsp.banco.web.usuario.UsuarioCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("control")
public class FrontController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Map<String, Command> commands = new HashMap<>();

    @Override
    public void init() throws ServletException {
        commands.put("CadastrarUsuario", new CadastrarUsuarioCommand());
        commands.put("Depositar", new DepositoCommand());
        // coloquem commands aqui
       
    }

    
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("command");

        if (acao == null || !commands.containsKey(acao)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Comando inválido");
            return;
        }

        Command command = commands.get(acao);
        String pagina = command.execute(request, response);

        request.getRequestDispatcher(pagina).forward(request, response);
    }
}
