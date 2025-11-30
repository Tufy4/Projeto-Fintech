package edu.ifsp.banco.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.modelo.enums.StatusUsuario;
import edu.ifsp.banco.modelo.enums.TipoUsuario;
import edu.ifsp.banco.modelo.enums.TiposConta;

public class AdministradorDAO {
	public List<Usuario> listarUsuariosBloqueados() {
	    List<Usuario> usuarios = new ArrayList<>();

	    String sql = "SELECT ID, NOME, EMAIL, TELEFONE, ENDERECO, PERFIL, STATUS FROM USUARIOS WHERE STATUS = 'BLOQUEADO'";

	    try (Connection conn = ConnectionSingleton.getInstance().getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            Usuario u = new Usuario();
	            u.setId(rs.getInt("ID"));
	            u.setNome(rs.getString("NOME"));
	            u.setEmail(rs.getString("EMAIL"));
	            u.setTelefone(rs.getString("TELEFONE"));
	            u.setEndereco(rs.getString("ENDERECO"));
	            u.setPerfil(TipoUsuario.valueOf(rs.getString("PERFIL")));
	            u.setStatus(StatusUsuario.valueOf(rs.getString("STATUS")));

	            usuarios.add(u);
	            System.out.println(u);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return usuarios;
	}
}
