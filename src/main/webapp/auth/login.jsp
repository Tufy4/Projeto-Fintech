<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BitPay | Inovação Financeira</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
.hero-section {
	background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)),
		url('https://images.unsplash.com/photo-1565514020176-dbf2277e4c73?auto=format&fit=crop&w=1920&q=80');
	background-size: cover;
	background-position: center;
	color: white;
	padding: 100px 0;
}

.feature-icon {
	font-size: 2.5rem;
	color: #0d6efd;
	margin-bottom: 1rem;
}

body{
background-color: rgb(210,210,210)}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand fw-bold" href="#">BitPay</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse justify-content-end"
				id="navbarNav">
				<ul class="navbar-nav gap-2">
					<li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/index.jsp">Home</a>
					</li>
					
				</ul>
			</div>
		</div>
	</nav>
	<%
	if (request.getAttribute("error") != null) {
	%>
	<p>Nome de usuário e senha inválidos.</p>
	<%
	}
	%>

	<div class="container d-flex justify-content-center align-items-center" style="min-height: 70vh;">
    <div class="col-md-4">
        <div class="card shadow-lg border-0 rounded-4">
            <div class="card-body p-4">

                <h3 class="text-center mb-4 fw-bold">Acesso à Conta</h3>

                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger text-center p-2">
                        Nome de usuário ou senha inválidos.
                    </div>
                <% } %>

                <form action="${pageContext.request.contextPath}/app?command=login" method="post">

                    <div class="mb-3">
                        <label class="form-label">Email:</label>
                        <input type="email" name="user" class="form-control form-control-lg" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Senha:</label>
                        <input type="password" name="password" class="form-control form-control-lg" required>
                    </div>

                    <% if (request.getParameter("next") != null) { %>
                        <input type="hidden" name="next" value="<%=request.getParameter("next")%>">
                    <% } %>

                    <button type="submit" class="btn btn-primary w-100 btn-lg mt-2">
                        Entrar
                    </button>

                </form>

                <div class="text-center mt-3">
                    <small>Não tem conta? 
                        <a href="${pageContext.request.contextPath}/app?command=redirect&url=auth/cadastro.jsp" class="text-primary fw-bold">
                            Criar Conta
                        </a>
                    </small>
                </div>

            </div>
        </div>
    </div>
</div>
	


	
</body>
</html>