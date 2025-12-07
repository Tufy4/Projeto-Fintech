<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Investimentos</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

<style>
body {
	background: #f4f4f4;
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* Navbar simples para manter consistência */
.navbar-custom {
	background-color: #343a40;
	padding: 10px 0;
	margin-bottom: 40px;
}

.container-custom {
	width: 880px;
	margin: 0 auto;
}

h2 {
	text-align: center;
	margin-bottom: 10px;
	color: #333;
	font-weight: bold;
}

.subtitle {
	text-align: center;
	color: #666;
	margin-bottom: 30px;
}

/* Botões de Ação no Topo */
.action-bar {
	text-align: center;
	margin-bottom: 40px;
	padding: 20px;
	background: white;
	border-radius: 10px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
	display: flex;
	justify-content: center;
	align-items: center;
	gap: 15px;
}

.btn-meus-investimentos {
	background-color: #198754; /* Verde Sucesso */
	color: white;
	padding: 12px 25px;
	border-radius: 50px;
	text-decoration: none;
	font-weight: bold;
	transition: all 0.3s;
	display: inline-flex;
	align-items: center;
	gap: 10px;
	border: none;
}

.btn-meus-investimentos:hover {
	background-color: #146c43;
	transform: translateY(-2px);
	color: white;
	box-shadow: 0 4px 8px rgba(25, 135, 84, 0.3);
}

/* Cards existentes (mantendo seu estilo original com melhorias) */
.cards {
	display: flex;
	gap: 20px;
}

.card-invest {
	background: white;
	border-radius: 15px;
	padding: 25px;
	width: 50%;
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
	transition: 0.3s;
	cursor: pointer;
	text-decoration: none;
	color: black;
	border: 1px solid transparent;
	position: relative;
	overflow: hidden;
}

.card-invest:hover {
	transform: translateY(-5px);
	box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
	border-color: #0066cc;
}

.card-invest::before {
	content: '';
	position: absolute;
	top: 0;
	left: 0;
	width: 5px;
	height: 100%;
	background-color: #0066cc;
}

.titulo {
	font-size: 22px;
	font-weight: bold;
	margin-bottom: 8px;
	color: #2c3e50;
}

.desc {
	margin-bottom: 15px;
	color: #666;
	line-height: 1.4;
	min-height: 40px;
}

.rent {
	font-weight: bold;
	color: #0066cc;
	font-size: 1.2rem;
	margin-bottom: 15px;
	display: block;
}

.btn-investir {
	display: block;
	width: 100%;
	padding: 12px;
	background: #0066cc;
	color: white !important;
	border-radius: 8px;
	text-align: center;
	font-weight: 600;
	transition: background 0.2s;
}

.btn-investir:hover {
	background: #004c99;
}
</style>
</head>
<body>

	<nav class="navbar navbar-dark bg-dark mb-4">
		<div
			class="container container-custom d-flex justify-content-between align-items-center">
			<a class="navbar-brand fw-bold" href="#">BitPay <span
				class="badge bg-primary">IB</span></a> <a
				href="${pageContext.request.contextPath}/app/home.jsp"
				class="btn btn-outline-light btn-sm">Voltar para Home</a>
		</div>
	</nav>

	<div class="container-custom">

		<h2>Central de Investimentos</h2>
		<p class="subtitle">Faça seu dinheiro render com segurança e
			liquidez.</p>

		<div class="action-bar">
			<div>
				<h5 class="m-0 fw-bold text-muted" style="font-size: 1rem;">Já
					possui aplicações?</h5>
				<small class="text-muted">Acompanhe seus rendimentos agora.</small>
			</div>
			<a
				href="${pageContext.request.contextPath}/app?command=meusInvestimentos"
				class="btn-meus-investimentos"> <i class="bi bi-wallet2"></i>
				Ver Meus Investimentos
			</a>
		</div>

		<div class="cards">

			<a class="card-invest"
				href="${pageContext.request.contextPath}/app?command=escolherInvestimento&produto=CDB_DI">
				<div class="titulo">CDB Liquidez Diária</div>
				<div class="desc">Ideal para reserva de emergência. Saque a
					qualquer momento.</div>
				<div class="rent">
					<i class="bi bi-graph-up-arrow me-1"></i> 100% do CDI
				</div>
				<div class="btn-investir">Investir Agora</div>
			</a> <a class="card-invest"
				href="${pageContext.request.contextPath}/app?command=escolherInvestimento&produto=CDB_1ANO">
				<div class="titulo">CDB Pré-fixado 1 Ano</div>
				<div class="desc">Rende mais que a poupança, com garantia do
					FGC.</div>
				<div class="rent">
					<i class="bi bi-graph-up-arrow me-1"></i> 115% do CDI
				</div>
				<div class="btn-investir">Investir Agora</div>
			</a>

		</div>

	</div>

</body>
</html>