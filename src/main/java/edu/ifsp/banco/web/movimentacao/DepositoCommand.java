package edu.ifsp.banco.web.movimentacao;

import java.math.BigDecimal;

import edu.ifsp.banco.service.MovimentacaoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DepositoCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            int idConta = Integer.parseInt(request.getParameter("idConta"));
            BigDecimal valor = new BigDecimal(request.getParameter("valor"));

            MovimentacaoSERVICE service = new MovimentacaoSERVICE();
            service.depositar(idConta, valor);

            request.setAttribute("msg", "Depósito realizado com sucesso!");

            return "/paginas/movimentacao/sucesso.jsp";

        } catch (Exception e) {
            request.setAttribute("erro", e.getMessage());
            return "/paginas/movimentacao/erro.jsp";
        }
    }
}
