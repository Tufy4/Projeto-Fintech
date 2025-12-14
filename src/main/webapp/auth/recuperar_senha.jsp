<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Recuperar Senha | BitPay</title>
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
}

.auth-card {
	background: white;
	border-radius: 20px;
	padding: 40px;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.3);
	width: 100%;
	max-width: 400px;
}

.brand-logo {
	text-align: center;
	margin-bottom: 20px;
	color: #0d6efd;
	font-size: 2rem;
	font-weight: bold;
}
</style>
</head>
<body>

	<div class="auth-card">
		<div class="brand-logo">
			<i class="bi bi-wallet2"></i> BitPay
		</div>

		<h4 class="text-center mb-3 text-secondary">Recuperação</h4>
		<p class="text-center text-muted small mb-4">Informe seu e-mail
			para receber as instruções.</p>

		<form
			action="${pageContext.request.contextPath}/app?command=solicitarRecuperacao"
			method="post">
			<div class="form-floating mb-4">
				<input type="email" class="form-control" id="email" name="email"
					placeholder="nome@exemplo.com" required> <label for="email">E-mail
					cadastrado</label>
			</div>

			<div class="d-grid gap-2">
				<button type="submit" class="btn btn-primary py-2 fw-bold">Enviar
					Link</button>
			</div>
		</form>

		<div class="text-center mt-4">
			<a href="${pageContext.request.contextPath}/auth/login.jsp"
				class="text-decoration-none small"> <i class="bi bi-arrow-left"></i>
				Voltar para Login
			</a>
		</div>
	</div>

</body>
</html>