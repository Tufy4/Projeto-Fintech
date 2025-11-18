package edu.ifsp.banco.modelo;

import java.sql.Timestamp;

public class Usuario {
	private int id;
	private String nome;
	private String email;
	private String senha;
	private String telefone;
	private String endereco;
	private tiposCliente perfil;
	private statusCliente status; 
	private Timestamp DataCriacao;
	private Timestamp DataAtualizacao;
    
    
    public enum tiposCliente{
    	CLIENTE("CLIENTE"),
    	GERENTE("GERENTE");
    	
    	private String valor;

    	tiposCliente(String valor) {
	        this.valor = valor;
	    }
	    public String getValor() {
	        return valor;
	    }
    }
    public enum statusCliente{
    	ATIVO("ATIVO"),
    	INATIVO("INATIVO"),
    	BLOQUEADO("BLOQUEADO");
    	
    	private String valor;

    	statusCliente(String valor) {
	        this.valor = valor;
	    }
	    public String getValor() {
	        return valor;
	    }
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public tiposCliente getPerfil() {
		return perfil;
	}
	public void setPerfil(tiposCliente perfil) {
		this.perfil = perfil;
	}
	public statusCliente getStatus() {
		return status;
	}
	public void setStatus(statusCliente status) {
		this.status = status;
	}
	public Timestamp getDataCriacao() {
		return DataCriacao;
	}
	public void setDataCriacao(Timestamp dataCriacao) {
		DataCriacao = dataCriacao;
	}
	public Timestamp getDataAtualizacao() {
		return DataAtualizacao;
	}
	public void setDataAtualizacao(Timestamp dataAtualizacao) {
		DataAtualizacao = dataAtualizacao;
	}
	public Usuario( String nome, String email, String senha, String telefone, String endereco,
			tiposCliente perfil, statusCliente status) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.telefone = telefone;
		this.endereco = endereco;
		this.perfil = perfil;
		this.status = status;
		this.DataCriacao=new Timestamp(System.currentTimeMillis());
		this.DataAtualizacao = new Timestamp(System.currentTimeMillis());
	}
	
	public Usuario( String nome, String email, String senha, String telefone, String endereco) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.telefone = telefone;
		this.endereco = endereco;
		this.DataCriacao=new Timestamp(System.currentTimeMillis());
		this.DataAtualizacao = new Timestamp(System.currentTimeMillis());
	}
	
	
	
	
	

    
    
    
}
