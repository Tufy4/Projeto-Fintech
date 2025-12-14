package edu.ifsp.banco.web.movimentacao;

import java.io.IOException;
import java.math.BigDecimal;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.service.ContaSERVICE;
import edu.ifsp.banco.service.InvestimentoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class FinalizarInvestimentoCommand implements Command {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {

		RequestDispatcher rd;

		try {
			HttpSession session = req.getSession();
			Conta contaSessao = (Conta) session.getAttribute("contaLogado");

			if (contaSessao == null) {
				throw new Exception("Sessão expirada. Faça login novamente.");
			}

			int numeroConta = contaSessao.getNumero_conta();
			String produto = req.getParameter("produto");

			if (produto == null || produto.trim().isEmpty()) {
				throw new Exception("O tipo de investimento (produto) não foi informado.");
			}

			String valorStr = req.getParameter("valor");
			if (valorStr == null || valorStr.isEmpty()) {
				throw new Exception("O valor do investimento é obrigatório.");
			}

			BigDecimal valor = new BigDecimal(valorStr.replace(",", "."));
			InvestimentoSERVICE investService = new InvestimentoSERVICE();
			investService.montarInvestimento(numeroConta, produto, valor);
			ContaSERVICE contaService = new ContaSERVICE();
			Conta contaAtualizada = contaService.buscarPorNumero(numeroConta);

			if (contaAtualizada != null) {
				session.setAttribute("contaLogado", contaAtualizada);
				session.setAttribute("saldoConta", contaAtualizada.getSaldo());
			}

			req.setAttribute("msg", "Investimento em " + produto + " realizado com sucesso!");
			rd = req.getRequestDispatcher("/app/movimentacao/sucesso.jsp");

		} catch (NumberFormatException e) {
			req.setAttribute("erro", "Formato de valor inválido.");
			rd = req.getRequestDispatcher("/app/movimentacao/erro.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("erro", "Erro ao investir: " + e.getMessage());
			rd = req.getRequestDispatcher("/app/movimentacao/erro.jsp");
		}

		try {
			rd.forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}