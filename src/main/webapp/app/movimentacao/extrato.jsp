<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="edu.ifsp.banco.modelo.Movimentacoes"%>
<%@ page import="edu.ifsp.banco.modelo.Conta"%>
<%@ page import="edu.ifsp.banco.modelo.enums.TipoMovimentacao"%>

<%
Conta conta = (Conta) session.getAttribute("contaLogado");
List<Movimentacoes> lista = (List<Movimentacoes>) request.getAttribute("listaExtrato");

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
} /* Verde */
.text-saida {
	color: #dc3545;
	font-weight: bold;
} /* Vermelho */
.table-hover tbody tr:hover {
	background-color: rgba(0, 0, 0, 0.02);
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
		<div class="row mb-4">
			<div class="col-md-8">
				<h2 class="fw-bold text-secondary">
					<i class="bi bi-list-columns-reverse me-2"></i>Extrato de
					Movimentações
				</h2>
			</div>
			<div class="col-md-4 text-md-end">
				<div class="card border-0 shadow-sm bg-primary text-white">
					<div class="card-body py-2">
						<small class="d-block opacity-75">Saldo Atual</small> <span
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
					<i class="bi bi-receipt display-1 mb-3"></i>
					<h4>Nenhuma movimentação encontrada.</h4>
				</div>
				<%
				} else {
				%>
				<div class="table-responsive">
					<table class="table table-hover mb-0 align-middle">
						<thead class="table-light">
							<tr>
								<th class="ps-4">Data</th>
								<th>Descrição</th>
								<th>Tipo</th>
								<th class="text-end pe-4">Valor</th>
							</tr>
						</thead>
						<tbody>
							<%
							for (Movimentacoes mov : lista) {
								boolean isEntrada = false;

								if (mov.getTipo() == TipoMovimentacao.DEPOSITO) {
									isEntrada = true;
								} else if (mov.getContaDestinoId() == conta.getId()) {
									isEntrada = true;
								} else {
									isEntrada = false; // Transferência, Saque, Pagamento
								}

								String classeCor = isEntrada ? "text-entrada" : "text-saida";
								String sinal = isEntrada ? "+" : "-";
							%>
							<tr>
								<td class="ps-4 text-muted small"><%=(mov.getDataTransacao() != null) ? sdf.format(mov.getDataTransacao()) : "-"%>
								</td>
								<td><span class="fw-bold text-dark"><%=mov.getDescricao()%></span>
									<%
									if (mov.getTipo() == TipoMovimentacao.TRANSFERENCIA) {
									%> <br> <small class="text-muted"> <%=isEntrada ? "De: Conta " + mov.getContaOrigemId() : "Para: Conta " + mov.getContaDestinoId()%>
								</small> <%
 }
 %></td>
								<td><span class="badge bg-light text-dark border"><%=mov.getTipo()%></span></td>
								<td class="text-end pe-4 <%=classeCor%>"><%=sinal%> <%=nf.format(mov.getValor())%>
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