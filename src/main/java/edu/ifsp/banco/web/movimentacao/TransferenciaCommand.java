package edu.ifsp.banco.web.movimentacao;

import java.io.IOException;
import java.math.BigDecimal;
import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.service.ContaSERVICE;
import edu.ifsp.banco.service.MovimentacaoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class TransferenciaCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd;

		try {
			int numeroDestino = Integer.parseInt(request.getParameter("contaDestino"));
			BigDecimal valor = new BigDecimal(request.getParameter("valor"));

			HttpSession session = request.getSession();
			Conta contaOrigem = (Conta) session.getAttribute("contaLogado");

			if (contaOrigem == null) {
				throw new Exception("Sessão expirada. Faça login novamente.");
			}

			MovimentacaoSERVICE movService = new MovimentacaoSERVICE();
			movService.realizarTransferencia(contaOrigem, numeroDestino, valor);

			ContaSERVICE contaService = new ContaSERVICE();
			Conta contaAtualizada = contaService.buscarPorNumero(contaOrigem.getNumero_conta());

			session.setAttribute("contaLogado", contaAtualizada);
			session.setAttribute("saldoConta", contaAtualizada.getSaldo());

			request.setAttribute("msg", "Transferência de R$ " + valor + " realizada com sucesso!");
			rd = request.getRequestDispatcher("/app/movimentacao/sucesso.jsp");

		} catch (NumberFormatException e) {
			request.setAttribute("erro", "Formato de valor ou conta inválido.");
			rd = request.getRequestDispatcher("/app/movimentacao/erro.jsp");
		} catch (Exception e) {
			e.printStackTrace();
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