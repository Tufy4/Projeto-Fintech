package edu.ifsp.banco.modelo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import edu.ifsp.banco.modelo.enums.StatusMovimentacao;
import edu.ifsp.banco.modelo.enums.TipoMovimentacao;

public class Movimentacoes {
	private int id;
	private int contaOrigemId;
	private int contaDestinoId;
	private BigDecimal valor;
	private TipoMovimentacao tipo;
	private Timestamp dataTransacao;
	private String descricao;
	private StatusMovimentacao status;

	public Movimentacoes() {
	}

	public Movimentacoes(int id, int contaOrigemId, int contaDestinoId, BigDecimal valor, TipoMovimentacao tipo,
			Timestamp dataTransacao, String descricao, StatusMovimentacao status) {
		this.id = id;
		this.contaOrigemId = contaOrigemId;
		this.contaDestinoId = contaDestinoId;
		this.valor = valor;
		this.tipo = tipo;
		this.dataTransacao = dataTransacao;
		this.descricao = descricao;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getContaOrigemId() {
		return contaOrigemId;
	}

	public void setContaOrigemId(int contaOrigemId) {
		this.contaOrigemId = contaOrigemId;
	}

	public int getContaDestinoId() {
		return contaDestinoId;
	}

	public void setContaDestinoId(int contaDestinoId) {
		this.contaDestinoId = contaDestinoId;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

    
	public TipoMovimentacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoMovimentacao tipo) {
		this.tipo = tipo;
	}

	public Timestamp getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Timestamp dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public StatusMovimentacao getStatus() {
		return status;
	}

	public void setStatus(StatusMovimentacao status) {
		this.status = status;
	}
}