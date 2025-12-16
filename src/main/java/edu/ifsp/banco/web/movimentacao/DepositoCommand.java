package edu.ifsp.banco.web.movimentacao;

import java.io.IOException;
import java.math.BigDecimal;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.service.MovimentacaoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DepositoCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd;

		try {
			HttpSession session = request.getSession();
			Conta contaLogado = (Conta) session.getAttribute("contaLogado");
			if (contaLogado == null) {
				throw new Exception("Sessão expirada. Faça login novamente.");
			}
			String valorStr = request.getParameter("valor");
			if (valorStr == null || valorStr.trim().isEmpty()) {
				throw new Exception("O valor do depósito é obrigatório.");
			}
			BigDecimal valor = new BigDecimal(valorStr.replace(",", "."));
			MovimentacaoSERVICE service = new MovimentacaoSERVICE();
			Conta contaAtualizada = service.depositar(contaLogado.getNumero_conta(), valor);

			session.setAttribute("contaLogado", contaAtualizada);
			session.setAttribute("saldoConta", contaAtualizada.getSaldo());

			request.setAttribute("titulo", "Depósito Realizado");
			request.setAttribute("msg", "Seu depósito de R$ " + valorStr + " foi processado com sucesso!");
			request.setAttribute("linkDestino", "app?command=dashboardCliente");
			request.setAttribute("textoBotao", "Voltar ao Início");

			rd = request.getRequestDispatcher("/app/sucesso.jsp");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("titulo", "Falha no Depósito");
			request.setAttribute("erro", "Não foi possível realizar o depósito: " + e.getMessage());
			request.setAttribute("linkDestino", "app?command=dashboardCliente");
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