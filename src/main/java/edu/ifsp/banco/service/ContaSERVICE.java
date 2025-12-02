package edu.ifsp.banco.service;

import java.math.BigDecimal;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.persistencia.ContaDAO;
import edu.ifsp.banco.persistencia.DataAccessException;

public class ContaSERVICE {

    private ContaDAO contaDAO;

    public ContaSERVICE() {
        this.contaDAO = new ContaDAO();
    }



    public void criarConta(Conta conta) throws Exception {
        if (conta == null) {
            throw new Exception("Conta invalida");
        }

        

        try {
            contaDAO.inserir(conta);
        } catch (DataAccessException e) {
            throw new Exception("Erro ao criar conta: " + e.getMessage());
        }
    }



    public Conta buscarPorNumero(int numeroConta) throws Exception {
        if (numeroConta <= 0) {
            throw new Exception("Número de conta inválido");
        }

        try {
            return contaDAO.buscarPorNumero(numeroConta);
        } catch (DataAccessException e) {
            throw new Exception("Erro ao buscar conta: " + e.getMessage());
        }
    }



    public List<Conta> listarContas() throws Exception {

        try {
            return contaDAO.listar();
        } catch (DataAccessException e) {
            throw new Exception("Erro ao listar contas: " + e.getMessage());
        }
    }



    public void atualizarSaldo(int idConta, BigDecimal novoSaldo) throws Exception {

        if (idConta <= 0) {
            throw new Exception("Conta inválida");
        }

        if (novoSaldo == null) {
            throw new Exception("Saldo inválido");
        }

        try {
            contaDAO.atualizarSaldo(idConta, novoSaldo.doubleValue());
        } catch (DataAccessException e) {
            throw new Exception("Erro ao atualizar saldo: " + e.getMessage());
        }
    }
}
