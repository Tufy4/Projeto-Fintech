<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nova Senha | BitPay</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
<style>
body {
	background: linear-gradient(135deg, rgba(0, 0, 0, 0.8) 0%,
		rgba(13, 110, 253, 0.6) 100%),
		url('https://images.unsplash.com/photo-1550751827-4bd374c3f58b?auto=format&fit=crop&w=1920&q=80');
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
	max-width: 420px;
}

.brand-logo {
	text-align: center;
	margin-bottom: 25px;
	color: #0d6efd;
	font-size: 2rem;
	font-weight: bold;
}

.icon-header {
	font-size: 3rem;
	color: #0d6efd;
	text-align: center;
	margin-bottom: 20px;
}

.btn-confirm {
	padding: 12px;
	font-size: 1.1rem;
	font-weight: 600;
	border-radius: 10px;
}
</style>
</head>
<body>

	<div class="auth-card">
		<div class="brand-logo">
			<i class="bi bi-wallet2"></i> BitPay
		</div>

		<div class="icon-header">
			<i class="bi bi-shield-lock"></i>
		</div>

		<h4 class="text-center mb-3 text-secondary">Redefinir Senha</h4>
		<p class="text-center text-muted small mb-4">Crie uma nova senha
			forte para sua conta.</p>

		<form
			action="${pageContext.request.contextPath}/app?command=redefinirSenha"
			method="post" onsubmit="return validarSenhas()">

			<input type="hidden" name="token" value="${tokenValido}"> <input
				type="hidden" name="idUsuario" value="${idUsuario}">

			<div class="form-floating mb-3">
				<input type="password" class="form-control" id="senha" name="senha"
					placeholder="Nova Senha" required minlength="6"> <label
					for="senha">Nova Senha</label>
			</div>

			<div class="form-floating mb-4">
				<input type="password" class="form-control" id="confirmaSenha"
					placeholder="Confirme a Senha" required> <label
					for="confirmaSenha">Confirme a Nova Senha</label>
			</div>

			<div id="msgErro" class="text-danger small mb-3 text-center"
				style="display: none;">
				<i class="bi bi-exclamation-circle"></i> As senhas não conferem.
			</div>

			<div class="d-grid gap-2">
				<button type="submit" class="btn btn-primary btn-confirm">
					Alterar Senha</button>
			</div>
		</form>

		<div class="text-center mt-4">
			<a href="${pageContext.request.contextPath}/auth/login.jsp"
				class="text-decoration-none text-muted small"> <i
				class="bi bi-arrow-left"></i> Voltar para Login
			</a>
		</div>
	</div>

	<script>
		function validarSenhas() {
			var senha = document.getElementById("senha").value;
			var confirma = document.getElementById("confirmaSenha").value;
			var msg = document.getElementById("msgErro");

			if (senha !== confirma) {
				msg.style.display = "block";
				return false;
			}
			msg.style.display = "none";
			return true;
		}
	</script>

</body>
</html>