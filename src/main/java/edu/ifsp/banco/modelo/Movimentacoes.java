package edu.ifsp.banco.modelo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Movimentacoes {
	private int id;
	private int contaOrigemId;
	private int contaDestinoId;
	private BigDecimal valor;
	private TipoMov tipo;
	private Timestamp dataTransacao; // ALTERAR TRANSAÇÃO, REPRESENTAÇÃO DO DB VAI SER ALTERANDO TENDO A DATA DE INICIO E FIM DA TRANSAÇÃO.
	private String descricao;
	private tipoStatus status;

	
	public enum TipoMov {
	    ATIVO("ATIVO"),
	    INATIVO("INATIVO"),
	    BLOQUEADO("BLOQUEADO");

	    private String valor;

	    TipoMov(String valor) {
	        this.valor = valor;
	    }
	    public String getValor() {
	        return valor;
	    }
	}
	    
	    
	    
	    public enum tipoStatus {
		    PENDENTE("PENDENTE"),
		    CONCLUIDA("CONCLUIDA"),
		    FALHA("FALHA");

		    private String valor;

		    tipoStatus(String valor) {
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



		public int getContaOrigemId() {
			return contaOrigemId;
		}



		public void setContaOrigemId(int contaOrigemId) {
			this.contaOrigemId = contaOrigemId;
		}



		public int getContaDestinoId() {
			return contaDestinoId;
		}



		public void setContaDestinoId(int contaDestinoId) {
			this.contaDestinoId = contaDestinoId;
		}



		public BigDecimal getValor() {
			return valor;
		}



		public void setValor(BigDecimal valor) {
			this.valor = valor;
		}



		public TipoMov getTipo() {
			return tipo;
		}



		public void setTipo(TipoMov tipo) {
			this.tipo = tipo;
		}



		public Timestamp getDataTransacao() {
			return dataTransacao;
		}



		public void setDataTransacao(Timestamp dataTransacao) {
			this.dataTransacao = dataTransacao;
		}



		public String getDescricao() {
			return descricao;
		}



		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}



		public tipoStatus getStatus() {
			return status;
		}



		public void setStatus(tipoStatus status) {
			this.status = status;
		}



		public Movimentacoes(int id, int contaOrigemId, int contaDestinoId, BigDecimal valor, TipoMov tipo,
				Timestamp dataTransacao, String descricao, tipoStatus status) {
			super();
			this.id = id;
			this.contaOrigemId = contaOrigemId;
			this.contaDestinoId = contaDestinoId;
			this.valor = valor;
			this.tipo = tipo;
			this.dataTransacao = new Timestamp(System.currentTimeMillis());
			this.descricao = descricao;
			this.status = status;
		}
	    
	    

}
