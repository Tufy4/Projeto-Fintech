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

			req.setAttribute("titulo", "Investimento Realizado");
			req.setAttribute("msg", "Sua aplicação em <strong>" + produto + "</strong> no valor de R$ " + valorStr
					+ " foi concluída com sucesso!");
			req.setAttribute("linkDestino", "app?command=dashboardCliente");
			req.setAttribute("textoBotao", "Voltar ao Dashboard");
			rd = req.getRequestDispatcher("/app/sucesso.jsp");

		} catch (NumberFormatException e) {
			req.setAttribute("titulo", "Valor Inválido");
			req.setAttribute("erro", "O formato do valor informado está incorreto. Use apenas números e vírgula.");
			req.setAttribute("linkDestino", "app?command=investimento");
			req.setAttribute("textoBotao", "Tentar Novamente");
			rd = req.getRequestDispatcher("/app/erro.jsp");

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("titulo", "Falha no Investimento");
			req.setAttribute("erro", "Não foi possível realizar a aplicação: " + e.getMessage());
			req.setAttribute("linkDestino", "app?command=investimento");
			req.setAttribute("textoBotao", "Voltar para Investimentos");
			rd = req.getRequestDispatcher("/app/erro.jsp");
		}

		try {
			rd.forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}