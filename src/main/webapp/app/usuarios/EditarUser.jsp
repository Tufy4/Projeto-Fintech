<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="edu.ifsp.banco.modelo.Usuario"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>


<%
Usuario user = (Usuario) session.getAttribute("userEditar");
%>
<body>
	<form action="">
		<input type="text" value="<%=user.getEmail()%>">; <label>EMail</label>

	</form>
</body>
</html>