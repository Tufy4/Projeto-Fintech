package edu.ifsp.banco.modelo.enums;

public enum StatusEmprestimo {
	SIMULADO("SIMULADO"), SOLICITADO("SOLICITADO"), APROVADO("APROVADO"), REJEITADO("REJEITADO"),
	EM_ANDAMENTO("EM_ANDAMENTO"), QUITADO("QUITADO");

	private String valor;

	StatusEmprestimo(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
}