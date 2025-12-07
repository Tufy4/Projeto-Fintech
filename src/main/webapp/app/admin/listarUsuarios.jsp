<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="edu.ifsp.banco.modelo.Usuario"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>BitPay Admin | Usuários Bloqueados</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
<style>
body {
	background-color: #f8f9fa;
}
</style>
</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
		<div class="container">
			<a class="navbar-brand fw-bold"
				href="${pageContext.request.contextPath}/app/admin/home.jsp">
				BitPay <span class="badge bg-primary"
				style="font-size: 0.5em; vertical-align: top;">ADMIN</span>
			</a>

			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse justify-content-end"
				id="navbarNav">
				<ul class="navbar-nav gap-2 align-items-center">
					<li class="nav-item me-2"><a class="btn btn-secondary btn-sm"
						href="${pageContext.request.contextPath}/app/admin/home.jsp">
							<i class="bi bi-arrow-left"></i> Voltar
					</a></li>
					<li class="nav-item"><span class="nav-link text-white">
							Olá, <strong>${sessionScope.usuarioLogado != null ? sessionScope.usuarioLogado.getNome() : 'Administrador'}</strong>
					</span></li>
					<li class="nav-item"><a class="btn btn-outline-danger btn-sm"
						href="${pageContext.request.contextPath}/app?command=logout">
							<i class="bi bi-box-arrow-right"></i>
					</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h3 class="text-secondary">
				<i class="bi bi-shield-lock"></i> Usuários Bloqueados
			</h3>
		</div>

		<%
		List<Usuario> lista = (List<Usuario>) request.getAttribute("ListaBloqueados");
		%>

		<div class="card shadow-sm border-0">
			<div class="card-body p-0">
				<div class="table-responsive">
					<table class="table table-hover table-striped mb-0 align-middle">
						<thead class="table-dark">
							<tr>
								<th class="ps-4">ID</th>
								<th>Nome</th>
								<th>Email</th>
								<th>Telefone</th>
								<th>Endereço</th>
								<th>Perfil</th>
								<th class="text-center">Ação</th>
							</tr>
						</thead>
						<tbody>
							<%
							if (lista != null && !lista.isEmpty()) {
								for (Usuario u : lista) {
							%>
							<tr>
								<td class="ps-4 fw-bold">#<%=u.getId()%></td>
								<td><%=u.getNome()%></td>
								<td><%=u.getEmail()%></td>
								<td><%=u.getTelefone()%></td>
								<td><%=u.getEndereco() != null ? u.getEndereco() : "-"%></td>
								<td><span class="badge bg-secondary"><%=u.getPerfil()%></span></td>
								<td class="text-center"><a class="btn btn-success btn-sm"
									href="${pageContext.request.contextPath}/app?command=liberarUsuario&id=<%= u.getId() %>"
									title="Aprovar Cadastro"> <i class="bi bi-check-lg"></i>
										Liberar
								</a></td>
							</tr>
							<%
							}
							} else {
							%>
							<tr>
								<td colspan="7" class="text-center py-5 text-muted"><i
									class="bi bi-check-circle display-4 d-block mb-3"></i>
									<h5>Tudo certo!</h5>
									<p>Não há usuários pendentes de liberação no momento.</p></td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

</body>
</html>