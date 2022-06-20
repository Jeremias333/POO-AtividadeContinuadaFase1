package br.com.cesarschool.poo.repositorios;

import br.com.cesarschool.poo.entidades.Conta;

public class RepositorioConta extends RepositorioGenericoIdentificavel {
	private static RepositorioConta instancia = null;
	
	private RepositorioConta() {
		
	}
	
	public static RepositorioConta getInstancia() {
		if (instancia == null) {
			instancia = new RepositorioConta();
		}
		return instancia; 
	}
	
	public boolean incluir(Conta Conta) {
		return super.incluir(Conta);
	}
	public boolean alterar(Conta Conta) {
		return super.alterar(Conta);				
	}
	
	public Conta buscar(long numero) {
		return (Conta)super.buscar("" + numero);
	}
	
	public boolean excluir(long numero) {
		return super.excluir("" + numero);
	}
	
	public Conta[] buscarTodos() {
		return (Conta[])super.buscarTodos();
	}

	@Override
	public int getTamanhoMaximoRepositorio() {
		return 10000;
	}

	@Override
	public String getNomeRepositorio() {
		return "Conta";
	}		
	
}
