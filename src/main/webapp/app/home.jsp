<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="edu.ifsp.banco.modelo.Usuario"%>
<%@ page import="edu.ifsp.banco.modelo.Conta"%>
<%
Usuario user = (Usuario) session.getAttribute("usuarioLogado");
Conta conta = (Conta) session.getAttribute("contaLogado");

String nomeUsuario = (user != null) ? user.getNome() : "Cliente";
String saldoFormatado = (conta != null) ? String.format("%.2f", conta.getSaldo()) : "0,00";
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BitPay | Minha Conta</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

<style>
body {
	background-color: #f8f9fa;
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}
a{
text-decoration: none;
}
.hero-dashboard {
	background: linear-gradient(135deg, rgba(0, 0, 0, 0.8) 0%,
		rgba(13, 110, 253, 0.6) 100%),
		url('https://images.unsplash.com/photo-1565514020176-dbf2277e4c73?auto=format&fit=crop&w=1920&q=80');
	background-size: cover;
	background-position: center;
	color: white;
	padding: 80px 0 120px 0;
	margin-bottom: -60px;
}

.op-card {
	background: white;
	border: none;
	border-radius: 15px;
	padding: 2rem;
	text-align: center;
	height: 100%;
	box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05);
	transition: all 0.3s ease;
	text-decoration: none;
	color: inherit;
	display: block;
}

.op-card:not(.disabled):hover {
	transform: translateY(-10px);
	box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
	color: #0d6efd;
}

.op-card.disabled {
	opacity: 0.6;
	cursor: not-allowed;
	background-color: #e9ecef;
}

.op-icon {
	font-size: 2.5rem;
	margin-bottom: 1rem;
	color: #0d6efd;
}

.op-card.disabled .op-icon {
	color: #6c757d;
}
</style>
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark py-3">
		<div class="container">
			<a class="navbar-brand fw-bold" href="#">BitPay <span
				class="badge bg-primary fs-6 ms-1">IB</span></a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse justify-content-end"
				id="navbarNav">
				<ul class="navbar-nav gap-3 align-items-center">
					<li class="nav-item"><span class="nav-link text-white">Olá,
							<strong><%=nomeUsuario%></strong>
					</span></li>
					<li class="nav-item"><a
						class="btn btn-outline-danger btn-sm rounded-pill px-3"
						href="${pageContext.request.contextPath}/app?command=logout">Sair</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<header class="hero-dashboard text-center">
		<div class="container">
			<p class="lead mb-1 text-white-50 text-uppercase small ls-1">Saldo
				Disponível</p>
			<h1 class="display-1 fw-bold mb-4">
				R$
				<%=saldoFormatado%></h1>

			<div class="d-flex justify-content-center">
				<a
					href="${pageContext.request.contextPath}/app?command=redirect&url=app/movimentacao/deposito.jsp"
					class="btn btn-primary btn-lg rounded-pill px-5 shadow-lg"> <i
					class="bi bi-plus-lg me-2"></i>Fazer Depósito
				</a>
			</div>
		</div>
	</header>

	<section class="container py-5">
		<div class="row g-4 row-cols-1 row-cols-md-2 row-cols-lg-4">

			<div class="col">
				<a href="${pageContext.request.contextPath}/app?command=extrato"
					class="op-card">
					<div class="op-icon">
						<i class="bi bi-receipt"></i>
					</div>
					<h3 class="h4 fw-bold">Extrato</h3>
					<p class="small text-muted mb-0">Consulte histórico de
						movimentações.</p>
				</a>
			</div>

			<div class="col">
				<a
					href="${pageContext.request.contextPath}/app?command=redirect&url=app/movimentacao/deposito.jsp"
					class="op-card">
					<div class="op-icon">
						<i class="bi bi-wallet2"></i>
					</div>
					<h3 class="h4 fw-bold">Depositar</h3>
					<p class="small text-muted mb-0">Adicione fundos à sua conta
						instantaneamente.</p>
				</a>
			</div>

			<div class="col">
				<a
					href="${pageContext.request.contextPath}/app/movimentacao/investir.jsp"
					class="op-card">
					<div class="op-icon">
						<i class="bi bi-graph-up-arrow"></i>
					</div>
					<h3 class="h4 fw-bold">Investimentos</h3>
					<p class="small text-muted mb-0">Renda fixa e variável com
						liquidez diária.</p>
				</a>
			</div>

			<div class="col">
				<a
					href="${pageContext.request.contextPath}/app?command=redirect&url=app/movimentacao/transferencia.jsp"
					class="op-card">
					<div class="op-icon">
						<i class="bi bi-arrow-left-right"></i>
					</div>
					<h3 class="h4 fw-bold">Transferir</h3>
					<p class="small text-muted mb-0">Envie dinheiro para outras
						contas.</p>
				</a>
			</div>

			<div class="col">
				<a
					href="${pageContext.request.contextPath}/app?command=meusEmprestimos"
					class="op-card">
					<div class="op-icon">
						<i class="bi bi-house-door"></i>
					</div>
					<h3 class="h4 fw-bold">Empréstimos</h3>
					<p class="small text-muted mb-0">Simulação de crédito SAC.</p>
				</a>
			</div>


	
	<div class="col">
    <a
        href="${pageContext.request.contextPath}/app?command=mostrarDadosUsuario&id=<%=conta.getUsuarioId() %>"
        class="op-card">
        <div class="op-icon">
            <i class="bi bi-pencil-square"></i>
        </div>
        <h3 class="h4 fw-bold">Editar Usuário</h3>
        <p class="small text-muted mb-0">Altere os dados do usuário.</p>
    </a>
</div>










		</div>

		<div class="mt-5 text-center text-muted small">
			<p>
				Conta: <strong><%=conta.getNumero_conta()%></strong> | Agência: <strong><%=conta.getAgencia()%></strong>
			</p>
		</div>

	</section>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>