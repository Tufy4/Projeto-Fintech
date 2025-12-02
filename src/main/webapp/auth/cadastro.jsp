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
	<div class="container d-flex justify-content-center align-items-center" 
     style="min-height: 90vh;">
    
    <div class="card shadow p-4" style="width: 100%; max-width: 420px;">

        <h3 class="text-center mb-4">Criar Conta</h3>

        <form action="${pageContext.request.contextPath}/app?command=cadastrarUsuario" method="post">

            <div class="mb-3">
                <label class="form-label">Nome:</label>
                <input type="text" name="name" class="form-control form-control-lg" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Email:</label>
                <input type="email" name="email" class="form-control form-control-lg" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Senha:</label>
                <input type="password" name="password" class="form-control form-control-lg" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Telefone:</label>
                <input type="text" name="telefone" class="form-control form-control-lg" required>
            </div>

            <div class="mb-4">
                <label class="form-label">Endereço:</label>
                <input type="text" name="endereco" class="form-control form-control-lg" required>
            </div>
            
            
             <div class="mb-4">
                <label class="form-label">Tipo da conta: </label>
                <input type="text" placeholder="Corrente/Poupança" name="typeAccount" class="form-control form-control-lg" required>
            </div>

            <button type="submit" class="btn btn-primary w-100 btn-lg">
                Criar Conta
            </button>
        </form>

    </div>
</div>

</body>
</html>