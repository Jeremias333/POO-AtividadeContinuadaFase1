package br.com.cesarschool.poo.mediators;

public class StatusValidacaoConta {
	 
	public static final int STATUS_NAO_PREENCHIDO = 1;
	public static final int CONTA_JA_CADASTRADA = 2;
	public static final int CONTA_NAO_ENCONTRADA = 3;
	public static final int CONTA_NAO_INFORMADA = 4;
	public static final int NUMERO_NAO_VALIDO = 5;
	public static final int DATA_NAO_PREENCHIDA = 6;
	public static final int DATA_NAO_VALIDA = 7;
	public static final int TAXA_INVALIDA = 8;
	public static final int NOME_NAO_PREENCHIDO = 9;
	public static final int CPF_NAO_PREENCHIDO = 10;
	public static final int CPF_INVALIDO = 11;
	public static final int QTD_SITUACOES_EXCECAO = 11;
	
	private int[] codigosErros;
	private String[] mensagens;
	private boolean valido;
	
	public StatusValidacaoConta(int[] codigosErros, String[] mensagens, boolean valido) {
		super();
		this.codigosErros = codigosErros;
		this.mensagens = mensagens;
		this.valido = valido;
	}
	
	public int[] getCodigosErros() {
		return codigosErros;
	}
	public String[] getMensagens() {
		return mensagens;
	}
	public boolean isValido() {
		return valido;
	}
	void setValido(boolean valido) {
		this.valido = valido;
	}
}
