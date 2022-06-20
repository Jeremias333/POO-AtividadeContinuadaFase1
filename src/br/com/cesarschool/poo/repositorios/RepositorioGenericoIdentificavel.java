package br.com.cesarschool.poo.repositorios;

import br.com.cesarschool.poo.excecoes.ExcecaoObjetoJaExistente;
import br.com.cesarschool.poo.excecoes.ExcecaoValidacao;
import br.com.cesarschool.poo.utils.Identificavel;

public abstract class RepositorioGenericoIdentificavel {
	
	private static final String JA_EXISTENETE = " já existente!";
	private static final String NAO_EXISTENETE = " não existente!";
	private Identificavel[] cadastro;
	private int tamanhoAtual = 0;
	
	public RepositorioGenericoIdentificavel() {
		cadastro = new Identificavel[getTamanhoMaximoRepositorio()];
	}
	
	public abstract int getTamanhoMaximoRepositorio(); 
	public abstract String getNomeRepositorio();
	
	public boolean incluir(Identificavel identificavel) throws ExcecaoObjetoJaExistente {
		if (buscarIndice(identificavel.getIdentificadorUnico()) != -1) {
			throw new ExcecaoObjetoJaExistente(getNomeRepositorio() + JA_EXISTENETE);
		} else if (tamanhoAtual == getTamanhoMaximoRepositorio() - 1) {
			throw new ExcecaoValidacao("Tamanho máximo do repositório excedido!");
		} else {
			for (int i = 0; i < cadastro.length; i++) {
				if (cadastro[i] == null) {
					cadastro[i] = identificavel; 
					break;
				}
			}
			tamanhoAtual++; 
			return true; 
		}
	}
	public boolean alterar(Identificavel identificavel) {
		int indice = buscarIndice(identificavel.getIdentificadorUnico()); 
		if (indice == -1) {
			return false;
		} else {
			cadastro[indice] = identificavel;
			return true; 
		}
	}
	
	public Identificavel buscar(String identificadorUnico) {
		int indice = buscarIndice(identificadorUnico);
		if (indice == -1) {
			return null;
		} else {
			return cadastro[indice];
		}
	}
	
	public boolean excluir(String identificadorUnico) {
		int indice = buscarIndice(identificadorUnico);
		if (indice == -1) {
			return false;
		} else {
			cadastro[indice] = null;
			tamanhoAtual--;
			return true;
		}		
	}
	
	public Identificavel[] buscarTodos() {
		Identificavel[] resultado = new Identificavel[tamanhoAtual];
		int indice = 0;
		for (Identificavel identificavel : cadastro) {
			if (identificavel != null) {
				resultado[indice++] = identificavel; 
			}
		}
		return resultado;
	}
	
	private int buscarIndice(String identificadorUnico) {		
		for (int i = 0; i < cadastro.length; i++) {
			Identificavel identificavel = cadastro[i];
			if (identificavel != null && identificavel.getIdentificadorUnico().equals(identificadorUnico)) {
				return i; 				
			}
		}
		return -1;
	}		
}
