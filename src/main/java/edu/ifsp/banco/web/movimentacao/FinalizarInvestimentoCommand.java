import java.io.IOException;
import java.math.BigDecimal;

import edu.ifsp.banco.service.InvestimentoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FinalizarInvestimentoCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        try {
            int numeroConta = Integer.parseInt(req.getParameter("numeroConta"));
            String produto = req.getParameter("produto");
            BigDecimal valor = new BigDecimal(req.getParameter("valor"));

            InvestimentoSERVICE service = new InvestimentoSERVICE();

            // desconta do saldo
            service.montarInvestimento(numeroConta, produto, valor);

            req.setAttribute("mensagem", "Investimento realizado com sucesso!");
            return "/movimentacao/sucesso.jsp";

        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            return "/movimentacao/erro.jsp";
        }
    }
}
