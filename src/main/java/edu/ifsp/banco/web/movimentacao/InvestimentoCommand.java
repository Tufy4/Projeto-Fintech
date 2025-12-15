package edu.ifsp.banco.web.movimentacao;

import java.io.IOException;
import java.math.BigDecimal;

import edu.ifsp.banco.service.InvestimentoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class InvestimentoCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd;

		try {
			String numeroContaStr = request.getParameter("numeroConta");
			String valorStr = request.getParameter("valor");
			String tipo = request.getParameter("tipo");
			if (numeroContaStr == null || valorStr == null || tipo == null) {
				throw new Exception("Dados incompletos para realizar o investimento.");
			}
			int numeroConta = Integer.parseInt(numeroContaStr);
			BigDecimal valor = new BigDecimal(valorStr.replace(",", "."));
			InvestimentoSERVICE service = new InvestimentoSERVICE();
			service.montarInvestimento(numeroConta, tipo, valor);

			request.setAttribute("titulo", "Investimento Criado");
			request.setAttribute("msg", "A aplicação do tipo <strong>" + tipo + "</strong> no valor de R$ " + valorStr
					+ " foi realizada com sucesso!");
			request.setAttribute("linkDestino", "app?command=meusInvestimentos");
			request.setAttribute("textoBotao", "Ver Meus Investimentos");
			rd = request.getRequestDispatcher("/app/sucesso.jsp");

		} catch (Exception e) {
			request.setAttribute("titulo", "Erro no Investimento");
			request.setAttribute("erro", "Não foi possível criar o investimento: " + e.getMessage());
			request.setAttribute("linkDestino", "app?command=dashboardCliente");
			request.setAttribute("textoBotao", "Voltar ao Início");
			rd = request.getRequestDispatcher("/app/erro.jsp");
		}
		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}