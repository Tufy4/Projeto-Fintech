<%@  page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Loja Patterns</title>
	<base href="<%= request.getContextPath() %>/">
</head>
<body>
	<h1>Loja Patterns</h1>
	
	<h2>Login</h2>
	<%
	if (request.getAttribute("error") != null) {
	%>
	<p>Nome de usuário e senha inválidos.</p>
	<%
	}
	%>
	
	<form action="Login" method="post">
		<label>Usuário: </label>
		<input type="email" name="user" required>
		<br>
		
		<label>Senha: </label>
		<input type="password" name="password" required>
		<br>
		
		<%
		if (request.getParameter("next") != null) {
		%>
		<input type="hidden" name="next" value="<%= request.getParameter("next") %>">
		<%
		}
		%>

		<button type="submit">Enviar</button>
	</form>
	
	
	<h2>Não tem conta? </h2>
	<a href="paginas/Cadastrar.jsp">Clique aqui para se cadastrar!!</a>	
</body>
</html>