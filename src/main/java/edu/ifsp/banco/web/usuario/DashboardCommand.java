package edu.ifsp.banco.web.usuario;

import java.io.IOException;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.service.ContaSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DashboardCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

		if (usuario == null) {
			response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
			return;
		}

		try {
			Conta contaLogada = (Conta) session.getAttribute("contaLogado");

			if (contaLogada != null) {
				ContaSERVICE service = new ContaSERVICE();
				contaLogada = service.buscarPorId(contaLogada.getId()); // não é ideal, mas garante saldo atualizado.
				session.setAttribute("contaLogado", contaLogada);

				request.getRequestDispatcher("/app/home.jsp").forward(request, response);
				return;
			}

			ContaSERVICE contaService = new ContaSERVICE();
			List<Conta> contas = contaService.buscarContasPorUsuario(usuario.getId());

			request.setAttribute("listaContas", contas);
			request.getRequestDispatcher("/auth/selecao_conta.jsp").forward(request, response);

		} catch (Exception e) {
			request.setAttribute("erro", "Erro ao carregar dashboard: " + e.getMessage());
			request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
		}
	}
}