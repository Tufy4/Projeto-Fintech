<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="edu.ifsp.banco.modelo.Usuario"%>
<%
Integer countBloqueados = (Integer) request.getAttribute("countBloqueados");
Integer countEmprestimos = (Integer) request.getAttribute("countEmprestimos");
Integer countContas = (Integer) request.getAttribute("countContas");

if (countBloqueados == null)
	countBloqueados = 0;
if (countEmprestimos == null)
	countEmprestimos = 0;
if (countContas == null)
	countContas = 0;

Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
String nomeUsuario = (usuarioLogado != null) ? usuarioLogado.getNome() : "Administrador";
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BitPay Admin | Home</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
<style>
body {
	background-color: #f8f9fa;
}

.feature-icon {
	font-size: 2rem;
	color: #0d6efd;
	margin-bottom: 1rem;
}

.admin-card {
	transition: transform 0.2s;
	cursor: pointer;
	position: relative;
	overflow: visible;
}

.admin-card:hover {
	transform: translateY(-5px);
	border-color: #0d6efd;
}

.metric-card {
	cursor: default;
	background-color: #fff;
	border: 1px solid #e9ecef;
}

.notification-badge {
	position: absolute;
	top: -10px;
	right: -10px;
	background-color: #fd7e14;
	color: white;
	border-radius: 50%;
	width: 40px;
	height: 40px;
	display: flex;
	align-items: center;
	justify-content: center;
	font-weight: bold;
	font-size: 1.1rem;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	border: 2px solid #fff;
	z-index: 10;
}

.notification-badge.zero {
	background-color: #6c757d;
	transform: scale(0.8);
}
</style>
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
		<div class="container">
			<a class="navbar-brand fw-bold"
				href="${pageContext.request.contextPath}/app?command=dashboardAdmin">
				BitPay <span class="badge bg-primary"
				style="font-size: 0.5em; vertical-align: top;">ADMIN</span>
			</a>

			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse justify-content-end"
				id="navbarNav">
				<ul class="navbar-nav gap-2 align-items-center">
					<li class="nav-item"><span class="nav-link text-white">
							Olá, <strong><%=nomeUsuario%></strong>
					</span></li>
					<li class="nav-item"><a class="btn btn-outline-danger btn-sm"
						href="${pageContext.request.contextPath}/app?command=logout">
							<i class="bi bi-box-arrow-right"></i> Sair
					</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<h2 class="mb-4 text-secondary">Painel Administrativo</h2>

		<%
		String erro = (String) request.getAttribute("erro");
		if (erro != null) {
		%>
		<div class="alert alert-danger"><%=erro%></div>
		<%
		}
		%>

		<div class="row row-cols-1 row-cols-md-3 g-4 mt-2">

			<div class="col">
				<a
					href="${countBloqueados > 0 ? pageContext.request.contextPath.concat('/app?command=consultarBloqueados') : '#'}"
					class="text-decoration-none">
					<div class="card h-100 border-0 shadow-sm admin-card">

						<div
							class="notification-badge <%=countBloqueados == 0 ? "zero" : ""%>">
							<%=countBloqueados%>
						</div>

						<div class="card-body text-center p-4">
							<div class="feature-icon">
								<i class="bi bi-person-check-fill"></i>
							</div>
							<h5 class="card-title text-dark">Liberação de Usuários</h5>
							<p class="card-text text-muted small">Consultar e aprovar
								contas que estão com status bloqueado.</p>
						</div>
					</div>
				</a>
			</div>

			<div class="col">
				<a
					href="${countEmprestimos > 0 ? pageContext.request.contextPath.concat('/app?command=listarEmprestimosGerente') : '#'}"
					class="text-decoration-none">
					<div class="card h-100 border-0 shadow-sm admin-card">

						<div
							class="notification-badge <%=countEmprestimos == 0 ? "zero" : ""%>">
							<%=countEmprestimos%>
						</div>

						<div class="card-body text-center p-4">
							<div class="feature-icon">
								<i class="bi bi-cash-coin"></i>
							</div>
							<h5 class="card-title text-dark">Empréstimos Solicitados</h5>
							<p class="card-text text-muted small">Consultar e aprovar
								empréstimos pendentes.</p>
						</div>
					</div>
				</a>
			</div>

			<div class="col">
				<div class="card h-100 shadow-sm metric-card">
					<div
						class="card-body text-center p-4 d-flex flex-column justify-content-center">

						<h5 class="card-title text-secondary mb-3">Total de Contas</h5>

						<h2 class="display-3 fw-bold text-primary mb-2">
							<%=countContas%>
						</h2>

						<p class="card-text text-muted small">
							<i class="bi bi-database-check"></i> Contas ativas na base
						</p>
					</div>
				</div>
			</div>

		</div>
	</div>

</body>
</html>