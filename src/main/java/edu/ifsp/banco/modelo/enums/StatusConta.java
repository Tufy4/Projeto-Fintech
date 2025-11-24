package edu.ifsp.banco.modelo.enums;

public enum StatusConta {
	ATIVO("ATIVA"), BLOQUEADO("BLOQUEADA");

	private String valor;

	StatusConta(String valor) {
	        this.valor = valor;
	    }

	public String getValor() {
		return valor;
	}
}
