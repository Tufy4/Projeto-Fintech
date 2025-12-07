<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Deposito</title>
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

.deposit-form {
	background-color: white;
	padding: 30px;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	max-width: 450px;
	margin: 0 auto;
}

.deposit-form label {
	font-size: 1.1rem;
	font-weight: 500;
}

.deposit-form input {
	width: 100%;
	padding: 12px;
	margin-bottom: 15px;
	font-size: 1rem;
	border-radius: 5px;
	border: 1px solid #ccc;
}

.deposit-form input:focus {
	border-color: #0d6efd;
	box-shadow: 0 0 5px rgba(13, 110, 253, 0.5);
}

.deposit-form button {
	width: 100%;
	padding: 12px;
	background-color: #0d6efd;
	color: white;
	border: none;
	border-radius: 5px;
	font-size: 1.1rem;
	cursor: pointer;
	font-weight: bold;
}

.deposit-form button:hover {
	background-color: #0056b3;
}

.deposit-form .note {
	font-size: 0.9rem;
	color: #666;
}

.navbar-brand {
	font-weight: bold;
}

.navbar-dark .navbar-nav .nav-link {
	color: white;
}

.navbar-dark .navbar-nav .nav-link:hover {
	color: #0d6efd;
}

.center-wrapper {
	min-height: calc(100vh - 80px); /* altura total menos a navbar */
	display: flex;
	align-items: center;
	justify-content: center;
}

.deposit-title {
	text-align: center;
	font-size: 2rem;
	font-weight: 600;
	margin-bottom: 20px;
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
					<li class="nav-item"><a class="btn btn-outline-primary btn-sm"
						href="${pageContext.request.contextPath}/app/home.jsp">HOME</a></li>
					<li class="nav-item"><span class="nav-link text-white">Olá,
							<strong>${sessionScope.usuarioLogado != null ?
sessionScope.usuarioLogado.getNome() : 'Cliente'}</strong>
					</span></li>
					<li class="nav-item"><a class="btn btn-outline-danger btn-sm"
						href="${pageContext.request.contextPath}/app?command=logout">Sair</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<section id="deposito" class="container center-wrapper">

		<h2>
			Seja Bem-vindo, <strong>${sessionScope.usuarioLogado != null ?
sessionScope.usuarioLogado.getNome() : 'Cliente'}</strong>
			<br> Saldo atual: R$ <strong>${sessionScope.saldoConta != null ? sessionScope.saldoConta : '0.00'}</strong>
		</h2>

		<div class="deposit-form">
			<h2 class="deposit-title">Depósito</h2>

			<form
				action="${pageContext.request.contextPath}/app?command=depositar"
				method="post">
				<input type="hidden" name="command" value="Depositar"> <label
					for="idConta">Numero da conta depositada: </label> <input
					type="number" id="idConta" name="idConta" required> <label
					for="valor">Valor (R$):</label> <input type="number" step="0.01"
					id="valor" name="valor" required>

				<p class="note">Digite o valor à ser depositado!</p>

				<button type="submit">Depositar</button>
			</form>
		</div>

	</section>

</body>
</html>