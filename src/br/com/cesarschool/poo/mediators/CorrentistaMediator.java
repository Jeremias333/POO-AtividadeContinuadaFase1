package br.com.cesarschool.poo.mediators;

import br.com.cesarschool.poo.entidades.Correntista;
import br.com.cesarschool.poo.repositorios.RepositorioCorrentista;
import br.com.cesarschool.poo.utils.Ordenador;

public class CorrentistaMediator {
	
	private static RepositorioCorrentista repositorioCorrentista = RepositorioCorrentista.getInstancia();

	public boolean incluir(Correntista correntista) {
		return repositorioCorrentista.incluir(correntista);
	}
	public boolean alterar(Correntista correntista) {
		return repositorioCorrentista.alterar(correntista);
	}
	public static Correntista buscar(String cpf) {
		return repositorioCorrentista.buscar(cpf);
	}
	public boolean excluir(String cpf) {
		return repositorioCorrentista.excluir(cpf);
	}
	
	public Correntista[] consultarTodosOrdenadoPorNome() {
		Correntista[] todos = repositorioCorrentista.buscarTodos();
		if (todos != null && todos.length > 0) {
			ordenarCorrentistaPorNome(todos);
		}
		return todos;
	}

	private void ordenarCorrentistaPorNome(Correntista[] correntista) {
		Ordenador.ordenar(correntista);
	}
}
