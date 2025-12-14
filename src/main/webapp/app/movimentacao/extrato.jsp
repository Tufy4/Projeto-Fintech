<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="edu.ifsp.banco.modelo.Movimentacoes"%>
<%@ page import="edu.ifsp.banco.modelo.Conta"%>
<%@ page import="edu.ifsp.banco.modelo.enums.TipoMovimentacao"%>

<%
Conta conta = (Conta) session.getAttribute("contaLogado");
List<Movimentacoes> lista = (List<Movimentacoes>) request.getAttribute("listaExtrato");

String filtroInicio = (String) request.getAttribute("filtroInicio");
String filtroFim = (String) request.getAttribute("filtroFim");

NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>BitPay | Extrato</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
<style>
body {
	background-color: #f8f9fa;
	font-family: 'Segoe UI', sans-serif;
}

.text-entrada {
	color: #198754;
	font-weight: bold;
}

.text-saida {
	color: #dc3545;
	font-weight: bold;
}

.hist-saldo {
	font-size: 0.85em;
	color: #6c757d;
}

.hist-label {
	font-size: 0.75em;
	color: #adb5bd;
	text-transform: uppercase;
}
</style>
</head>
<body>

	<nav class="navbar navbar-dark bg-dark py-3 mb-4">
		<div class="container">
			<a class="navbar-brand fw-bold" href="#">BitPay <span
				class="badge bg-primary">IB</span></a> <a
				href="<%=request.getContextPath()%>/app?command=dashboardCliente"
				class="btn btn-outline-light btn-sm">Voltar</a>
		</div>
	</nav>

	<div class="container pb-5">

		<div
			class="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-3">
			<div>
				<h2 class="fw-bold text-secondary mb-0">
					<i class="bi bi-list-columns-reverse me-2"></i>Extrato
				</h2>
			</div>

			<form action="app" method="get" class="d-flex gap-2 align-items-end">
				<input type="hidden" name="command" value="extrato">

				<div>
					<label class="form-label small mb-0 text-muted">Início</label> <input
						type="date" class="form-control form-control-sm" name="dataInicio"
						value="<%=filtroInicio != null ? filtroInicio : ""%>">
				</div>
				<div>
					<label class="form-label small mb-0 text-muted">Fim</label> <input
						type="date" class="form-control form-control-sm" name="dataFim"
						value="<%=filtroFim != null ? filtroFim : ""%>">
				</div>
				<button type="submit" class="btn btn-primary btn-sm">
					<i class="bi bi-filter"></i> Filtrar
				</button>
				<%
				if (filtroInicio != null || filtroFim != null) {
				%>
				<a href="app?command=extrato"
					class="btn btn-outline-secondary btn-sm">Limpar</a>
				<%
				}
				%>
			</form>
		</div>

		<div class="row mb-4">
			<div class="col-12">
				<div class="card border-0 shadow-sm bg-primary text-white">
					<div
						class="card-body py-3 d-flex justify-content-between align-items-center">
						<span class="opacity-75">Saldo Atual Disponível</span> <span
							class="fs-4 fw-bold"><%=nf.format(conta.getSaldo())%></span>
					</div>
				</div>
			</div>
		</div>

		<div class="card shadow-sm border-0">
			<div class="card-body p-0">
				<%
				if (lista == null || lista.isEmpty()) {
				%>
				<div class="p-5 text-center text-muted">
					<i class="bi bi-calendar-x display-1 mb-3"></i>
					<h4>Nenhuma movimentação encontrada para o período.</h4>
				</div>
				<%
				} else {
				%>
				<div class="table-responsive">
					<table class="table table-hover mb-0 align-middle">
						<thead class="table-light small text-uppercase text-muted">
							<tr>
								<th class="ps-4">Data</th>
								<th>Descrição</th>
								<th class="text-center d-none d-md-table-cell">Saldo Ant.</th>
								<th class="text-end pe-4">Valor</th>
								<th class="text-center d-none d-md-table-cell">Saldo Post.</th>
							</tr>
						</thead>
						<tbody>
							<%
							for (Movimentacoes mov : lista) {
								boolean isEntrada = false;
								String descricaoExtra = "";
								if (mov.getTipo() == TipoMovimentacao.DEPOSITO || mov.getTipo() == TipoMovimentacao.TRANSFERENCIA_RECEBIDA
								|| mov.getTipo() == TipoMovimentacao.EMPRESTIMO) {
									isEntrada = true;
								}

								if (mov.getTipo() == TipoMovimentacao.TRANSFERENCIA_ENVIADA) {
									descricaoExtra = "Para: Conta ID " + mov.getContaDestinoId();
								} else if (mov.getTipo() == TipoMovimentacao.TRANSFERENCIA_RECEBIDA) {
									descricaoExtra = "De: Conta ID " + mov.getContaOrigemId();
								}

								String classeCor = isEntrada ? "text-entrada" : "text-saida";
								String sinal = isEntrada ? "+" : "-";
							%>
							<tr>
								<td class="ps-4 text-muted small" style="white-space: nowrap;">
									<%=(mov.getDataTransacao() != null) ? sdf.format(mov.getDataTransacao()) : "-"%>
								</td>

								<td><span class="fw-bold text-dark d-block"><%=mov.getDescricao()%></span>
									<small class="text-muted"><%=descricaoExtra%></small></td>

								<td class="text-center hist-saldo d-none d-md-table-cell">
									<%=nf.format(mov.getSaldoAnterior())%>
								</td>

								<td class="text-end pe-4 <%=classeCor%>"><%=sinal%> <%=nf.format(mov.getValor())%>
								</td>

								<td class="text-center hist-saldo d-none d-md-table-cell">
									<strong><%=nf.format(mov.getSaldoPosterior())%></strong>
								</td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
				</div>
				<%
				}
				%>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>