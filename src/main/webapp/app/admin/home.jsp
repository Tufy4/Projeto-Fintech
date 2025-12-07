<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
}

.admin-card:hover {
	transform: translateY(-5px);
	border-color: #0d6efd;
}
</style>
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
		<div class="container">
			<a class="navbar-brand fw-bold"
				href="${pageContext.request.contextPath}/app/admin/home.jsp">
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
							Olá, <strong>${sessionScope.usuarioLogado != null ? sessionScope.usuarioLogado.getNome() : 'Administrador'}</strong>
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

		<div class="row row-cols-1 row-cols-md-3 g-4">

			<div class="col">
				<a
					href="${pageContext.request.contextPath}/app?command=consultarBloqueados"
					class="text-decoration-none">
					<div class="card h-100 border-0 shadow-sm admin-card">
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
					href="${pageContext.request.contextPath}/app?command=listarEmprestimosGerente"
					class="text-decoration-none">
					<div class="card h-100 border-0 shadow-sm admin-card">
						<div class="card-body text-center p-4">
							<div class="feature-icon">
								<i class="bi bi-person-check-fill"></i>
							</div>
							<h5 class="card-title text-dark">Emprestimos solicitados</h5>
							<p class="card-text text-muted small">Consultar e aprovar
								emprestimos pendentes.</p>
						</div>
					</div>
				</a>
			</div>

			<div class="col">
				<div class="card h-100 border-0 shadow-sm opacity-50"
					style="cursor: not-allowed;">
					<div class="card-body text-center p-4">
						<div class="feature-icon text-secondary">
							<i class="bi bi-bar-chart-fill"></i>
						</div>
						<h5 class="card-title text-secondary">Relatórios</h5>
						<p class="card-text text-muted small">Em breve: Visualização
							de transações globais.</p>
					</div>
				</div>
			</div>

		</div>
	</div>

</body>
</html>