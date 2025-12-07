package edu.ifsp.banco.web.usuario;

import java.io.IOException;
import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.persistencia.ContaDAO;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DashboardCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

		if (usuario == null) {
			try {
				response.sendRedirect("login.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		try {
			RequestDispatcher rd = request.getRequestDispatcher("/app/home.jsp");

			if (session.getAttribute("contaLogado") != null) {
				rd.forward(request, response);
				return;
			}
			ContaDAO contaDAO = new ContaDAO();
			Conta conta = contaDAO.buscarPorIdUsuario(usuario.getId());
			session.setAttribute("contaLogado", conta);
			rd.forward(request, response);

		} catch (Exception e) {
			request.setAttribute("erro", "Erro ao carregar dados: " + e.getMessage());
			try {
				request.getRequestDispatcher("/app/erro.jsp").forward(request, response);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}