package edu.ifsp.banco.login;

import java.sql.ResultSet;

import edu.ifsp.banco.web.Command;
import jakarta.servlet.annotation.WebServlet;


public class LoginCommand implements Command{
	private static final long serialVersionUID = 1L;
	Connection conn = ConnectionFactory.getConnection();
	
	
	String user = request.getParameter("user");
	String password = request.getParameter("password");
	
	String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";
	PreparedStatement ps = conn.prepareStatement(sql);
	ps.setString(1, user);
	ps.setString(2, password);  // ou hash se estiver usando hash
	ResultSet rs = ps.executeQuery();

	if(rs.next()) {
	    System.out.println("Login bem-sucedido!");
	} else {
	    System.out.println("Usuário ou senha incorretos!");
	}

	// Fechar recursos
	rs.close();
	ps.close();
	conn.close();
}

}
