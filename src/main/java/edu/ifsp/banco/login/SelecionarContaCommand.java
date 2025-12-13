package edu.ifsp.banco.login;

import java.io.IOException;
import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.modelo.enums.TiposConta;
import edu.ifsp.banco.service.ContaSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SelecionarContaCommand implements Command {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("usuarioLogado") == null) {
			response.sendRedirect("index.jsp");
			return;
		}

		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		String idContaStr = request.getParameter("idConta");
		ContaSERVICE contaService = new ContaSERVICE();

		try {
			int idConta = Integer.parseInt(idContaStr);
			Conta contaSelecionada = contaService.buscarPorId(idConta);

			if (contaSelecionada.getUsuarioId() != usuario.getId()) {
				session.invalidate();
				throw new Exception("Tentativa de acesso a conta de terceiros. Porque tu fez isso hein???");
			}

			session.setAttribute("contaLogado", contaSelecionada);

			System.out.println(
					"Conta selecionada: " + contaSelecionada.getNumero_conta() + " - " + contaSelecionada.getTipo());

			if (contaSelecionada.getTipo() == TiposConta.GERENTE) {
				response.sendRedirect("app?command=dashboardAdmin");
			} else {
				response.sendRedirect("app?command=dashboardCliente");
			}

		} catch (Exception e) {
			request.setAttribute("erro", "Erro ao selecionar conta: " + e.getMessage());
			request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
		}
	}
}