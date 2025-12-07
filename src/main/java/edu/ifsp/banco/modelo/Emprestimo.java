package edu.ifsp.banco.modelo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import edu.ifsp.banco.modelo.enums.StatusEmprestimo;

public class Emprestimo {
	private int id;
	private int conta_id;
	private BigDecimal valor_emprestimo;
	private BigDecimal taxa_juros_mensal;
	private int parcelas;
	private StatusEmprestimo status;
	private Timestamp data_solicitacao;
	private Timestamp data_aprovacao;
	private Timestamp data_ultimo_pagamento;

	private List<ParcelaEmprestimo> listaParcelas = new ArrayList<>();

	public Emprestimo(int id, int conta_id, BigDecimal valor_emprestimo, BigDecimal taxa_juros_mensal, int parcelas,
			StatusEmprestimo status, Timestamp data_solicitacao) {
		this.id = id;
		this.conta_id = conta_id;
		this.valor_emprestimo = valor_emprestimo;
		this.taxa_juros_mensal = taxa_juros_mensal;
		this.parcelas = parcelas;
		this.status = status;
		this.data_solicitacao = data_solicitacao;
	}

	public BigDecimal getTaxa_juros_mensal() {
		return taxa_juros_mensal;
	}

	public void setTaxa_juros_mensal(BigDecimal taxa_juros_mensal) {
		this.taxa_juros_mensal = taxa_juros_mensal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getConta_id() {
		return conta_id;
	}

	public void setConta_id(int conta_id) {
		this.conta_id = conta_id;
	}

	public BigDecimal getValor_emprestimo() {
		return valor_emprestimo;
	}

	public void setValor_emprestimo(BigDecimal valor_emprestimo) {
		this.valor_emprestimo = valor_emprestimo;
	}

	public int getParcelas() {
		return parcelas;
	}

	public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}

	public StatusEmprestimo getStatus() {
		return status;
	}

	public void setStatus(StatusEmprestimo status) {
		this.status = status;
	}

	public Timestamp getData_solicitacao() {
		return data_solicitacao;
	}

	public void setData_solicitacao(Timestamp data_solicitacao) {
		this.data_solicitacao = data_solicitacao;
	}

	public Timestamp getData_aprovacao() {
		return data_aprovacao;
	}

	public void setData_aprovacao(Timestamp data_aprovacao) {
		this.data_aprovacao = data_aprovacao;
	}

	public Timestamp getData_ultimo_pagamento() {
		return data_ultimo_pagamento;
	}

	public void setData_ultimo_pagamento(Timestamp data_ultimo_pagamento) {
		this.data_ultimo_pagamento = data_ultimo_pagamento;
	}

	public List<ParcelaEmprestimo> getListaParcelas() {
		return listaParcelas;
	}

	public void setListaParcelas(List<ParcelaEmprestimo> listaParcelas) {
		this.listaParcelas = listaParcelas;
	}

}