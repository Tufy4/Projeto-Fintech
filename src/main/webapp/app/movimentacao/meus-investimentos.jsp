<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="edu.ifsp.banco.modelo.Investimento" %>
<%@ page import="edu.ifsp.banco.modelo.enums.StatusInvestimento" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.math.BigDecimal" %>

<%
    Locale localeBR = new Locale("pt", "BR");
    NumberFormat dinheiro = NumberFormat.getCurrencyInstance(localeBR);
    SimpleDateFormat dataFmt = new SimpleDateFormat("dd/MM/yyyy");
    
    List<Investimento> lista = (List<Investimento>) request.getAttribute("investimentos");
    BigDecimal total = (BigDecimal) request.getAttribute("totalInvestido");
    if(total == null) total = BigDecimal.ZERO;
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Meus Investimentos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        /* Hero Section com Gradiente */
        .hero-invest {
            background: linear-gradient(135deg, #0d6efd 0%, #0099ff 100%);
            color: white;
            padding: 60px 0 100px 0;
            margin-bottom: -60px; /* Efeito de sobreposição dos cards */
            border-radius: 0 0 50px 50px;
            box-shadow: 0 10px 20px rgba(13, 110, 253, 0.2);
        }

        .total-badge {
            background-color: rgba(255, 255, 255, 0.2);
            padding: 10px 20px;
            border-radius: 15px;
            backdrop-filter: blur(5px);
            display: inline-block;
            margin-top: 15px;
        }

        /* Cards de Investimento */
        .invest-card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.05);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            background: white;
            overflow: hidden;
            height: 100%;
        }

        .invest-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 35px rgba(0,0,0,0.1);
        }

        .card-header-custom {
            background-color: transparent;
            border-bottom: 1px solid #eee;
            padding: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .product-icon {
            width: 50px;
            height: 50px;
            background-color: #e7f1ff;
            color: #0d6efd;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
        }

        .card-body {
            padding: 25px;
        }

        .value-display {
            font-size: 1.8rem;
            font-weight: 700;
            color: #2c3e50;
            margin-bottom: 15px;
        }

        .detail-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            color: #6c757d;
            font-size: 0.95rem;
        }

        .status-badge {
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 0.8rem;
            font-weight: 600;
            text-transform: uppercase;
        }

        .status-ativo { background-color: #d1e7dd; color: #0f5132; }
        .status-encerrado { background-color: #f8d7da; color: #842029; }

      
        .btn-fab {
            position: fixed;
            bottom: 30px;
            right: 30px;
            width: 60px;
            height: 60px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            box-shadow: 0 4px 15px rgba(13, 110, 253, 0.4);
            z-index: 1000;
        }
    </style>
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand fw-bold" href="#">BitPay <span class="badge bg-primary">IB</span></a>
            <div class="d-flex">
                <a href="${pageContext.request.contextPath}/app/home.jsp" class="btn btn-outline-light btn-sm me-2">Voltar</a>
                <a href="${pageContext.request.contextPath}/app?command=logout" class="btn btn-danger btn-sm">Sair</a>
            </div>
        </div>
    </nav>

    <div class="hero-invest text-center">
        <div class="container">
            <h1 class="fw-bold display-5">Carteira de Ativos</h1>
            <p class="lead opacity-75">Acompanhe a evolução do seu patrimônio.</p>
            
            <div class="total-badge">
                <span class="d-block text-uppercase small opacity-75">Total Aplicado (Ativo)</span>
                <span class="fs-2 fw-bold"><%= dinheiro.format(total) %></span>
            </div>
        </div>
    </div>

    <div class="container" style="padding-bottom: 80px;">
        <div class="row g-4">
            
            <% if (lista == null || lista.isEmpty()) { %>
                <div class="col-12 text-center mt-5">
                    <div class="p-5 bg-white rounded shadow-sm">
                        <i class="bi bi-piggy-bank display-1 text-muted"></i>
                        <h3 class="mt-3 text-muted">Você ainda não possui investimentos.</h3>
                        <a href="${pageContext.request.contextPath}/app/movimentacao/investir.jsp" class="btn btn-primary mt-3 px-4 rounded-pill">
                            Começar a Investir
                        </a>
                    </div>
                </div>
            <% } else { 
                for(Investimento inv : lista) { 
                    boolean isAtivo = inv.getStatus() == StatusInvestimento.ATIVO;
                    String statusClass = isAtivo ? "status-ativo" : "status-encerrado";
                    String icon = inv.getTipoInvestimento().contains("CDB") ? "bi-graph-up-arrow" : "bi-coin";
            %>
            
            <div class="col-md-6 col-lg-4">
                <div class="invest-card">
                    <div class="card-header-custom">
                        <div class="d-flex align-items-center gap-3">
                            <div class="product-icon">
                                <i class="bi <%= icon %>"></i>
                            </div>
                            <div>
                                <h5 class="m-0 fw-bold"><%= inv.getTipoInvestimento() %></h5>
                                <small class="text-muted">Renda Fixa</small>
                            </div>
                        </div>
                        <span class="status-badge <%= statusClass %>">
                            <%= inv.getStatus() %>
                        </span>
                    </div>
                    
                    <div class="card-body">
                        <div class="value-display">
                            <%= dinheiro.format(inv.getValorInvestido()) %>
                        </div>
                        
                        <div class="detail-row">
                            <span><i class="bi bi-calendar-event me-2"></i>Aplicado em:</span>
                            <span class="fw-medium"><%= dataFmt.format(inv.getDataInicio()) %></span>
                        </div>
                        
                        <div class="detail-row">
                            <span><i class="bi bi-calendar-check me-2"></i>Vencimento:</span>
                            <span class="fw-medium">
                                <%= (inv.getDataFim() != null) ? dataFmt.format(inv.getDataFim()) : "Liquidez Diária" %>
                            </span>
                        </div>
                        
                        <% if(isAtivo) { %>
                        <hr class="my-3 opacity-25">
                        <div class="d-grid">
                            <button class="btn btn-outline-primary btn-sm rounded-pill" disabled>
                                Processando Rendimentos...
                            </button>
                        </div>
                        <% } %>
                    </div>
                </div>
            </div>
            
            <% } 
            }  %>
            
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/app/movimentacao/investir.jsp" 
       class="btn btn-primary btn-fab" title="Novo Investimento">
        <i class="bi bi-plus-lg"></i>
    </a>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>