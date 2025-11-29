package edu.ifsp.banco.modelo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import edu.ifsp.banco.modelo.enums.*;

public class Conta {
	private int id;
	private int usuarioId;
	private int agencia;
	private int numero_conta;
	private BigDecimal saldo;
	private TiposConta tipo;
	private StatusConta status;
	private Timestamp DataCriacao;
	    
	public Conta(int id, int usuarioId, int agencia, int numero_conta, BigDecimal saldo, TiposConta tipo,
			StatusConta status, Timestamp dataCriacao) {
		super();
		this.id = id;
		this.usuarioId = usuarioId;
		this.agencia = agencia;
		this.numero_conta = numero_conta;
		this.saldo = saldo;
		this.tipo = tipo;
		this.status = status;
		DataCriacao = dataCriacao;
	}
	

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getUsuarioId() {
			return usuarioId;
		}
		public void setUsuarioId(int usuarioId) {
			this.usuarioId = usuarioId;
		}
		public BigDecimal getSaldo() {
			return saldo;
		}
		public void setSaldo(BigDecimal saldo) {
			this.saldo = saldo;
		}
		public TiposConta getTipo() {
			return tipo;
		}
		public void setTipo(TiposConta tipo) {
			this.tipo = tipo;
		}
		public StatusConta getStatus() {
			return status;
		}
		public void setStatus(StatusConta status) {
			this.status = status;
		}
		public Timestamp getDataCriacao() {
			return DataCriacao;
		}
		public void setDataCriacao(Timestamp dataCriacao) {
			DataCriacao = dataCriacao;
		}
		
		public int getAgencia() {
			return agencia;
		}
		public void setAgencia(int agencia) {
			this.agencia = agencia;
		}
		public int getNumero_conta() {
			return numero_conta;
		}
		public void setNumero_conta(int numero_conta) {
			this.numero_conta = numero_conta;
		}
		
		
		
		
		
	    
	    
}
