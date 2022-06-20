package br.com.cesarschool.poo.repositorios;

import br.com.cesarschool.poo.entidades.Correntista;

public class RepositorioCorrentista extends RepositorioGenericoIdentificavel {

	private static RepositorioCorrentista instancia; 
	
	
	private RepositorioCorrentista() {		
	}
	
	public static RepositorioCorrentista getInstancia() {
		if (instancia == null) {
			instancia = new RepositorioCorrentista();
		}
		return instancia; 
	}
	
	public boolean incluir(Correntista correntista) {
		return super.incluir(correntista);
	}
	public boolean alterar(Correntista correntista) {
		return super.alterar(correntista);
	}
	
	public Correntista buscar(String cpf) {
		return (Correntista)super.buscar(cpf);
	}
	
	public boolean excluir(String cpf) {
		return super.excluir(cpf);
	}
	
	public Correntista[] buscarTodos() {
		return (Correntista[])super.buscarTodos();
	}

	@Override
	public int getTamanhoMaximoRepositorio() {
		return 1000;
	}

	@Override
	public String getNomeRepositorio() {
		return "Correntista";
	}

}
