package edu.ifsp.banco.web.movimentacao;

import java.io.IOException;
import java.math.BigDecimal;

import edu.ifsp.banco.service.MovimentacaoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

public class DepositoCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd;
		try {
			int idConta = Integer.parseInt(request.getParameter("idConta"));
			BigDecimal valor = new BigDecimal(request.getParameter("valor"));

			MovimentacaoSERVICE service = new MovimentacaoSERVICE();
			service.depositar(idConta, valor);

			request.setAttribute("msg", "Depósito realizado com sucesso!");

			rd = request.getRequestDispatcher("/app/movimentacao/sucesso.jsp");
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
