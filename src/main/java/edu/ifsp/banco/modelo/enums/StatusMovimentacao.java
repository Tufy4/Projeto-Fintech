package edu.ifsp.banco.modelo.enums;

public enum StatusMovimentacao {
	PENDENTE("PENDENTE"), CONCLUIDA("CONCLUIDA"), FALHA("FALHA");

	private String valor;

	StatusMovimentacao(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
