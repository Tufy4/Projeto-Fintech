<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.util.Locale"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="edu.ifsp.banco.modelo.Conta"%>

<%
Locale localeBR = new Locale("pt", "BR");
NumberFormat dinheiro = NumberFormat.getCurrencyInstance(localeBR);

Conta conta = (Conta) session.getAttribute("contaLogado");
BigDecimal saldo = (conta != null) ? conta.getSaldo() : BigDecimal.ZERO;

String produto = (String) request.getAttribute("produto");
if (produto == null || produto.isEmpty()) {
	produto = request.getParameter("produto");
}
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Confirmar Investimento - BitPay</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

<style>
body {
	background-color: #f4f6f9;
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	min-height: 100vh;
	display: flex;
	flex-direction: column;
}

.navbar-custom {
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.main-content {
	flex: 1;
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 40px 0;
}

.card-investimento {
	border: none;
	border-radius: 20px;
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
	overflow: hidden;
	background: white;
	max-width: 500px;
	width: 100%;
}

.card-header-invest {
	background: linear-gradient(135deg, #0d6efd 0%, #0043a8 100%);
	color: white;
	padding: 30px;
	text-align: center;
	border-bottom: none;
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

.produto-titulo {
	font-weight: 700;
	font-size: 1.5rem;
	margin-bottom: 5px;
	text-transform: uppercase;
	letter-spacing: 1px;
}

.saldo-disponivel {
	background-color: #e9ecef;
	color: #495057;
	padding: 10px 20px;
	border-radius: 50px;
	display: inline-block;
	font-weight: 600;
	margin-bottom: 25px;
	font-size: 0.9rem;
}

.saldo-valor {
	color: #198754; /* Verde */
}

.form-floating>label {
	color: #6c757d;
}

.form-control:focus {
	border-color: #0d6efd;
	box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.15);
}

.btn-confirmar {
	padding: 12px;
	font-weight: 600;
	font-size: 1.1rem;
	border-radius: 10px;
	transition: all 0.3s;
}

.btn-confirmar:hover {
	transform: translateY(-2px);
	box-shadow: 0 5px 15px rgba(13, 110, 253, 0.3);
}

.info-rendimento {
	font-size: 0.85rem;
	color: #6c757d;
	text-align: center;
	margin-top: 15px;
	padding-top: 15px;
	border-top: 1px solid #eee;
}
</style>
</head>
<body>

	<nav class="navbar navbar-dark bg-dark navbar-custom">
		<div class="container">
			<a class="navbar-brand fw-bold" href="#">BitPay <span
				class="badge bg-primary">IB</span></a> <a
				href="${pageContext.request.contextPath}/app/movimentacao/investir.jsp"
				class="btn btn-outline-light btn-sm"> <i
				class="bi bi-arrow-left"></i> Cancelar
			</a>
		</div>
	</nav>

	<div class="container main-content">
		<div class="card-investimento">

			<div class="card-header-invest">
				<div class="icon-box">
					<i class="bi bi-graph-up-arrow"></i>
				</div>
				<h2 class="produto-titulo"><%=produto%></h2>
				<p class="mb-0 opacity-75">Configurar Aplicação</p>
			</div>

			<div class="card-body p-4">

				<div class="text-center">
					<div class="saldo-disponivel">
						Saldo Disponível: <span class="saldo-valor"><%=dinheiro.format(saldo)%></span>
					</div>
				</div>

				<form
					action="${pageContext.request.contextPath}/app?command=finalizarInvestimento"
					method="post">

					<div class="mb-4">
						<div class="form-floating">
							<input type="number" class="form-control" id="valorInput"
								name="valor" placeholder="0,00" min="1" step="0.01" required>
							<label for="valorInput">Valor a Investir (R$)</label>
						</div>
						<div class="form-text ms-1">Mínimo de R$ 1,00</div>
					</div>

					<input type="hidden" name="produto" value="<%=produto%>">

					<input type="hidden" name="numeroConta"
						value="${sessionScope.contaLogado.numero_conta}">

					<div class="d-grid gap-2">
						<button type="submit" class="btn btn-primary btn-confirmar">
							Confirmar Investimento</button>
					</div>

					<div class="info-rendimento">
						<i class="bi bi-shield-check me-1"></i> Investimento garantido
						pelo FGC.<br> Ao confirmar, o valor será debitado da sua
						conta corrente.
					</div>
				</form>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>