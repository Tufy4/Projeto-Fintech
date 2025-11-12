package edu.ifsp.banco.modelo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Emprestimo {
	private int id;
	private int conta_id;
	private BigDecimal valor_emprestimo;
	private BigDecimal juros;
	private int parcelas;
	private tipoStatus status;
	private Timestamp data_solicitacao;
	private Timestamp data_aprovacao;
	private Timestamp data_ultimo_pagamento;
	
	
	
    public enum tipoStatus {
	    SIMULADO("SIMULADO"),
	    APROVADO("APROVADO"),
	    EM_ANDAMENTO("EM_ANDAMENTO"),
	    QUITADO("QUITADO");

	    private String valor;

	    tipoStatus(String valor) {
	        this.valor = valor;
	    }
	    public String getValor() {
	        return valor;
	    }
	}
    
    



	public Emprestimo(int id, int conta_id, BigDecimal valor_emprestimo, BigDecimal juros, int parcelas,
			tipoStatus status, Timestamp data_solicitacao, Timestamp data_aprovacao, Timestamp data_ultimo_pagamento) {
		super();
		this.id = id;
		this.conta_id = conta_id;
		this.valor_emprestimo = valor_emprestimo;
		this.juros = juros;
		this.parcelas = parcelas;
		this.status = status;
		this.data_solicitacao = data_solicitacao;
		this.data_aprovacao = data_aprovacao;
		this.data_ultimo_pagamento = data_ultimo_pagamento;
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



	public BigDecimal getJuros() {
		return juros;
	}



	public void setJuros(BigDecimal juros) {
		this.juros = juros;
	}



	public int getParcelas() {
		return parcelas;
	}



	public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}



	public tipoStatus getStatus() {
		return status;
	}



	public void setStatus(tipoStatus status) {
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
    
    

	
	
	
	

}
