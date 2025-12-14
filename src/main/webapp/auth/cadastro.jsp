<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cadastro | BitPay</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
<style>
body {
	background: linear-gradient(135deg, rgba(0, 0, 0, 0.8) 0%,
		rgba(13, 110, 253, 0.6) 100%),
		url('https://images.unsplash.com/photo-1565514020176-dbf2277e4c73?auto=format&fit=crop&w=1920&q=80');
	background-size: cover;
	background-position: center;
	min-height: 100vh;
	display: flex;
	align-items: center;
	justify-content: center;
	font-family: 'Segoe UI', sans-serif;
	padding: 20px;
}

.auth-card {
	background: white;
	border-radius: 20px;
	padding: 40px;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.3);
	width: 100%;
	max-width: 500px;
}

.brand-logo {
	text-align: center;
	margin-bottom: 20px;
	color: #0d6efd;
	font-size: 2rem;
	font-weight: bold;
}

.btn-register {
	padding: 12px;
	font-size: 1.1rem;
	font-weight: 600;
	border-radius: 10px;
}

.back-link {
	position: absolute;
	top: 20px;
	left: 20px;
	color: white;
	text-decoration: none;
	font-weight: 500;
	opacity: 0.8;
	transition: 0.3s;
}

.back-link:hover {
	opacity: 1;
	color: white;
}
</style>
</head>
<body>

	<a
		href="${pageContext.request.contextPath}/app?command=redirect&url=auth/login.jsp"
		class="back-link"> <i class="bi bi-arrow-left"></i> Voltar para
		Login
	</a>

	<div class="auth-card">
		<div class="brand-logo">
			<i class="bi bi-person-plus-fill"></i> Nova Conta
		</div>

		<h5 class="text-center mb-4 text-muted">Junte-se ao futuro
			financeiro</h5>

		<%
		if (request.getAttribute("erro") != null) {
		%>
		<div class="alert alert-danger" role="alert">${erro}</div>
		<%
		}
		%>

		<form
			action="${pageContext.request.contextPath}/app?command=cadastrarUsuario"
			method="post">

			<div class="form-floating mb-3">
				<input type="text" class="form-control" id="nome" name="nome"
					placeholder="Seu nome completo" required> <label for="nome">Nome
					Completo</label>
			</div>

			<div class="form-floating mb-3">
				<input type="email" class="form-control" id="email" name="email"
					placeholder="nome@exemplo.com" required> <label for="email">E-mail</label>
			</div>

			<div class="row g-2 mb-3">
				<div class="col-md-6">
					<div class="form-floating">
						<input type="tel" class="form-control" id="telefone"
							name="telefone" placeholder="(00) 00000-0000"> <label
							for="telefone">Telefone</label>
					</div>
				</div>
				<div class="col-md-6">
					<div class="form-floating">
						<input type="password" class="form-control" id="senha"
							name="senha" placeholder="Senha" required> <label
							for="senha">Senha</label>
					</div>
				</div>
			</div>

			<div class="form-floating mb-4">
				<input type="text" class="form-control" id="endereco"
					name="endereco" placeholder="Endereço"> <label
					for="endereco">Endereço</label>
			</div>

			<div class="d-grid gap-2">
				<button type="submit" class="btn btn-primary btn-register">Criar
					Conta</button>
			</div>
		</form>

		<div class="text-center mt-4">
			<p class="text-muted small">
				Já possui cadastro? <a
					href="${pageContext.request.contextPath}/app?command=redirect&url=auth/login.jsp"
					class="text-decoration-none fw-bold">Fazer Login</a>
			</p>
		</div>
	</div>

</body>
</html>