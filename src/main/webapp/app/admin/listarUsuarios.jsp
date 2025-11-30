<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="edu.ifsp.banco.modelo.Usuario" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Usuários Bloqueados</title>
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
</style>
</head>
<body>

<h2>Usuários Bloqueados</h2>

<%
    // Pegando a lista do request
    List<Usuario> lista = (List<Usuario>) request.getAttribute("ListaBloqueados");
    // Se preferir usar sessão, troque request.getAttribute por session.getAttribute
    // List<Usuario> lista = (List<Usuario>) session.getAttribute("ListaBloqueados");

    if (lista != null && !lista.isEmpty()) {
%>
    <table>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Email</th>
            <th>Telefone</th>
            <th>Endereço</th>
            <th>Perfil</th>
            <th>Status</th>
        </tr>
<%
        for (Usuario u : lista) {
%>
        <tr>
            <td><%= u.getId() %></td>
            <td><%= u.getNome() %></td>
            <td><%= u.getEmail() %></td>
            <td><%= u.getTelefone() %></td>
            <td><%= u.getEndereco() %></td>
            <td><%= u.getPerfil() %></td>
            <td><%= u.getStatus() %></td>
        </tr>
<%
        }
%>
    </table>
<%
    } else {
%>
    <p>Não há usuários bloqueados.</p>
<%
    }
%>

</body>
</html>
