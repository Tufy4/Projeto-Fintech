<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Depósito realizado</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
							<strong>${sessionScope.usuarioLogado != null ? sessionScope.usuarioLogado.getNome() : 'Cliente'}</strong>
					</span></li>
					<li class="nav-item"><a class="btn btn-outline-danger btn-sm"
						href="${pageContext.request.contextPath}/app?command=logout">Sair</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container" style="margin-top: 250px;">
		<div class="row justify-content-center">
			<div class="col-12 col-sm-10 col-md-8 col-lg-6">
				<div class="card shadow-sm">
					<div class="card-body text-center p-3 p-md-4 p-lg-5">
						<h4 class="card-title text-success">Empréstimo solicitado com sucesso!</h4>
						<p class="card-text">${msg}</p>
						<a href="${pageContext.request.contextPath}/app?command=dashboardCliente"
							class="btn btn-primary w-100">Voltar para a Home</a>
					</div>
				</div>
			</div>
		</div>
	</div>





	<!--funcionalidade do navbar-->
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
