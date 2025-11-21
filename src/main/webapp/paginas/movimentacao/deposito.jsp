<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/control" method="post">
    <input type="hidden" name="command" value="Depositar">

    <label>ID da Conta:</label>
    <input type="number" name="idConta" required>

    <label>Valor:</label>
    <input type="number" step="0.01" name="valor" required>

    <button type="submit">Depositar</button>
</form>

</body>
</html>