package edu.ifsp.banco.web.movimentacao;

import java.io.IOException;
import java.math.BigDecimal;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.persistencia.ContaDAO;
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
			// 1. Pega conta da sessão (Segurança)
			HttpSession session = req.getSession();
			Conta contaSessao = (Conta) session.getAttribute("contaLogado");

			if (contaSessao == null) {
				throw new Exception("Sessão expirada. Faça login novamente.");
			}

			int numeroConta = contaSessao.getNumero_conta();

			// 2. Valida Produto (Evita ORA-01400)
			String produto = req.getParameter("produto");

			if (produto == null || produto.trim().isEmpty()) {
				throw new Exception("O tipo de investimento (produto) não foi informado.");
			}

			// 3. Valida Valor
			String valorStr = req.getParameter("valor");
			if (valorStr == null || valorStr.isEmpty()) {
				throw new Exception("O valor do investimento é obrigatório.");
			}

			BigDecimal valor = new BigDecimal(valorStr.replace(",", "."));

			// 4. Executa Serviço
			InvestimentoSERVICE service = new InvestimentoSERVICE();
			service.montarInvestimento(numeroConta, produto, valor);

			// 5. Atualiza Sessão (Saldo Visual)
			ContaDAO contaDAO = new ContaDAO();
			Conta contaAtualizada = contaDAO.buscarPorNumero(numeroConta);

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