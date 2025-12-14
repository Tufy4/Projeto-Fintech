package edu.ifsp.banco.web.emprestimo;

import java.io.IOException;
import java.math.BigDecimal;
import edu.ifsp.banco.modelo.Emprestimo;
import edu.ifsp.banco.modelo.enums.StatusEmprestimo; // Importante!
import edu.ifsp.banco.service.EmprestimoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SolicitarEmprestimoCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher rd;
		try {
			String idContaStr = request.getParameter("idConta");
			String valorStr = request.getParameter("valor");
			String parcelasStr = request.getParameter("parcelas");
			String taxaStr = request.getParameter("taxa");
			if (idContaStr == null || valorStr == null) {
				throw new Exception("Dados inválidos na requisição.");
			}

			int idConta = Integer.parseInt(idContaStr);
			BigDecimal valor = new BigDecimal(valorStr);
			int parcelas = Integer.parseInt(parcelasStr);
			BigDecimal taxa = new BigDecimal(taxaStr);

			Emprestimo emp = new Emprestimo(0, idConta, valor, taxa, parcelas, StatusEmprestimo.SOLICITADO, null);

			EmprestimoSERVICE service = new EmprestimoSERVICE();
			service.solicitarEmprestimo(emp);

			request.setAttribute("msg", "Solicitação realizada! Nº do protocolo: " + emp.getId());

			rd = request.getRequestDispatcher("/app/emprestimo/sucesso.jsp");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("erro", "Erro ao solicitar: " + e.getMessage());
			rd = request.getRequestDispatcher("/app/emprestimo/erro.jsp");
		}

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}