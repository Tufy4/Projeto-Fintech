package edu.ifsp.banco.web.emprestimo;

import java.io.IOException;

import edu.ifsp.banco.service.EmprestimoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PagarParcelaCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd;

		try {
			int idParcela = Integer.parseInt(request.getParameter("idParcela"));

			EmprestimoSERVICE service = new EmprestimoSERVICE();
			service.pagarParcela(idParcela);

			request.getSession().setAttribute("contaLogado", null);
			request.setAttribute("titulo", "Pagamento Confirmado");
			request.setAttribute("msg", "A parcela foi paga com sucesso e o saldo atualizado.");

			request.setAttribute("linkDestino", "app?command=dashboardCliente");
			request.setAttribute("textoBotao", "Voltar para Home");
			rd = request.getRequestDispatcher("/app/sucesso.jsp");

		} catch (Exception e) {
			request.setAttribute("titulo", "Falha no Pagamento");
			request.setAttribute("erro", "Não foi possível pagar a parcela: " + e.getMessage());
			request.setAttribute("linkDestino", "app?command=meusEmprestimos");
			request.setAttribute("textoBotao", "Tentar Novamente");

			rd = request.getRequestDispatcher("/app/erro.jsp");
		}

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}