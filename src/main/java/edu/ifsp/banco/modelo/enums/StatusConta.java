package edu.ifsp.banco.modelo.enums;

public enum StatusConta {
	ATIVA("ATIVA"), BLOQUEADA("BLOQUEADA");

	private String valor;

	StatusConta(String valor) {
	        this.valor = valor;
	    }

	public String getValor() {
		return valor;
	}
}
