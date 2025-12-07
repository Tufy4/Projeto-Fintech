package edu.ifsp.banco.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import edu.ifsp.banco.modelo.enums.StatusParcela;

public class ParcelaEmprestimo {
	private int id;
	private int emprestimoId;
	private int numeroParcela;
	private BigDecimal valorParcela;
	private BigDecimal valorAmortizacao;
	private BigDecimal valorJuros;
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;
	private StatusParcela status;

	public ParcelaEmprestimo(int id, int emprestimoId, int numeroParcela, BigDecimal valorParcela,
			LocalDate dataVencimento, StatusParcela status) {
		this.id = id;
		this.emprestimoId = emprestimoId;
		this.numeroParcela = numeroParcela;
		this.valorParcela = valorParcela;
		this.dataVencimento = dataVencimento;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmprestimoId() {
		return emprestimoId;
	}

	public void setEmprestimoId(int emprestimoId) {
		this.emprestimoId = emprestimoId;
	}

	public int getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(int numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

	public BigDecimal getValorAmortizacao() {
		return valorAmortizacao;
	}

	public void setValorAmortizacao(BigDecimal valorAmortizacao) {
		this.valorAmortizacao = valorAmortizacao;
	}

	public BigDecimal getValorJuros() {
		return valorJuros;
	}

	public void setValorJuros(BigDecimal valorJuros) {
		this.valorJuros = valorJuros;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public StatusParcela getStatus() {
		return status;
	}

	public void setStatus(StatusParcela status) {
		this.status = status;
	}
}
