package edu.ifsp.banco.web.emprestimo;

import java.io.IOException;

import edu.ifsp.banco.service.EmprestimoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AprovarEmprestimoCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd;

		try {
			int idEmprestimo = Integer.parseInt(request.getParameter("idEmprestimo"));

			EmprestimoSERVICE service = new EmprestimoSERVICE();
			service.aprovarEmprestimo(idEmprestimo);

			request.setAttribute("titulo", "Aprovação Realizada");
			request.setAttribute("msg", "Empréstimo ID " + idEmprestimo
					+ " aprovado com sucesso. O valor foi creditado e as parcelas geradas.");
			request.setAttribute("linkDestino", "app?command=dashboardAdmin");
			request.setAttribute("textoBotao", "Voltar ao Dashboard");

			rd = request.getRequestDispatcher("/app/sucesso.jsp");

		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("titulo", "Erro na Aprovação");
			request.setAttribute("erro", "Não foi possível aprovar o empréstimo: " + e.getMessage());
			request.setAttribute("linkDestino", "app?command=dashboardAdmin");
			request.setAttribute("textoBotao", "Voltar");

			rd = request.getRequestDispatcher("/app/erro.jsp");
		}

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}