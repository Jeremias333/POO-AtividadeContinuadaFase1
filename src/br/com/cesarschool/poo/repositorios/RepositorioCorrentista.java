package br.com.cesarschool.poo.repositorios;

import br.com.cesarschool.poo.entidades.Correntista;

public class RepositorioCorrentista {

	private static final int TAMANHO_MAX_FORNECEDORES = 1000;
	private static RepositorioCorrentista instancia; 
	
	private Correntista[] cadastroFornecedor = new Correntista[TAMANHO_MAX_FORNECEDORES];
	private int tamanhoAtual = 0;
	
	private RepositorioCorrentista() {
		
	}
	
	public static RepositorioCorrentista getInstancia() {
		if (instancia == null) {
			instancia = new RepositorioCorrentista();
		}
		return instancia; 
	}
	
	public boolean incluir(Correntista correntista) {
		if (buscarIndice(correntista.getCpf()) != -1) {
			return false;
		} else if (tamanhoAtual == TAMANHO_MAX_FORNECEDORES - 1) {
			return false;
		} else {
			for (int i = 0; i < cadastroFornecedor.length; i++) {
				if (cadastroFornecedor[i] == null) {
					cadastroFornecedor[i] = correntista; 
					break;
				}
			}
			tamanhoAtual++; 
			return true; 
		}
	}
	public boolean alterar(Correntista correntista) {
		int indice = buscarIndice(correntista.getCpf()); 
		if (indice == -1) {
			return false;
		} else {
			cadastroFornecedor[indice] = correntista;
			return true; 
		}
	}
	
	public Correntista buscar(String cpf) {
		int indice = buscarIndice(cpf);
		if (indice == -1) {
			return null;
		} else {
			return cadastroFornecedor[indice];
		}
	}
	
	public boolean excluir(String cpf) {
		int indice = buscarIndice(cpf);
		if (indice == -1) {
			return false;
		} else {
			cadastroFornecedor[indice] = null;
			tamanhoAtual--;
			return true;
		}		
	}
	
	public Correntista[] buscarTodos() {
		Correntista[] resultado = new Correntista[tamanhoAtual];
		int indice = 0;
		for (Correntista correntista : cadastroFornecedor) {
			if (correntista != null) {
				resultado[indice++] = correntista; 
			}
		}
		return resultado;
	}
	
	private int buscarIndice(String cpf) {		
		for (int i = 0; i < cadastroFornecedor.length; i++) {
			Correntista correntista = cadastroFornecedor[i];
			if (correntista != null && correntista.getCpf().equals(cpf)) {
				return i; 				
			}
		}
		return -1;
	}		
}
