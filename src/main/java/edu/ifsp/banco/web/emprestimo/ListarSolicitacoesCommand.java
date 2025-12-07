package edu.ifsp.banco.web.emprestimo;

import java.io.IOException;
import java.util.List;

import edu.ifsp.banco.modelo.Emprestimo;
import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.modelo.enums.StatusEmprestimo;
import edu.ifsp.banco.modelo.enums.TipoUsuario;
import edu.ifsp.banco.service.EmprestimoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ListarSolicitacoesCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Usuario user = (Usuario) session.getAttribute("usuarioLogado");

		if (user == null || TipoUsuario.GERENTE != user.getPerfil()) {
			try {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		try {

			EmprestimoSERVICE service = new EmprestimoSERVICE();
			List<Emprestimo> pendentes = service.listarPorStatus(StatusEmprestimo.SOLICITADO);
			request.setAttribute("listaPendentes", pendentes);

			RequestDispatcher rd = request.getRequestDispatcher("/app/admin/aprovacao.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			request.setAttribute("erro", "Erro ao listar solicitações: " + e.getMessage());
			try {
				request.getRequestDispatcher("/app/emprestimo/erro.jsp").forward(request, response);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}