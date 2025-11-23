package edu.ifsp.banco.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.ifsp.banco.persistencia.ConnectionSingleton;
import edu.ifsp.banco.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			String user = request.getParameter("user");
			String password = request.getParameter("password");
			String status;

			String sql = "SELECT * FROM usuarios WHERE EMAIL = ? AND SENHA = ?";
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, user);
				ps.setString(2, password);

				try (ResultSet rs = ps.executeQuery()) {

					if (rs.next()) {
						status = rs.getString("STATUS");
						if (!status.equalsIgnoreCase("BLOQUEADO")) {
							System.out.println("Login bem-sucedido!");
							response.getWriter().write("Login bem-sucedido!");
							response.setStatus(HttpServletResponse.SC_OK); // Status HTTP 200
							HttpSession session = request.getSession();
							session.setAttribute("username", user);
						} else {
							response.getWriter().write("Usuário não liberado");
						}

					} else {
						System.out.println("Usuário ou senha incorretos!");
						response.getWriter().write("Usuário ou senha incorretos!");
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Status HTTP 401
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("Erro no processamento do login.");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Status HTTP 500
		}
	}
}
