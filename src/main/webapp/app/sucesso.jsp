<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>BitPay | ${not empty titulo ? titulo : 'Sucesso'}</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body class="bg-light">

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
						href="${pageContext.request.contextPath}/app?command=dashboardCliente">HOME</a>
					</li>
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

	<div class="container d-flex justify-content-center align-items-center"
		style="min-height: 80vh;">
		<div class="col-12 col-sm-10 col-md-8 col-lg-6">
			<div class="card shadow border-0">
				<div class="card-body text-center p-4 p-md-5">

					<div class="mb-4">
						<i class="bi bi-check-circle-fill text-success"
							style="font-size: 4rem;"></i>
					</div>

					<h4 class="card-title fw-bold mb-3">${not empty titulo ? titulo : 'Operação realizada com sucesso!'}
					</h4>

					<p class="card-text text-muted mb-4 fs-5">${not empty msg ? msg : 'Sua solicitação foi processada corretamente.'}
					</p>

					<a
						href="${not empty linkDestino ? linkDestino : pageContext.request.contextPath + '/app?command=dashboardCliente'}"
						class="btn btn-primary btn-lg w-100"> ${not empty textoBotao ? textoBotao : 'Voltar para o Início'}
					</a>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>