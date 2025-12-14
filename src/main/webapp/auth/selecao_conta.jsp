<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="edu.ifsp.banco.modelo.Conta"%>
<%@ page import="edu.ifsp.banco.modelo.Usuario"%>
<%@ page import="edu.ifsp.banco.modelo.enums.TiposConta"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Selecionar Conta | BitPay</title>
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
	max-width: 450px;
	position: relative;
}

.brand-logo {
	text-align: center;
	margin-bottom: 20px;
	color: #0d6efd;
	font-size: 2rem;
	font-weight: bold;
}

.logout-link {
	position: absolute;
	top: 20px;
	left: 20px;
	color: white;
	text-decoration: none;
	font-weight: 500;
	opacity: 0.8;
	transition: 0.3s;
	z-index: 10;
}

.logout-link:hover {
	opacity: 1;
	color: #ffcccc; 
}

.conta-card {
	border: 1px solid #e9ecef;
	border-radius: 12px;
	padding: 15px;
	margin-bottom: 15px;
	cursor: pointer;
	transition: all 0.2s ease;
	text-decoration: none;
	color: inherit;
	display: flex;
	align-items: center;
	background: #f8f9fa;
}

.conta-card:hover {
	transform: translateY(-3px);
	box-shadow: 0 5px 15px rgba(13, 110, 253, 0.15);
	border-color: #0d6efd;
	background: white;
}

.conta-icon {
	width: 50px;
	height: 50px;
	background: #e7f1ff;
	color: #0d6efd;
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 1.5rem;
	margin-right: 15px;
}

.conta-info h5 {
	margin: 0;
	font-size: 1.1rem;
	font-weight: 600;
	color: #333;
}

.conta-info p {
	margin: 0;
	color: #6c757d;
	font-size: 0.9rem;
}

.badge-tipo {
	font-size: 0.7rem;
	padding: 4px 8px;
	border-radius: 6px;
	margin-left: auto;
}
</style>
</head>
<body>

	<a href="${pageContext.request.contextPath}/app?command=logout"
		class="logout-link"> <i class="bi bi-box-arrow-left"></i> Sair
	</a>

	<div class="auth-card">
		<div class="brand-logo">
			<i class="bi bi-wallet2"></i> BitPay
		</div>

		<%
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		if (usuario != null) {
		%>
		<div class="text-center mb-4">
			<h5 class="text-secondary">
				Olá,
				<%=usuario.getNome().split(" ")[0]%>!
			</h5>
			<p class="text-muted small">Selecione uma conta para continuar</p>
		</div>
		<%
		}
		%>

		<div class="contas-list">
			<%
			List<Conta> contas = (List<Conta>) request.getAttribute("listaContas");

			if (contas != null && !contas.isEmpty()) {
				for (Conta c : contas) {
					// Lógica visual simples
					String iconClass = "bi-person";
					String tipoLabel = "Cliente";
					String bgBadge = "bg-secondary";

					if (c.getTipo() == TiposConta.GERENTE) {
				iconClass = "bi-briefcase-fill";
				tipoLabel = "Gerente";
				bgBadge = "bg-primary";
					}
			%>

			<a
				href="${pageContext.request.contextPath}/app?command=selecionarConta&idConta=<%= c.getId() %>"
				class="conta-card">

				<div class="conta-icon">
					<i class="bi <%=iconClass%>"></i>
				</div>

				<div class="conta-info">
					<h5>
						Conta
						<%=c.getNumero_conta()%>-<%=c.getAgencia()%></h5>
					<p>
						Saldo: R$
						<%=String.format("%.2f", c.getSaldo())%></p>
				</div> <span class="badge <%=bgBadge%> badge-tipo text-white"><%=tipoLabel%></span>
			</a>

			<%
			}
			} else {
			%>
			<div class="alert alert-warning text-center">
				<i class="bi bi-exclamation-circle"></i> Nenhuma conta encontrada.
			</div>
			<%
			}
			%>
		</div>

		<div class="text-center mt-4">
			<p class="text-muted small">
				Deseja abrir uma nova conta? <br> <a href="#"
					class="text-decoration-none fw-bold">Contate o suporte</a>
			</p>
		</div>
	</div>

</body>
</html>