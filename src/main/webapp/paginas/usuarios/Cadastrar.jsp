<%@  page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Banco</title>
	<base href="<%= request.getContextPath() %>/">
</head>
<body>
	<form action="control?command=CadastrarUsuario" method="post">
		<label>Nome: </label>
		<input type="text" name="name" required>
		<br>
		
		
		<label>Email: </label>
		<input type="email" name="email" required>
		<br>
		
		<label>Senha: </label>
		<input type="password" name="password" required>
		<br>
		
		
		<label>Telefone: </label>
		<input type="text" name="telefone" required>
		<br>
		
		<label>Endereço: </label>
		<input type="text" name="endereco" required>
		<br>
		
		

		<button type="submit">Enviar</button>
	</form>
</body>
</html>