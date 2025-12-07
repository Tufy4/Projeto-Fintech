<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="edu.ifsp.banco.modelo.Emprestimo" %>
<%@ page import="edu.ifsp.banco.modelo.Usuario" %>

<%
    List<Emprestimo> pendentes = (List<Emprestimo>) request.getAttribute("listaPendentes");
    String msg = (String) request.getAttribute("msg");
    String erro = (String) request.getAttribute("erro");
    Usuario user = (Usuario) session.getAttribute("usuarioLogado");

    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Gerência | Aprovação</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body { background-color: #e9ecef; font-family: 'Segoe UI', sans-serif; }
        .card-request { transition: 0.3s; border: none; border-left: 5px solid #ffc107; }
        .card-request:hover { transform: translateY(-3px); box-shadow: 0 5px 15px rgba(0,0,0,0.1); }
    </style>
</head>
<body>

    <nav class="navbar navbar-dark bg-danger mb-4 py-3 shadow-sm">
        <div class="container">
            <span class="navbar-brand mb-0 h1 fw-bold">Área do Gerente</span>
            <div class="d-flex align-items-center gap-3">
                <span class="text-white small">Olá, <%= user != null ? user.getNome() : "" %></span>
                <a href="<%= request.getContextPath() %>/app?command=logout" class="btn btn-sm btn-light rounded-pill px-3">Sair</a>
            </div>
        </div>
    </nav>

    <div class="container pb-5">
        
        <% if(msg != null) { %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <%= msg %> <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>
        
        <% if(erro != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <%= erro %> <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <h3 class="text-secondary mb-4 border-bottom pb-2">Solicitações Pendentes</h3>

        <div class="row">
            <% 
            if (pendentes != null && !pendentes.isEmpty()) {
                for (Emprestimo emp : pendentes) {
            %>
            <div class="col-12 mb-3">
                <div class="card card-request shadow-sm bg-white p-3">
                    <div class="row align-items-center">
                        <div class="col-md-5">
                            <h5 class="mb-0 fw-bold">Solicitação #<%= emp.getId() %></h5>
                            <small class="text-muted">Conta: <%= emp.getConta_id() %></small> <br>
                            <small class="text-muted"><%= (emp.getData_solicitacao() != null) ? sdf.format(emp.getData_solicitacao()) : "-" %></small>
                        </div>

                        <div class="col-md-3 text-center border-start border-end">
                            <small class="text-uppercase text-muted">Valor</small>
                            <div class="fs-4 fw-bold text-primary"><%= nf.format(emp.getValor_emprestimo()) %></div>
                        </div>

                        <div class="col-md-2 text-center">
                            <span class="fw-bold"><%= emp.getParcelas() %>x</span> <br>
                            <small class="text-success"><%= emp.getTaxa_juros_mensal() %>% a.m.</small>
                        </div>

                        <div class="col-md-2 text-end d-grid gap-2">
                            <form action="<%= request.getContextPath() %>/app" method="post">
                                <input type="hidden" name="command" value="aprovarEmprestimo">
                                <input type="hidden" name="idEmprestimo" value="<%= emp.getId() %>">
                                <button type="submit" class="btn btn-success btn-sm w-100 rounded-pill">Aprovar</button>
                            </form>
                            </div>
                    </div>
                </div>
            </div>
            <% 
                } 
            } else { 
            %>
                <div class="col-12"><div class="alert alert-light text-center border">Nenhuma pendência.</div></div>
            <% } %>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>