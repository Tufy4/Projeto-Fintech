package edu.ifsp.banco.modelo.enums;

public enum StatusEmprestimo {
	SIMULADO("SIMULADO"), APROVADO("APROVADO"), EM_ANDAMENTO("EM_ANDAMENTO"), QUITADO("QUITADO");

	private String valor;

	StatusEmprestimo(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
}
