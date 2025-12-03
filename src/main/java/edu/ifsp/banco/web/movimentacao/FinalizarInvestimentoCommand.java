package edu.ifsp.banco.web.movimentacao;

import java.io.IOException;
import java.math.BigDecimal;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Usuario;
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
            int numeroConta = Integer.parseInt(req.getParameter("numeroConta"));
            String produto = req.getParameter("produto");
            BigDecimal valor = new BigDecimal(req.getParameter("valor"));

            InvestimentoSERVICE service = new InvestimentoSERVICE();

          
            service.montarInvestimento(numeroConta, produto, valor);

 
            HttpSession session = req.getSession();
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
            
            if (usuario != null) {
                ContaDAO contaDAO = new ContaDAO();
                Conta contaAtualizada = contaDAO.buscarPorIdUsuario(usuario.getId());
                
                if (contaAtualizada != null) {
                    session.setAttribute("conta", contaAtualizada);
                    session.setAttribute("saldoConta", contaAtualizada.getSaldo());
                }
            }

            req.setAttribute("mensagem", "Investimento realizado com sucesso!");
            rd = req.getRequestDispatcher("/app/movimentacao/sucesso.jsp");

        } catch (Exception e) {
            e.printStackTrace(); 
            req.setAttribute("erro", e.getMessage());
            rd = req.getRequestDispatcher("/app/movimentacao/erro.jsp");
        }

        try {
            rd.forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}