package edu.ifsp.banco.modelo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Conta {
	private int id;
	private int usuarioId;
	private BigDecimal saldo;
	private tiposConta tipo;
	private statusConta status;
	private Timestamp DataCriacao;
	    
	    
	    public enum tiposConta{
	    	CORRENTE("CORRENTE"),
	    	POUPANCA("POUPANCA");
	    	
	    	 private String valor;

	    	 tiposConta(String valor) {
	 	        this.valor = valor;
	 	    }
	 	    public String getValor() {
	 	        return valor;
	 	    }
	    }
	    
	    
	    public enum statusConta{
	    	ATIVO("ATIVO"),
	    	BLOQUEADO("BLOQUEADO");
	    	
	    	 private String valor;

	    	statusConta(String valor) {
	 	        this.valor = valor;
	 	    }
	 	    public String getValor() {
	 	        return valor;
	 	    }
	    	
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
		public tiposConta getTipo() {
			return tipo;
		}
		public void setTipo(tiposConta tipo) {
			this.tipo = tipo;
		}
		public statusConta getStatus() {
			return status;
		}
		public void setStatus(statusConta status) {
			this.status = status;
		}
		public Timestamp getDataCriacao() {
			return DataCriacao;
		}
		public void setDataCriacao(Timestamp dataCriacao) {
			DataCriacao = dataCriacao;
		}
		
		public Conta(int id, int usuarioId, BigDecimal saldo, tiposConta tipo, statusConta status) {
			super();
			this.id = id;
			this.usuarioId = usuarioId;
			this.saldo = saldo;
			this.tipo = tipo;
			this.status = status;
			this.DataCriacao = new Timestamp(System.currentTimeMillis());
		}
	    
	    
}
