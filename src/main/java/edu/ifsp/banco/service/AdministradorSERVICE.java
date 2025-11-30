package edu.ifsp.banco.service;

import java.util.*;

import edu.ifsp.banco.modelo.Usuario;
import edu.ifsp.banco.persistencia.AdministradorDAO;

public class AdministradorSERVICE {
	
	
	public List<Usuario> consulta() throws Exception{
		AdministradorDAO dao = new AdministradorDAO();
		List<Usuario> usuarios = dao.listarUsuariosBloqueados();
		if(usuarios.isEmpty()==true) {
			throw new Exception("Não tem usuarios bloqueados");
		}
		return usuarios;
		
	}
	
	
	public void liberar(int id) throws Exception {
		AdministradorDAO dao = new AdministradorDAO();
		List<Usuario> usuarios = dao.listarUsuariosBloqueados();
		if(dao.liberarUsuario(id)==false) {
			throw new Exception("Deu problema na liberação do usuario");
		}
	}
}
