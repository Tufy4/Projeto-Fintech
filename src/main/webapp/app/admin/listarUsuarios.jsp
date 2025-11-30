<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="edu.ifsp.banco.modelo.Usuario" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Usuários Bloqueados</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    table {
        border-collapse: collapse;
        width: 80%;
        margin: 20px auto;
    }
    th, td {
        border: 1px solid #333;
        padding: 8px 12px;
        text-align: left;
    }
    th {
        background-color: #f2f2f2;
    }
    h2 {
        text-align: center;
    }
    p {
        text-align: center;
    }
      .hero-section {
            background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)),
                url('https://images.unsplash.com/photo-1565514020176-dbf2277e4c73?auto=format&fit=crop&w=1920&q=80');
            background-size: cover;
            background-position: center;
            color: white;
            padding: 80px 0;
        }

        .feature-icon {
            font-size: 2.5rem;
            color: #0d6efd;
            margin-bottom: 1rem;
        }
        
        .disabled-card {
            opacity: 0.6;
            cursor: not-allowed;
            background-color: #f8f9fa;
        }
</style>
</head>
<body>
   <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
        
            <a class="navbar-brand fw-bold" href="#">BitPay <span class="badge bg-primary" style="font-size: 0.5em; vertical-align: top;">IB</span></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            
           
            	
                        <a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/app/admin/home.jsp">Inicio</a>
            
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            		

                <ul class="navbar-nav gap-2 align-items-center">
                    <li class="nav-item">
                        <span class="nav-link text-white">Olá, <strong>${sessionScope.usuarioLogado != null ? sessionScope.usuarioLogado.getNome() : 'Cliente'}</strong></span>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-outline-danger btn-sm" href="${pageContext.request.contextPath}/app?command=logout">Sair</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
<h2 class="mt-4">Usuários Bloqueados</h2>

<%
    List<Usuario> lista = (List<Usuario>) request.getAttribute("ListaBloqueados");
%>

<div class="container my-4 d-flex justify-content-center">
    <div class="table-responsive shadow rounded" style="max-width: 90%;">
        <table class="table table-hover align-middle mb-0">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>Telefone</th>
                    <th>Endereço</th>
                    <th>Perfil</th>
                    <th>Status</th>
                    <th>Ação</th>
                </tr>
            </thead>

            <tbody>
            <%
                if (lista != null && !lista.isEmpty()) {
                    for (Usuario u : lista) {
            %>
                <tr>
                    <td><%= u.getId() %></td>
                    <td><%= u.getNome() %></td>
                    <td><%= u.getEmail() %></td>
                    <td><%= u.getTelefone() %></td>
                    <td><%= u.getEndereco() %></td>
                    <td><%= u.getPerfil() %></td>
                    <td><span class="badge bg-danger"><%= u.getStatus() %></span></td>
                    <td>
                        <a class="btn btn-success btn-sm"
                           href="${pageContext.request.contextPath}/app?command=liberarUsuario&id=<%= u.getId() %>">
                            ✔ Liberar
                        </a>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="8" class="text-center py-4">
                        <em>Nenhum usuário bloqueado encontrado.</em>
                    </td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
