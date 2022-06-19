package br.com.cesarschool.poo.mediators;

import br.com.cesarschool.poo.entidades.Correntista;
import br.com.cesarschool.poo.repositorios.RepositorioCorrentista;

public class CorrentistaMediator {
	
	private RepositorioCorrentista repositorioCorrentista = RepositorioCorrentista.getInstancia();

	public boolean incluir(Correntista correntista) {
		return repositorioCorrentista.incluir(correntista);
	}
	public boolean alterar(Correntista correntista) {
		return repositorioCorrentista.alterar(correntista);
	}
	public Correntista buscar(String cpf) {
		return repositorioCorrentista.buscar(cpf);
	}
	public boolean excluir(String cpf) {
		return repositorioCorrentista.excluir(cpf);
	}
	
	public Correntista[] consultarTodosOrdenadoPorNome() {
		Correntista[] todos = repositorioCorrentista.buscarTodos();
		if (todos != null && todos.length > 0) {
			ordenarFornecedorPorNome(todos);
		}
		return todos;
	}
	private void ordenarFornecedorPorNome(Correntista[] correntista) {
		Correntista ax = null;
		for (int i = 0; i < correntista.length; i++) {
			for (int k = i; k < correntista.length; k++) {
				if (correntista[i].getNome().compareTo(correntista[k].getNome()) > 0) {
					ax = correntista[i];
					correntista[i] = correntista[k];
					correntista[k] = ax;
				}
			} 
		}
	}
}
