<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BitPay | Inovação Financeira</title>
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
	padding: 100px 0;
}

.feature-icon {
	font-size: 2.5rem;
	color: #0d6efd;
	margin-bottom: 1rem;
}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand fw-bold" href="#">BitPay</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse justify-content-end"
				id="navbarNav">
				<ul class="navbar-nav gap-2">
					<li class="nav-item"><a class="nav-link active" href="#">Home</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#recursos">Recursos</a>
					</li>
					<li class="nav-item"><a class="btn btn-outline-light btn-sm"
						href="${pageContext.request.contextPath}/app?command=redirect&url=auth/login.jsp">Entrar</a>
					</li>
					<li class="nav-item"><a class="btn btn-primary btn-sm"
						href="${pageContext.request.contextPath}/app?command=cadastro">Abra
							sua Conta</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Hero Section -->
	<header class="hero-section text-center">
		<div class="container">
			<h1 class="display-4 fw-bold mb-4">O Futuro do Seu Dinheiro</h1>
			<p class="lead mb-4">
				Segurança, rentabilidade e tecnologia em um só lugar. <br>Gerencie
				seus ativos com a inteligência da BitPay.
			</p>
			<div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
				<a href="${pageContext.request.contextPath}/app?command=cadastro"
					class="btn btn-primary btn-lg px-4 gap-3">Começar Agora</a> <a
					href="${pageContext.request.contextPath}/app?command=login"
					class="btn btn-outline-light btn-lg px-4">Já sou cliente</a>
			</div>
		</div>
	</header>

	<!-- Features Section -->
	<section id="recursos" class="container py-5">
		<div class="row g-4 py-5 row-cols-1 row-cols-lg-3">
			<div class="col">
				<div
					class="feature-col text-center p-4 border rounded shadow-sm h-100">
					<div class="feature-icon">🔒</div>
					<h3 class="fs-2">Segurança Total</h3>
					<p>Seus dados e transações protegidos com as melhores práticas
						de criptografia e controle de acesso.</p>
				</div>
			</div>
			<div class="col">
				<div
					class="feature-col text-center p-4 border rounded shadow-sm h-100">
					<div class="feature-icon">📈</div>
					<h3 class="fs-2">Investimentos</h3>
					<p>Diversifique sua carteira com opções de renda fixa e
						variável. Rentabilidade calculada em tempo real.</p>
				</div>
			</div>
			<div class="col">
				<div
					class="feature-col text-center p-4 border rounded shadow-sm h-100">
					<div class="feature-icon">🏠</div>
					<h3 class="fs-2">Crédito SAC</h3>
					<p>Simule e contrate empréstimos com transparência. Sistema de
						Amortização Constante integrado.</p>
				</div>
			</div>
		</div>
	</section>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>