<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="edu.ifsp.banco.modelo.Emprestimo"%>
<%@ page import="edu.ifsp.banco.modelo.ParcelaEmprestimo"%>
<%@ page import="edu.ifsp.banco.modelo.Conta"%>
<%@ page import="edu.ifsp.banco.modelo.enums.StatusEmprestimo"%>
<%@ page import="edu.ifsp.banco.modelo.enums.StatusParcela"%>

<%
List<Emprestimo> lista = (List<Emprestimo>) request.getAttribute("meusEmprestimos");
Conta conta = (Conta) session.getAttribute("contaLogado");

NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>BitPay | Meus Empréstimos</title>
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

.accordion-button:not(.collapsed) {
	color: #0d6efd;
	background-color: #e7f1ff;
}

.status-badge {
	font-size: 0.8em;
	padding: 5px 10px;
	border-radius: 20px;
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
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h2 class="fw-bold text-secondary">Meus Empréstimos</h2>
			<a href="<%=request.getContextPath()%>/app?command=prepararSimulacao"
				class="btn btn-primary rounded-pill"> <i
				class="bi bi-plus-lg me-2"></i>Novo Empréstimo
			</a>
		</div>

		<%
		if (lista == null || lista.isEmpty()) {
		%>
		<div class="alert alert-light text-center border shadow-sm p-5">
			<i class="bi bi-wallet2 display-4 text-muted mb-3"></i>
			<h4>Você não possui empréstimos ativos.</h4>
			<p class="text-muted">Faça uma simulação e realize seus sonhos.</p>
		</div>
		<%
		} else {
		%>

		<div class="accordion shadow-sm" id="accordionEmprestimos">
			<%
			for (Emprestimo emp : lista) {
				String statusColor = "secondary";
				if (emp.getStatus() == StatusEmprestimo.EM_ANDAMENTO)
					statusColor = "primary";
				if (emp.getStatus() == StatusEmprestimo.QUITADO)
					statusColor = "success";
				if (emp.getStatus() == StatusEmprestimo.REJEITADO)
					statusColor = "danger";
				if (emp.getStatus() == StatusEmprestimo.SOLICITADO)
					statusColor = "warning text-dark";
			%>

			<div class="accordion-item">
				<h2 class="accordion-header">
					<button class="accordion-button collapsed" type="button"
						data-bs-toggle="collapse"
						data-bs-target="#collapse<%=emp.getId()%>">
						<div
							class="d-flex w-100 justify-content-between align-items-center me-3">
							<div>
								<span class="fw-bold">Empréstimo #<%=emp.getId()%></span> <span
									class="text-muted small ms-2"> Solicitado em: <%=(emp.getData_solicitacao() != null) ? dtf.format(emp.getData_solicitacao().toLocalDateTime()) : "-"%>
								</span>
							</div>
							<div class="text-end">
								<span class="badge bg-<%=statusColor%> status-badge me-3"><%=emp.getStatus()%></span>
								<span class="fw-bold text-dark"><%=nf.format(emp.getValor_emprestimo())%></span>
							</div>
						</div>
					</button>
				</h2>
				<div id="collapse<%=emp.getId()%>"
					class="accordion-collapse collapse"
					data-bs-parent="#accordionEmprestimos">
					<div class="accordion-body bg-light">

						<div class="row mb-3 pb-3 border-bottom">
							<div class="col-md-3">
								<small class="text-muted d-block">Taxa de Juros</small> <strong><%=emp.getTaxa_juros_mensal()%>%
									a.m.</strong>
							</div>
							<div class="col-md-3">
								<small class="text-muted d-block">Parcelamento</small> <strong><%=emp.getParcelas()%>
									vezes</strong>
							</div>
							<div class="col-md-3">
								<small class="text-muted d-block">Aprovado em</small> <strong><%=(emp.getData_aprovacao() != null) ? dtf.format(emp.getData_aprovacao().toLocalDateTime()) : "Pendente"%></strong>
							</div>
						</div>

						<h6 class="fw-bold text-secondary mb-3">Cronograma de
							Pagamento</h6>

						<%
						if (emp.getListaParcelas() != null && !emp.getListaParcelas().isEmpty()) {
						%>
						<div class="table-responsive bg-white rounded shadow-sm border">
							<table class="table table-hover mb-0 align-middle">
								<thead class="table-light">
									<tr>
										<th class="ps-3">#</th>
										<th>Vencimento</th>
										<th>Valor</th>
										<th>Situação</th>
										<th class="text-end pe-3">Ação</th>
									</tr>
								</thead>
								<tbody>
									<%
									for (ParcelaEmprestimo p : emp.getListaParcelas()) {
										boolean podePagar = (p.getStatus() == StatusParcela.PENDENTE)
										&& ((emp.getStatus() == StatusEmprestimo.APROVADO) || (emp.getStatus() == StatusEmprestimo.EM_ANDAMENTO));
									%>
									<tr>
										<td class="ps-3 fw-bold"><%=p.getNumeroParcela()%></td>
										<td><%=p.getDataVencimento().format(dtf)%></td>
										<td class="fw-bold"><%=nf.format(p.getValorParcela())%></td>
										<td>
											<%
											if (p.getStatus() == StatusParcela.PAGO) {
											%> <span class="badge bg-success">PAGO</span> <small
											class="text-muted d-block" style="font-size: 0.7rem;">
												<%=(p.getDataPagamento() != null) ? p.getDataPagamento().format(dtf) : ""%>
										</small> <%
 } else if (p.getStatus() == StatusParcela.ATRASADO) {
 %> <span class="badge bg-danger">ATRASADO</span> <%
 } else {
 %> <span class="badge bg-secondary">PENDENTE</span> <%
 }
 %>
										</td>
										<td class="text-end pe-3">
											<%
											if (podePagar) {
											%>
											<form action="<%=request.getContextPath()%>/app"
												method="post">
												<input type="hidden" name="command" value="pagarParcela">
												<input type="hidden" name="idParcela" value="<%=p.getId()%>">
												<button type="submit"
													class="btn btn-sm btn-outline-success rounded-pill px-3">
													<i class="bi bi-currency-dollar"></i> Pagar
												</button>
											</form> <%
 } else if (p.getStatus() == StatusParcela.PAGO) {
 %> <i class="bi bi-check-circle-fill text-success fs-5"></i> <%
 } else {
 %>
											<button class="btn btn-sm btn-light text-muted" disabled>Indisponível</button>
											<%
											}
											%>
										</td>
									</tr>
									<%
									}
									%>
								</tbody>
							</table>
						</div>
						<%
						} else {
						%>
						<p class="text-muted fst-italic">As parcelas serão geradas
							após a aprovação do gerente.</p>
						<%
						}
						%>

					</div>
				</div>
			</div>
			<%
			}
			%>
		</div>
		<%
		}
		%>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>