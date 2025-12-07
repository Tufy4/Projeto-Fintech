<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.math.BigDecimal"%>

<%
Locale localeBR = new Locale("pt", "BR");
NumberFormat dinheiro = NumberFormat.getCurrencyInstance(localeBR);
BigDecimal saldo = (BigDecimal) session.getAttribute("saldoConta");
if (saldo == null)
	saldo = BigDecimal.ZERO;
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Transferência | BitPay</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

<style>
body {
	background-color: #f4f6f9;
	font-family: 'Segoe UI', sans-serif;
	min-height: 100vh;
	display: flex;
	flex-direction: column;
}

.main-content {
	flex: 1;
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 40px 0;
}

.card-transfer {
	border: none;
	border-radius: 20px;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
	overflow: hidden;
	background: white;
	max-width: 500px;
	width: 100%;
}

.card-header-custom {
	background: linear-gradient(135deg, #6610f2 0%, #520dc2 100%);
	color: white;
	padding: 30px;
	text-align: center;
}

.icon-box {
	width: 70px;
	height: 70px;
	background-color: rgba(255, 255, 255, 0.2);
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	margin: 0 auto 15px auto;
	font-size: 32px;
	backdrop-filter: blur(5px);
}

.saldo-tag {
	background-color: #e9ecef;
	color: #495057;
	padding: 8px 16px;
	border-radius: 50px;
	font-weight: 600;
	font-size: 0.9rem;
	display: inline-block;
	margin-bottom: 20px;
}

.btn-transfer {
	background-color: #6610f2;
	border: none;
	padding: 12px;
	font-weight: 600;
	font-size: 1.1rem;
}

.btn-transfer:hover {
	background-color: #520dc2;
}
</style>
</head>
<body>

	<nav class="navbar navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand fw-bold" href="#">BitPay <span
				class="badge bg-primary">IB</span></a> <a
				href="${pageContext.request.contextPath}/app/home.jsp"
				class="btn btn-outline-light btn-sm"> <i
				class="bi bi-arrow-left"></i> Voltar
			</a>
		</div>
	</nav>

	<div class="container main-content">
		<div class="card-transfer">

			<div class="card-header-custom">
				<div class="icon-box">
					<i class="bi bi-arrow-left-right"></i>
				</div>
				<h3 class="fw-bold mb-0">Nova Transferência</h3>
				<p class="mb-0 opacity-75">Envie dinheiro rapidamente</p>
			</div>

			<div class="card-body p-4">

				<div class="text-center">
					<div class="saldo-tag">
						Disponível: <span class="text-success"><%=dinheiro.format(saldo)%></span>
					</div>
				</div>

				<form
					action="${pageContext.request.contextPath}/app?command=transferir"
					method="post">

					<div class="form-floating mb-3">
						<input type="number" class="form-control" id="contaDestino"
							name="contaDestino" required placeholder="00000"> <label
							for="contaDestino">Número da Conta Destino</label>
					</div>

					<div class="form-floating mb-4">
						<input type="number" class="form-control" id="valor" name="valor"
							step="0.01" min="0.01" required placeholder="0,00"> <label
							for="valor">Valor (R$)</label>
					</div>

					<div class="d-grid">
						<button type="submit" class="btn btn-primary btn-transfer">
							Confirmar Transferência</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>