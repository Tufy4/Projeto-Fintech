package edu.ifsp.banco.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.ifsp.banco.login.LoginCommand;
import edu.ifsp.banco.web.helpers.RedirectCommand;
import edu.ifsp.banco.web.movimentacao.DepositoCommand;
import edu.ifsp.banco.web.usuario.CadastrarUsuarioCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/app")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Command> commands = new HashMap<>();

	@Override
	public void init() throws ServletException {
		commands.put("cadastrarUsuario", new CadastrarUsuarioCommand());
		commands.put("depositar", new DepositoCommand());
		commands.put("login", new LoginCommand());
		commands.put("redirect", new RedirectCommand());
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
		command.execute(request, response);
	}
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	service(req, resp);
    }
}
