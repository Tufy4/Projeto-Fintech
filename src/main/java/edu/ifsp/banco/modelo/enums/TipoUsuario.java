package edu.ifsp.banco.modelo.enums;

public enum TipoUsuario {
	CLIENTE("CLIENTE"), GERENTE("GERENTE");

	private String valor;

	TipoUsuario(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
}
