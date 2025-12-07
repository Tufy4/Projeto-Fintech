package edu.ifsp.banco.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionSingleton {
	private static ConnectionSingleton instance = null;
	private DataSource dataSource;

	private ConnectionSingleton() {
		try {
			Context ctx = new InitialContext();
			// Busca config definida em webapp/META-INF/context.xml
			this.dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/system");
		} catch (NamingException e) {
			throw new RuntimeException("Erro ao buscar DataSource JNDI: " + e.getMessage(), e);
		}
	}

	public static synchronized ConnectionSingleton getInstance() {
		if (instance == null) {
			instance = new ConnectionSingleton();
		}
		return instance;
	}

	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}
}
