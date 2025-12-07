<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="edu.ifsp.banco.modelo.ParcelaEmprestimo"%>
<%@ page import="edu.ifsp.banco.modelo.Conta"%>

<%
List<ParcelaEmprestimo> lista = (List<ParcelaEmprestimo>) request.getAttribute("listaSimulacao");
BigDecimal valorSolicitado = (BigDecimal) request.getAttribute("valorSolicitado");
Integer qtdParcelas = (Integer) request.getAttribute("qtdParcelas");
BigDecimal taxaJuros = (BigDecimal) request.getAttribute("taxaJuros");
Conta conta = (Conta) session.getAttribute("contaLogado");

NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Resultado da Simulação</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
body {
	background: #f8f9fa;
}

.summary-box {
	background: #fff;
	padding: 20px;
	border-radius: 10px;
	border-left: 5px solid #0d6efd;
	margin-bottom: 20px;
}

th {
	background-color: #e9ecef !important;
}
</style>
</head>
<body>

	<div class="container py-5">
		<h2 class="mb-4 text-center fw-bold">Resultado da Simulação</h2>

		<div class="row justify-content-center">
			<div class="col-lg-10">

				<div class="summary-box shadow-sm">
					<div class="row text-center">
						<div class="col-md-4">
							<small class="text-muted">Valor Solicitado</small>
							<h3 class="text-primary"><%=nf.format(valorSolicitado)%></h3>
						</div>
						<div class="col-md-4">
							<small class="text-muted">Prazo</small>
							<h3><%=qtdParcelas%>
								meses
							</h3>
						</div>
						<div class="col-md-4">
							<small class="text-muted">Taxa</small>
							<h3><%=taxaJuros%>% a.m.
							</h3>
						</div>
					</div>
				</div>

				<div class="card shadow-sm border-0 mb-4">
					<div class="card-body p-0">
						<table class="table table-striped mb-0 text-center align-middle">
							<thead>
								<tr>
									<th>#</th>
									<th>Vencimento</th>
									<th>Amortização</th>
									<th>Juros</th>
									<th>Total da Parcela</th>
								</tr>
							</thead>
							<tbody>
								<%
								if (lista != null) {
									for (ParcelaEmprestimo p : lista) {
								%>
								<tr>
									<td><strong><%=p.getNumeroParcela()%></strong></td>
									<td><%=p.getDataVencimento().format(dtf)%></td>
									<td class="text-secondary"><%=nf.format(p.getValorAmortizacao())%></td>
									<td class="text-danger small">+ <%=nf.format(p.getValorJuros())%></td>
									<td class="fw-bold text-dark"><%=nf.format(p.getValorParcela())%></td>
								</tr>
								<%
								}
								}
								%>
							</tbody>
						</table>
					</div>
				</div>

				<div class="d-flex justify-content-center gap-3">
					<a
						href="<%=request.getContextPath()%>/app?command=redirect&url=app/emprestimo/simular.jsp"
						class="btn btn-secondary px-4">Nova Simulação</a>

					<form action="<%=request.getContextPath()%>/app" method="post">
						<input type="hidden" name="command" value="solicitarEmprestimo">
						<input type="hidden" name="valor" value="<%=valorSolicitado%>">
						<input type="hidden" name="parcelas" value="<%=qtdParcelas%>">
						<input type="hidden" name="taxa" value="<%=taxaJuros%>">
						<input type="hidden" name="idConta" value="<%=conta.getId()%>">

						<button type="submit" class="btn btn-success px-5 fw-bold">Confirmar
							Solicitação</button>
					</form>
				</div>

			</div>
		</div>
	</div>

</body>
</html>