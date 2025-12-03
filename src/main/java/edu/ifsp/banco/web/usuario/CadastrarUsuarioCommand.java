package edu.ifsp.banco.web.usuario;

import java.io.IOException;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.modelo.enums.TiposConta;
import edu.ifsp.banco.persistencia.UsuarioDAO;
import edu.ifsp.banco.service.ContaSERVICE;
import edu.ifsp.banco.service.UsuarioSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CadastrarUsuarioCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher rd;

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String telefone = request.getParameter("telefone");
        String endereco = request.getParameter("endereco");
        String tipoConta = request.getParameter("typeAccount");

        Usuario usuario = new Usuario(nome, email, senha, telefone, endereco);
        UsuarioSERVICE service = new UsuarioSERVICE();
        UsuarioDAO dao = new UsuarioDAO();
        ContaSERVICE ContaServ = new ContaSERVICE();

        try {
            service.criarConta(usuario);
            
            Usuario userBancoDados = dao.buscarPorEmail(usuario.getEmail());
            Conta conta = new Conta();
            conta.setUsuarioId(userBancoDados.getId());
            conta.setTipo(TiposConta.valueOf(tipoConta));
            ContaServ.criarConta(conta);
            
            rd = request.getRequestDispatcher("index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
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