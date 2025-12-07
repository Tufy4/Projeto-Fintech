package edu.ifsp.banco.web.movimentacao;

import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EscolherInvestimentoCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd = null;

		try {
			String produto = request.getParameter("produto");
			request.setAttribute("produto", produto);

			rd = request.getRequestDispatcher("/app/movimentacao/investir-detalhe.jsp");
		} catch (Exception e) {
			request.setAttribute("erro", e.getMessage());
			rd = request.getRequestDispatcher("/app/movimentacao/erro.jsp");
		}

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
