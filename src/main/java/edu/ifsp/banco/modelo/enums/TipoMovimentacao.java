package edu.ifsp.banco.modelo.enums;

public enum TipoMovimentacao {
	DEPOSITO("DEPOSITO"), TRANSFERENCIA_ENVIADA("TRANSFERENCIA_ENVIADA"),
	TRANSFERENCIA_RECEBIDA("TRANSFERENCIA_RECEBIDA"), INVESTIMENTO("INVESTIMENTO"), SAQUE("SAQUE"),
	EMPRESTIMO("EMPRESTIMO");

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
