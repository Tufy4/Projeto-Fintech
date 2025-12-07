<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Erro ao processar movimentação</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">
<style>
.backg
    	
        .container {
	height: 100vh;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
}

.error-image {
	font-size: 80px;
	color: #dc3545;
	margin-bottom: 20px;
}

.error-message {
	text-align: center;
}
</style>
</head>
<body>
	<div class="container">
		<i class="bi bi-coin error-image"></i>

		<div class="error-message">
			<h2>Erro</h2>
			<p style="color: red;">${erro}</p>
			<a
				href="${pageContext.request.contextPath}/app/movimentacao/deposito.jsp"
				class="btn btn-danger">Tentar novamente</a>
		</div>
	</div>

</body>
</html>
