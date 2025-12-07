package edu.ifsp.banco.web.emprestimo;

import java.io.IOException;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Emprestimo;
import edu.ifsp.banco.service.EmprestimoSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MeusEmprestimosCommand implements Command {

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
            EmprestimoSERVICE service = new EmprestimoSERVICE();
            
            List<Emprestimo> meusEmprestimos = service.buscarPorConta(conta.getId());

            request.setAttribute("meusEmprestimos", meusEmprestimos);
            
            RequestDispatcher rd = request.getRequestDispatcher("/app/emprestimo/meus_emprestimos.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            request.setAttribute("erro", "Erro ao carregar seus empréstimos: " + e.getMessage());
            try {
                request.getRequestDispatcher("/app/emprestimo/erro.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}