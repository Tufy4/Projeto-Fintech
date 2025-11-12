package edu.ifsp.banco.modelo;

import java.sql.Timestamp;

public class Extrato {
	private int id;
	private int conta_id;
	private Timestamp data_inicio;
	private Timestamp data_fim;
	private Timestamp data_geracao;
	
	
	public Extrato(int id, int conta_id, Timestamp data_inicio, Timestamp data_fim, Timestamp data_geracao) {
		super();
		this.id = id;
		this.conta_id = conta_id;
		this.data_inicio = data_inicio;
		this.data_fim = data_fim;
		this.data_geracao = data_geracao;
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


	public Timestamp getData_inicio() {
		return data_inicio;
	}


	public void setData_inicio(Timestamp data_inicio) {
		this.data_inicio = data_inicio;
	}


	public Timestamp getData_fim() {
		return data_fim;
	}


	public void setData_fim(Timestamp data_fim) {
		this.data_fim = data_fim;
	}


	public Timestamp getData_geracao() {
		return data_geracao;
	}


	public void setData_geracao(Timestamp data_geracao) {
		this.data_geracao = data_geracao;
	}
	
	
	
	
	
	
	

}
