package edu.ifsp.banco.web.movimentacao;

import edu.ifsp.banco.web.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EscolherInvestimentoCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String produto = request.getParameter("produto");

        
        request.setAttribute("produto", produto);

        return "/movimentacao/investir-detalhe.jsp";
    }
}
