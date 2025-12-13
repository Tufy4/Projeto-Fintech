package edu.ifsp.banco.web.movimentacao;

import java.io.IOException;
import java.math.BigDecimal;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.persistencia.ContaDAO;
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
			// 1. Validação de Sessão
			HttpSession session = request.getSession();
			Conta contaOrigem = (Conta) session.getAttribute("contaLogado");

			if (contaOrigem == null) {
				throw new Exception("Sessão expirada. Faça login novamente.");
			}

			// 2. Parâmetros
			// O input hidden no JSP deve ter name="tipoTransacao"
			String tipo = request.getParameter("tipoTransacao");
			String valorStr = request.getParameter("valor");

			if (valorStr == null || valorStr.isEmpty()) {
				throw new Exception("Valor obrigatório.");
			}

			// Troca vírgula por ponto para evitar erro de conversão
			BigDecimal valor = new BigDecimal(valorStr.replace(",", "."));

			MovimentacaoSERVICE service = new MovimentacaoSERVICE();
			String mensagemSucesso = "";

			// 3. Decisão do Fluxo
			if ("DEPOSITO".equalsIgnoreCase(tipo)) {

				service.depositar(contaOrigem.getNumero_conta(), valor);
				mensagemSucesso = "Depósito realizado com sucesso!";

			} else if ("TRANSFERENCIA".equalsIgnoreCase(tipo)) {

				String contaDestinoStr = request.getParameter("contaDestino");
				if (contaDestinoStr == null || contaDestinoStr.isEmpty()) {
					throw new Exception("Conta de destino obrigatória.");
				}
				int numeroDestino = Integer.parseInt(contaDestinoStr);

				service.realizarTransferencia(contaOrigem, numeroDestino, valor);
				mensagemSucesso = "Transferência de R$ " + valorStr + " realizada com sucesso!";

			} else {
				throw new Exception("Tipo de transação desconhecido.");
			}

			// 4. Atualização de Sessão (Saldo Visual)
			ContaDAO contaDAO = new ContaDAO();
			Conta contaAtualizada = contaDAO.buscarPorNumero(contaOrigem.getNumero_conta());

			if (contaAtualizada != null) {
				session.setAttribute("contaLogado", contaAtualizada);
				session.setAttribute("saldoConta", contaAtualizada.getSaldo());
			}

			request.setAttribute("msg", mensagemSucesso);
			rd = request.getRequestDispatcher("/app/movimentacao/sucesso.jsp");

		} catch (NumberFormatException e) {
			request.setAttribute("erro", "Valor ou número da conta inválido.");
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