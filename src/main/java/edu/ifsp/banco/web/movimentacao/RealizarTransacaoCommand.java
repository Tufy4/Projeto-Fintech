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

public class RealizarTransacaoCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd;

		try {
			HttpSession session = request.getSession();
			Conta contaOrigem = (Conta) session.getAttribute("contaLogado");
			if (contaOrigem == null) {
				throw new Exception("Sessão expirada. Faça login novamente.");
			}
			String tipo = request.getParameter("tipoTransacao");
			String valorStr = request.getParameter("valor");
			if (valorStr == null || valorStr.isEmpty()) {
				throw new Exception("Valor obrigatório.");
			}
			BigDecimal valor = new BigDecimal(valorStr.replace(",", "."));
			MovimentacaoSERVICE movService = new MovimentacaoSERVICE();

			String tituloSucesso = "";
			String mensagemSucesso = "";

			if ("DEPOSITO".equalsIgnoreCase(tipo)) {
				movService.depositar(contaOrigem.getNumero_conta(), valor);
				tituloSucesso = "Depósito Realizado";
				mensagemSucesso = "Depósito de R$ " + valorStr + " creditado com sucesso!";
			} else if ("TRANSFERENCIA".equalsIgnoreCase(tipo)) {
				String contaDestinoStr = request.getParameter("contaDestino");
				if (contaDestinoStr == null || contaDestinoStr.isEmpty()) {
					throw new Exception("Conta de destino obrigatória.");
				}
				int numeroDestino = Integer.parseInt(contaDestinoStr);
				movService.realizarTransferencia(contaOrigem, numeroDestino, valor);
				tituloSucesso = "Transferência Realizada";
				mensagemSucesso = "Transferência de R$ " + valorStr + " para a conta " + numeroDestino
						+ " concluída com sucesso!";
			} else {
				throw new Exception("Tipo de transação desconhecido.");
			}

			ContaSERVICE contaService = new ContaSERVICE();
			Conta contaAtualizada = contaService.buscarPorNumero(contaOrigem.getNumero_conta());
			if (contaAtualizada != null) {
				session.setAttribute("contaLogado", contaAtualizada);
				session.setAttribute("saldoConta", contaAtualizada.getSaldo());
			}

			request.setAttribute("titulo", tituloSucesso);
			request.setAttribute("msg", mensagemSucesso);
			request.setAttribute("linkDestino", "app?command=dashboardCliente");
			request.setAttribute("textoBotao", "Voltar ao Dashboard");
			rd = request.getRequestDispatcher("/app/sucesso.jsp");

		} catch (NumberFormatException e) {
			request.setAttribute("titulo", "Dados Inválidos");
			request.setAttribute("erro", "Verifique se o valor ou número da conta foram digitados corretamente.");
			request.setAttribute("linkDestino", "app?command=dashboardCliente");
			request.setAttribute("textoBotao", "Tentar Novamente");
			rd = request.getRequestDispatcher("/app/erro.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("titulo", "Falha na Transação");
			request.setAttribute("erro", e.getMessage());
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