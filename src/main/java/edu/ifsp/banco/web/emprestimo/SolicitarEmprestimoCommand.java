package edu.ifsp.banco.web.emprestimo;

import java.io.IOException;
import java.math.BigDecimal;

import edu.ifsp.banco.modelo.Emprestimo;
import edu.ifsp.banco.modelo.enums.StatusEmprestimo;
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
			BigDecimal valor = new BigDecimal(valorStr.replace(",", "."));
			int parcelas = Integer.parseInt(parcelasStr);
			BigDecimal taxa = new BigDecimal(taxaStr.replace(",", "."));
			Emprestimo emp = new Emprestimo(0, idConta, valor, taxa, parcelas, StatusEmprestimo.SOLICITADO, null);

			EmprestimoSERVICE service = new EmprestimoSERVICE();
			service.solicitarEmprestimo(emp);

			request.setAttribute("titulo", "Solicitação Enviada");
			request.setAttribute("msg", "Sua solicitação foi registrada com sucesso! <br>Protocolo: <strong>"
					+ emp.getId() + "</strong>. <br>Aguarde a análise do gerente.");
			request.setAttribute("linkDestino", "app?command=meusEmprestimos");
			request.setAttribute("textoBotao", "Acompanhar Solicitação");
			rd = request.getRequestDispatcher("/app/sucesso.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("titulo", "Erro na Solicitação");
			request.setAttribute("erro", "Não foi possível registrar o pedido: " + e.getMessage());
			request.setAttribute("linkDestino", "app?command=prepararSimulacao");
			request.setAttribute("textoBotao", "Simular Novamente");
			rd = request.getRequestDispatcher("/app/erro.jsp");
		}

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}