package edu.ifsp.banco.modelo.enums;

public enum TipoMovimentacao {
	DEPOSITO("DEPOSITO"), TRANSFERENCIA("TRANSFERENCIA"), INVESTIMENTO("INVESTIMENTO"), SAQUE("SAQUE");

	private String valor;

	TipoMovimentacao(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
