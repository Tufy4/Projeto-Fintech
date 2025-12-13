package edu.ifsp.banco.modelo.enums;

public enum TiposConta {
	CLIENTE("CLIENTE"), GERENTE("GERENTE");

	private String valor;

	TiposConta(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
}
