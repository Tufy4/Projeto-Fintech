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
			HttpSession session = request.getSession();
			Conta contaOrigem = (Conta) session.getAttribute("contaLogado");
			if (contaOrigem == null) {
				throw new Exception("Sessão expirada. Faça login novamente.");
			}
			String contaDestinoStr = request.getParameter("contaDestino");
			String valorStr = request.getParameter("valor");
			
			if (contaDestinoStr == null || valorStr == null || valorStr.isEmpty()) {
				throw new Exception("Conta de destino e valor são obrigatórios.");
			}
			int numeroDestino = Integer.parseInt(contaDestinoStr);
			BigDecimal valor = new BigDecimal(valorStr.replace(",", "."));
			MovimentacaoSERVICE movService = new MovimentacaoSERVICE();
			movService.realizarTransferencia(contaOrigem, numeroDestino, valor);
			ContaSERVICE contaService = new ContaSERVICE();
			Conta contaAtualizada = contaService.buscarPorNumero(contaOrigem.getNumero_conta());

			if (contaAtualizada != null) {
				session.setAttribute("contaLogado", contaAtualizada);
				session.setAttribute("saldoConta", contaAtualizada.getSaldo());
			}
			request.setAttribute("titulo", "Transferência Concluída");
			request.setAttribute("msg", "Transferência de <strong>R$ " + valorStr + "</strong> para a conta <strong>"
					+ numeroDestino + "</strong> realizada com sucesso!");
			request.setAttribute("linkDestino", "app?command=dashboardCliente");
			request.setAttribute("textoBotao", "Voltar ao Dashboard");
			rd = request.getRequestDispatcher("/app/sucesso.jsp");
		} catch (NumberFormatException e) {
			request.setAttribute("titulo", "Dados Inválidos");
			request.setAttribute("erro", "Verifique se o número da conta e o valor foram digitados corretamente.");
			request.setAttribute("linkDestino", "app?command=dashboardCliente");
			request.setAttribute("textoBotao", "Tentar Novamente");
			rd = request.getRequestDispatcher("/app/erro.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("titulo", "Falha na Transferência");
			request.setAttribute("erro", "Não foi possível completar a transferência: " + e.getMessage());
			request.setAttribute("linkDestino", "app?command=dashboardCliente");
			request.setAttribute("textoBotao", "Voltar ao Dashboard");
			rd = request.getRequestDispatcher("/app/erro.jsp");
		}
		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}