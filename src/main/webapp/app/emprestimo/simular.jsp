<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="edu.ifsp.banco.modelo.Usuario"%>
<%@ page import="edu.ifsp.banco.modelo.Conta"%>
<%
Usuario user = (Usuario) session.getAttribute("usuarioLogado");
Conta conta = (Conta) session.getAttribute("contaLogado");
if (conta == null) {
	response.sendRedirect(request.getContextPath() + "/login.jsp");
	return;
}
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>BitPay | Simular Empréstimo</title>
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

.hero-header {
	background: linear-gradient(135deg, #0d6efd 0%, #000000 100%);
	color: white;
	padding: 40px 0;
	margin-bottom: 30px;
}

.card-custom {
	border: none;
	border-radius: 15px;
	box-shadow: 0 10px 20px rgba(0, 0, 0, 0.08);
	padding: 30px;
	background: white;
}
</style>
</head>
<body>

	<nav class="navbar navbar-dark bg-dark py-3">
		<div class="container">
			<a class="navbar-brand fw-bold" href="#">BitPay <span
				class="badge bg-primary">IB</span></a> <a
				href="<%=request.getContextPath()%>/app/home.jsp"
				class="btn btn-outline-light btn-sm">Voltar</a>
		</div>
	</nav>

	<header class="hero-header text-center">
		<div class="container">
			<h2>Simulação de Crédito SAC</h2>
			<p class="opacity-75">Planeje seu futuro com taxas transparentes.</p>
		</div>
	</header>

	<div class="container pb-5">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card-custom">
					<form action="<%=request.getContextPath()%>/app" method="post">
						<input type="hidden" name="command" value="simularEmprestimo">
						<input type="hidden" name="idConta" value="<%=conta.getId()%>">

						<div class="mb-3">
							<label class="form-label fw-bold">Valor do Empréstimo</label>
							<div class="input-group">
								<span class="input-group-text">R$</span> <input type="number"
									name="valor" class="form-control" step="0.01" min="100"
									placeholder="Ex: 5000,00" required>
							</div>
						</div>

						<div class="mb-3">
							<label class="form-label fw-bold">Parcelamento</label> <select
								name="parcelas" class="form-select" required>
								<option value="6">6 meses</option>
								<option value="12" selected>12 meses</option>
								<option value="24">24 meses</option>
								<option value="36">36 meses</option>
								<option value="48">48 meses</option>
							</select>
						</div>

						<div class="mb-4">
							<h3 class="form-label fw-bold">
								Taxa de Juros <%= request.getSession().getAttribute("taxaPadrao") %>% a.m.</h3>
						</div>

						<div class="d-grid">
							<button type="submit" class="btn btn-primary btn-lg rounded-pill">
								Simular Agora</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>