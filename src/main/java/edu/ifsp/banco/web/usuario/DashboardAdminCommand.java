package edu.ifsp.banco.web.usuario;

import java.io.IOException;

import edu.ifsp.banco.service.AdministradorSERVICE;
import edu.ifsp.banco.service.ContaSERVICE;
import edu.ifsp.banco.service.EmprestimoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DashboardAdminCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			AdministradorSERVICE adminService = new AdministradorSERVICE();
			EmprestimoSERVICE emprestimoService = new EmprestimoSERVICE();
			ContaSERVICE contaService = new ContaSERVICE();

			int qtdBloqueados = adminService.obterQuantidadeUsuariosBloqueados();
			int qtdEmprestimos = emprestimoService.obterQuantidadeSolicitacoes();
			int qtdContas = contaService.obterTotalContas();

			request.setAttribute("countBloqueados", qtdBloqueados);
			request.setAttribute("countEmprestimos", qtdEmprestimos);
			request.setAttribute("countContas", qtdContas);

			request.getRequestDispatcher("/app/admin/home.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("erro", "Erro ao carregar dashboard: " + e.getMessage());
			request.getRequestDispatcher("/app/admin/home.jsp").forward(request, response);
		}
	}
}