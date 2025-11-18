package edu.ifsp.banco.service;

import java.math.BigDecimal;
import java.sql.Timestamp;

import edu.ifsp.banco.modelo.Emprestimo;
import edu.ifsp.banco.persistencia.ContaDAO;
import edu.ifsp.banco.persistencia.DataAccessException;
import edu.ifsp.banco.persistencia.EmprestimoDAO;

public class EmprestimoSERVICE {

    private EmprestimoDAO emprestimoDAO;
    private ContaDAO contaDAO;

    public EmprestimoSERVICE() {
        this.emprestimoDAO = new EmprestimoDAO();
        this.contaDAO = new ContaDAO();
    }

    public void solicitarEmprestimo(Emprestimo emp) throws Exception {

        if (emp.getValor_emprestimo() == null) {
            throw new Exception("Valor do empréstimo não pode ser nulo.");
        }

        if (emp.getValor_emprestimo().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Valor do empréstimo deve ser maior que zero.");
        }

        if (emp.getJuros() == null) {
            throw new Exception("Valor dos juros não pode ser nulo.");
        }

        if (emp.getJuros().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Juros deve ser maior que zero.");
        }

        if (emp.getParcelas() <= 0) {
            throw new Exception("Número de parcelas deve ser igual ou maior que 1.");
        }

        if (contaDAO.buscarPorNumero(emp.getConta_id()) == null) {
            throw new Exception("Conta informada não existe.");
        }

        if (emp.getStatus() == null) {
            throw new Exception("Status do empréstimo não pode ser nulo.");
        }

        if (!emp.getStatus().equals(Emprestimo.tipoStatus.SIMULADO) &&
            !emp.getStatus().equals(Emprestimo.tipoStatus.APROVADO)) {
            throw new Exception("Status inválido ao solicitar empréstimo.");
        }

        if (emp.getData_solicitacao() == null) {
            emp.setData_solicitacao(new Timestamp(System.currentTimeMillis()));
        }

        try {
            emprestimoDAO.inserir(emp);
        } catch (DataAccessException e) {
            throw new Exception("Erro ao registrar solicitação de empréstimo.");
        }
    }

    
    
    
    public void aprovarEmprestimo(int idEmprestimo) throws Exception {

        Emprestimo emp = emprestimoDAO.buscarPorId(idEmprestimo);

        if (emp == null) {
            throw new Exception("Empréstimo não encontrado.");
        }

        if (!emp.getStatus().equals(Emprestimo.tipoStatus.SIMULADO)) {
            throw new Exception("Somente empréstimos SIMULADOS podem ser aprovados.");
        }

        Timestamp agora = new Timestamp(System.currentTimeMillis());

        emp.setStatus(Emprestimo.tipoStatus.APROVADO);
        emp.setData_aprovacao(agora);

        try {
            emprestimoDAO.atualizarStatus(idEmprestimo, Emprestimo.tipoStatus.APROVADO);
        } catch (DataAccessException e) {
            throw new Exception("Erro ao aprovar empréstimo.");
        }
    }
    
    
    
    

    public void iniciarEmprestimo(int idEmprestimo) throws Exception {

        Emprestimo emp = emprestimoDAO.buscarPorId(idEmprestimo);

        if (emp == null) {
            throw new Exception("Empréstimo não encontrado.");
        }

        if (!emp.getStatus().equals(Emprestimo.tipoStatus.APROVADO)) {
            throw new Exception("Somente empréstimos APROVADOS podem ser iniciados.");
        }

        try {
            emprestimoDAO.atualizarStatus(idEmprestimo, Emprestimo.tipoStatus.EM_ANDAMENTO);
        } catch (DataAccessException e) {
            throw new Exception("Erro ao iniciar empréstimo.");
        }
    }

    
    
    
    
    public void registrarPagamento(int idEmprestimo) throws Exception {

        Emprestimo emp = emprestimoDAO.buscarPorId(idEmprestimo);

        if (emp == null) {
            throw new Exception("Empréstimo não encontrado.");
        }

        if (!emp.getStatus().equals(Emprestimo.tipoStatus.EM_ANDAMENTO)) {
            throw new Exception("Pagamento só pode ser registrado em empréstimos EM_ANDAMENTO.");
        }

        Timestamp agora = new Timestamp(System.currentTimeMillis());

        try {
            emprestimoDAO.registrarPagamento(idEmprestimo, agora);
        } catch (DataAccessException e) {
            throw new Exception("Erro ao registrar pagamento.");
        }
    }

    
    
    
    public void quitarEmprestimo(int idEmprestimo) throws Exception {

        Emprestimo emp = emprestimoDAO.buscarPorId(idEmprestimo);

        if (emp == null) {
            throw new Exception("Empréstimo não encontrado.");
        }

        if (!emp.getStatus().equals(Emprestimo.tipoStatus.EM_ANDAMENTO)) {
            throw new Exception("Somente empréstimos EM_ANDAMENTO podem ser quitados.");
        }

        try {
            emprestimoDAO.atualizarStatus(idEmprestimo, Emprestimo.tipoStatus.QUITADO);
        } catch (DataAccessException e) {
            throw new Exception("Erro ao quitar empréstimo.");
        }
    }
}
