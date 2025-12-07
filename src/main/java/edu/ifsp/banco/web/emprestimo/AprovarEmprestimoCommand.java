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

			request.setAttribute("msg", "Empréstimo ID " + idEmprestimo + " aprovado e parcelas geradas.");
			rd = request.getRequestDispatcher("/app/emprestimo/sucesso.jsp");

		} catch (Exception e) {
			request.setAttribute("erro", "Erro na aprovação: " + e.getMessage());
			rd = request.getRequestDispatcher("/app/emprestimo/erro.jsp");
		}

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}