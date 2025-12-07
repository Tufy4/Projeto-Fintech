package edu.ifsp.banco.web.emprestimo;

import java.io.IOException;
import java.math.BigDecimal;

import edu.ifsp.banco.service.EmprestimoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PrepararSimulacaoCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		EmprestimoSERVICE service = new EmprestimoSERVICE();

		BigDecimal taxaAtual = service.buscarTaxaPadrao();

		request.getSession().setAttribute("taxaPadrao", taxaAtual);

		RequestDispatcher rd = request.getRequestDispatcher("/app/emprestimo/simular.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}