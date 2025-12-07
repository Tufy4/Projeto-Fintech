package edu.ifsp.banco.web.movimentacao;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Investimento;
import edu.ifsp.banco.modelo.enums.StatusInvestimento;
import edu.ifsp.banco.service.InvestimentoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MeusInvestimentosCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd;

		try {
			HttpSession session = request.getSession();
			Conta conta = (Conta) session.getAttribute("conta");

			if (conta == null) {

				response.sendRedirect("index.jsp");
				return;
			}

			InvestimentoSERVICE service = new InvestimentoSERVICE();
			List<Investimento> investimentos = service.listarPorConta(conta.getId());

			BigDecimal totalInvestido = BigDecimal.ZERO;
			for (Investimento inv : investimentos) {
				if (inv.getStatus() == StatusInvestimento.ATIVO) {
					totalInvestido = totalInvestido.add(inv.getValorInvestido());
				}
			}

			request.setAttribute("investimentos", investimentos);
			request.setAttribute("totalInvestido", totalInvestido);

			rd = request.getRequestDispatcher("/app/movimentacao/meus-investimentos.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("erro", "Erro ao carregar investimentos: " + e.getMessage());
			try {
				request.getRequestDispatcher("/app/movimentacao/erro.jsp").forward(request, response);
			} catch (ServletException | IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}