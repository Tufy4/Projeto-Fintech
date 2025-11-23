package edu.ifsp.banco.modelo.enums;

public enum StatusUsuario {
	ATIVO("ATIVO"), INATIVO("INATIVO"), BLOQUEADO("BLOQUEADO");

	private String valor;

	StatusUsuario(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
}
