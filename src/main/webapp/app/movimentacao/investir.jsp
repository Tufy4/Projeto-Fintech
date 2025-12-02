<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Investimentos</title>

    <style>
        body {
            background: #f4f4f4;
            font-family: Arial, sans-serif;
        }
        .container {
            width: 880px;
            margin: 40px auto;
        }
        h2 {
            text-align: center;
            margin-bottom: 25px;
        }
        .cards {
            display: flex;
            gap: 20px;
        }
        .card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            width: 50%;
            box-shadow: 0 0 10px #ccc;
            transition: 0.2s;
            cursor: pointer;
            text-decoration: none;
            color: black;
        }
        .card:hover {
            transform: scale(1.02);
            box-shadow: 0 0 15px #bbb;
        }
        .titulo {
            font-size: 22px;
            font-weight: bold;
            margin-bottom: 8px;
        }
        .desc {
            margin-bottom: 12px;
            color: #444;
        }
        .rent {
            font-weight: bold;
            color: #0066cc;
        }
        .btn {
            margin-top: 10px;
            display: inline-block;
            padding: 10px;
            background: #0066cc;
            color: white !important;
            border-radius: 5px;
            text-align: center;
        }
        .btn:hover {
            background: #004c99;
        }
    </style>
</head>
<body>

<div class="container">

    <h2>Escolha seu Investimento</h2>

    <div class="cards">

        <!-- CDB 1 -->
        <a class="card"
           href="${pageContext.request.contextPath}/app?command=escolherInvestimento&produto=CDB_DI">
            <div class="titulo">CDB Liquidez Diária</div>
            <div class="desc">Ideal para reserva de emergência. Saque a qualquer momento.</div>
            <div class="rent">100% do CDI</div>
            <div class="btn">Investir</div>
        </a>

        <!-- CDB 2 -->
        <a class="card"
           href="${pageContext.request.contextPath}/app?command=escolherInvestimento&produto=CDB_1ANO">
            <div class="titulo">CDB 1 Ano</div>
            <div class="desc">Rende mais, porém exige permanência mínima.</div>
            <div class="rent">115% do CDI</div>
            <div class="btn">Investir</div>
        </a>

    </div>

</div>

</body>
</html>
