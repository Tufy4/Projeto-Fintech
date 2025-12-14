package edu.ifsp.banco.web.usuario;

import java.io.IOException;
import java.util.List;

import edu.ifsp.banco.modelo.Conta;
import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.service.ContaSERVICE;
import edu.ifsp.banco.service.UsuarioSERVICE;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MostrarDadosUsuarioCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idParam = request.getParameter("id");

		if (idParam == null || idParam.isEmpty()) {
			response.sendRedirect("app/admin/home.jsp?msg=IdInvalido");
			return;
		}

		try {
			int idUsuario = Integer.parseInt(idParam);

			UsuarioSERVICE usuarioService = new UsuarioSERVICE();
			Usuario usuarioAlvo = usuarioService.buscarPorId(idUsuario);

			ContaSERVICE contaService = new ContaSERVICE();
			List<Conta> contasDoUsuario = contaService.buscarContasPorUsuario(idUsuario);

			request.setAttribute("usuarioDetalhe", usuarioAlvo);
			request.setAttribute("contasDetalhe", contasDoUsuario);

			request.getRequestDispatcher("/app/admin/detalhes_usuario.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("app/admin/home.jsp?msg=ErroAoCarregarUsuario");
		}
	}
}