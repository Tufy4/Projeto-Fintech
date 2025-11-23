package edu.ifsp.banco.modelo.enums;

public enum StatusInvestimento {
	ATIVO("ATIVO"), ENCERRADO("ENCERRADO");

	private String valor;

	StatusInvestimento(String valor) {
	        this.valor = valor;
	    }

	public String getValor() {
		return valor;
	}
}
