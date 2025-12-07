package edu.ifsp.banco.web.movimentacao;

import java.io.IOException;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Movimentacoes;
import edu.ifsp.banco.service.MovimentacaoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ExtratoCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Conta conta = (Conta) session.getAttribute("contaLogado");

		if (conta == null) {
			try {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		try {
			MovimentacaoSERVICE service = new MovimentacaoSERVICE();
			List<Movimentacoes> extrato = service.consultarExtrato(conta.getId());

			request.setAttribute("listaExtrato", extrato);

			RequestDispatcher rd = request.getRequestDispatcher("/app/movimentacao/extrato.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			request.setAttribute("erro", "Erro ao carregar extrato: " + e.getMessage());
			try {
				request.getRequestDispatcher("/app/home.jsp").forward(request, response);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}