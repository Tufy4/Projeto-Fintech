package edu.ifsp.banco.modelo.enums;

public enum StatusParcela {
	PENDENTE("PENDENTE"), PAGO("PAGO"), ATRASADO("ATRASADO");

	private String valor;

	StatusParcela(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
