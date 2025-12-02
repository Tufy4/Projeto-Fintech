<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Investir</title>
</head>
<body>

<h2>Detalhes do investimento selecionado</h2>

<p>Produto escolhido: <strong>${produto}</strong></p>

<form action="${pageContext.request.contextPath}/app?command=finalizarInvestimento" method="post">

    <label>Valor a investir:</label>
    <input type="number" name="valor" min="1" required>

    <input type="hidden" name="produto" value="${produto}">
    <input type="hidden" name="numeroConta" value="${usuario.conta.numero}">

    <button type="submit">Confirmar investimento</button>
</form>


</body>
</html>
