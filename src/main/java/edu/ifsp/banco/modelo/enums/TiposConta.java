package edu.ifsp.banco.modelo.enums;

public enum TiposConta {
	CORRENTE("CORRENTE"), POUPANCA("POUPANCA");

	private String valor;

	TiposConta(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
}
