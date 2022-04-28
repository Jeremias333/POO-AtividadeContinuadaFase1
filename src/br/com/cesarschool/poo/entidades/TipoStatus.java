package br.com.cesarschool.poo.entidades;

public enum TipoStatus {
	ATIVA(1, "Ativa"), 
	ENCERRADA(2, "Encerrada"), 
	BLOQUEADA(3,"Bloqueada");
	
	private int codigo;
	private String descricao;
	
	private TipoStatus(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	
	public static TipoStatus obterPorCodigo(int codigo) {
		for (TipoStatus tipoStatus : TipoStatus.values()) {
			if (tipoStatus.getCodigo() == codigo) {
				return tipoStatus;
			}
		}
		return null;
	}
}
