package edu.ifsp.banco.modelo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Investimento {
	private int id;
	private int idConta;
	private String tipoInvestimento;
	private BigDecimal ValorInvestido;
	private Timestamp DataInicio;
	private Timestamp DataFim; 
    
    public enum statusInvestimento{
    	ATIVO("ATIVO"),
    	ENCERRADO("ENCERRADO");
    	
    	 private String valor;

    	 statusInvestimento(String valor) {
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

	public int getIdConta() {
		return idConta;
	}

	public void setIdConta(int idConta) {
		this.idConta = idConta;
	}

	public String getTipoInvestimento() {
		return tipoInvestimento;
	}

	public void setTipoInvestimento(String tipoInvestimento) {
		this.tipoInvestimento = tipoInvestimento;
	}

	public BigDecimal getValorInvestido() {
		return ValorInvestido;
	}

	public void setValorInvestido(BigDecimal valorInvestido) {
		ValorInvestido = valorInvestido;
	}

	public Timestamp getDataInicio() {
		return DataInicio;
	}

	public void setDataInicio(Timestamp dataInicio) {
		DataInicio = dataInicio;
	}

	public Timestamp getDataFim() {
		return DataFim;
	}

	public void setDataFim(Timestamp dataFim) {
		DataFim = dataFim;
	}

	public Investimento(int id, int idConta, String tipoInvestimento, BigDecimal valorInvestido, Timestamp dataInicio,
			Timestamp dataFim) {
		super();
		this.id = id;
		this.idConta = idConta;
		this.tipoInvestimento = tipoInvestimento;
		ValorInvestido = valorInvestido;
		DataInicio = dataInicio;
		DataFim = dataFim;
	}
    
    
    
    
}
