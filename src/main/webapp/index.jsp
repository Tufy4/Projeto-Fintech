<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BitPay | Inovação Financeira</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        
        /* Hero Section */
        .hero-section {
            background: linear-gradient(135deg, rgba(0,0,0,0.8) 0%, rgba(13,110,253,0.6) 100%),
                        url('https://images.unsplash.com/photo-1565514020176-dbf2277e4c73?auto=format&fit=crop&w=1920&q=80');
            background-size: cover;
            background-position: center;
            color: white;
            padding: 120px 0;
            margin-bottom: 40px;
        }

        /* Cards */
        .feature-card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.05);
            transition: all 0.3s ease;
            height: 100%;
            background: white;
            padding: 2rem;
        }

        .feature-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 15px 30px rgba(0,0,0,0.1);
        }

        .icon-box {
            font-size: 3rem;
            color: #0d6efd;
            margin-bottom: 1.5rem;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark py-3">
        <div class="container">
            <a class="navbar-brand fw-bold fs-4" href="#">BitPay</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <ul class="navbar-nav gap-3 align-items-center">
                    <li class="nav-item"><a class="nav-link active" href="#">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="#recursos">Recursos</a></li>
                    <li class="nav-item">
                        <a class="btn btn-outline-light px-4 rounded-pill" 
                           href="${pageContext.request.contextPath}/app?command=redirect&url=auth/login.jsp">Entrar</a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-primary px-4 rounded-pill" 
                           href="${pageContext.request.contextPath}/app?command=redirect&url=auth/cadastro.jsp">Abra sua Conta</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <header class="hero-section text-center">
        <div class="container">
            <h1 class="display-3 fw-bold mb-4">O Futuro do Seu Dinheiro</h1>
            <p class="lead mb-5 opacity-75">
                Segurança, rentabilidade e tecnologia em um só lugar.<br>
                Gerencie seus ativos com a inteligência da BitPay.
            </p>
            <div class="d-flex justify-content-center gap-3">
                <a href="${pageContext.request.contextPath}/app?command=redirect&url=auth/cadastro.jsp" 
                   class="btn btn-primary btn-lg px-5 rounded-pill fw-bold">Começar Agora</a>
                <a href="${pageContext.request.contextPath}/app?command=redirect&url=auth/login.jsp" 
                   class="btn btn-outline-light btn-lg px-5 rounded-pill">Já sou cliente</a>
            </div>
        </div>
    </header>

    <section id="recursos" class="container py-5 mb-5">
        <div class="row g-4 row-cols-1 row-cols-lg-3">
            
            <div class="col">
                <div class="feature-card text-center">
                    <div class="icon-box"><i class="bi bi-shield-lock"></i></div>
                    <h3 class="h4 fw-bold mb-3">Segurança Total</h3>
                    <p class="text-secondary">Seus dados e transações protegidos com as melhores práticas de criptografia ponta a ponta.</p>
                </div>
            </div>
            
            <div class="col">
                <div class="feature-card text-center">
                    <div class="icon-box"><i class="bi bi-graph-up-arrow"></i></div>
                    <h3 class="h4 fw-bold mb-3">Investimentos</h3>
                    <p class="text-secondary">Diversifique sua carteira com opções de renda fixa e variável. Rentabilidade em tempo real.</p>
                </div>
            </div>
            
            <div class="col">
                <div class="feature-card text-center">
                    <div class="icon-box"><i class="bi bi-bank"></i></div>
                    <h3 class="h4 fw-bold mb-3">Crédito Inteligente</h3>
                    <p class="text-secondary">Simule e contrate empréstimos com transparência. Sistema SAC integrado e taxas justas.</p>
                </div>
            </div>

        </div>
    </section>

    <footer class="bg-dark text-white text-center py-4">
        <p class="mb-0 opacity-50">&copy; 2025 BitPay Fintech. Todos os direitos reservados.</p>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>