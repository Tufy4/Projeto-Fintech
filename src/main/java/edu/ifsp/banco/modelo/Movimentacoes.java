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
	private Timestamp dataTransacao; // ALTERAR TRANSAÇÃO, REPRESENTAÇÃO DO DB VAI SER ALTERANDO TENDO A DATA DE
										// INICIO E FIM DA TRANSAÇÃO.
	private String descricao;
	private StatusMovimentacao status;

	public int getId() {
		return id;
	}

	StatusMovimentacao stats = StatusMovimentacao.CONCLUIDA;

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

	public String getTipo() {
		return tipo.getValor();
	}

	public void setTipo(String tipo) {
		this.tipo.setValor(tipo);
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

	public String getStatus() {
		return status.getValor();
	}

	public void setStatus(String status) {
		this.status.setValor(status);
	}

	public Movimentacoes(int id, int contaOrigemId, int contaDestinoId, BigDecimal valor, TipoMovimentacao tipo,
			Timestamp dataTransacao, String descricao, StatusMovimentacao status) {
		super();
		this.id = id;
		this.contaOrigemId = contaOrigemId;
		this.contaDestinoId = contaDestinoId;
		this.valor = valor;
		this.tipo = tipo;
		this.dataTransacao = new Timestamp(System.currentTimeMillis());
		this.descricao = descricao;
		this.status = status;
	}

	public Movimentacoes() {

	}

}
