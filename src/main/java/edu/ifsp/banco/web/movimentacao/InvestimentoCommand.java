package edu.ifsp.banco.web.movimentacao;

import java.io.IOException;
import java.math.BigDecimal;

import edu.ifsp.banco.service.InvestimentoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

public class InvestimentoCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher rd;

        try {
            int numeroConta = Integer.parseInt(request.getParameter("numeroConta"));
            String tipo = request.getParameter("tipo");
            BigDecimal valor = new BigDecimal(request.getParameter("valor"));

            InvestimentoSERVICE service = new InvestimentoSERVICE();
            service.montarInvestimento(numeroConta, tipo, valor);

            request.setAttribute("msg", "Investimento criado com sucesso!");

            rd = request.getRequestDispatcher("/app/movimentacao/sucesso.jsp");

        } catch (Exception e) {
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
