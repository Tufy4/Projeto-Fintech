package edu.ifsp.banco.web.emprestimo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import edu.ifsp.banco.modelo.ParcelaEmprestimo;
import edu.ifsp.banco.service.EmprestimoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SimularEmprestimoCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd;
		try {
			BigDecimal valor = new BigDecimal(request.getParameter("valor"));
			int parcelas = Integer.parseInt(request.getParameter("parcelas"));

			EmprestimoSERVICE service = new EmprestimoSERVICE();

			BigDecimal taxa = service.buscarTaxaPadrao();

			List<ParcelaEmprestimo> simulacao = service.simularCondicoes(valor, parcelas, taxa);

			request.setAttribute("listaSimulacao", simulacao);
			request.setAttribute("valorSolicitado", valor);
			request.setAttribute("qtdParcelas", parcelas);

			rd = request.getRequestDispatcher("/app/emprestimo/resultado_simulacao.jsp");
		} catch (Exception e) {
			request.setAttribute("erro", "Erro na simulação: " + e.getMessage());
			rd = request.getRequestDispatcher("/app/emprestimo/erro.jsp");
		}

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}