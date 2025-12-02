<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BitPay | Minha Conta</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
.hero-section {
	background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)),
		url('https://images.unsplash.com/photo-1565514020176-dbf2277e4c73?auto=format&fit=crop&w=1920&q=80');
	background-size: cover;
	background-position: center;
	color: white;
	padding: 80px 0;
}

.feature-icon {
	font-size: 2.5rem;
	color: #0d6efd;
	margin-bottom: 1rem;
}

.disabled-card {
	opacity: 0.6;
	cursor: not-allowed;
	background-color: #f8f9fa;
}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand fw-bold" href="#">BitPay <span
				class="badge bg-primary"
				style="font-size: 0.5em; vertical-align: top;">IB</span></a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse justify-content-end"
				id="navbarNav">
				<ul class="navbar-nav gap-2 align-items-center">
					<li class="nav-item"><span class="nav-link text-white">Olá,
							<strong>${sessionScope.usuarioLogado != null ? sessionScope.usuarioLogado.getNome() : 'Cliente'}</strong>
					</span></li>
					<li class="nav-item"><a class="btn btn-outline-danger btn-sm"
						href="${pageContext.request.contextPath}/app?command=logout">Sair</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<section id="operacoes" class="container py-5">

		<div class="col">
			<a
				href="${pageContext.request.contextPath}/app?command=consultarBloqueados"
				class="text-decoration-none text-dark">
				<div
					class="feature-col text-center p-4 border rounded shadow-sm h-100 bg-white">

					<div class="feature-icon">📄</div>

					<h3 class="fs-4">Liberação</h3>
					<p class="small text-muted">Consultando usuarios que necessitam
						de liberação</p>
				</div>
			</a>
		</div>


	</section>

</body>
</html>