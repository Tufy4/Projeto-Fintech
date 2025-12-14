package edu.ifsp.banco.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.enums.TiposConta;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class FiltroSeguranca extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;
	private static final List<String> COMANDOS_PUBLICOS = Arrays.asList("login", "cadastro", "salvarUsuario",
			"cadastrarUsuario", "redirect", "solicitarRecuperacao", "validarToken", "redefinirSenha");

	private static final List<String> CAMINHOS_PUBLICOS = Arrays.asList("/index.jsp", "/auth/login.jsp",
			"/auth/cadastro.jsp", "/auth/recuperar_senha.jsp", "/auth/nova_senha.jsp");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession(false);
		boolean isLogado = (session != null && session.getAttribute("usuarioLogado") != null);

		String command = req.getParameter("command");
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length());

		if (path.equals("/") || path.equals("/index.jsp")) {
			chain.doFilter(request, response);
			return;
		}

		boolean isPublicPath = CAMINHOS_PUBLICOS.stream().anyMatch(path::startsWith);
		if (isPublicPath) {
			chain.doFilter(request, response);
			return;
		}

		if (path.equals("/app")) {
			if (command == null || COMANDOS_PUBLICOS.contains(command)) {
				chain.doFilter(request, response);
				return;
			}
		}

		if (path.startsWith("/app/admin/")) {

			if (!isLogado) {
				res.sendRedirect(contextPath + "/auth/login.jsp");
				return;
			} else {
				Conta conta = (Conta) session.getAttribute("contaLogado");
				if (conta == null || conta.getTipo() != TiposConta.GERENTE) {
					res.sendRedirect(contextPath + "/auth/login.jsp");
					return;
				}
			}
		}

		if (isLogado) {
			chain.doFilter(request, response);
		} else {
			req.setAttribute("erro", "Acesso negado. Faça login.");
			req.getRequestDispatcher("/auth/login.jsp").forward(request, response);
		}
	}
}