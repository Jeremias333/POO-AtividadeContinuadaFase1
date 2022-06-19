package br.com.cesarschool.poo.entidades;

import java.time.LocalDate;

public class ContaPoupanca extends Conta {

	private float taxaJuros;
	private int taxaDeposito;
	
	public ContaPoupanca(long numero, TipoStatus status, LocalDate dataAbertura, 
			Correntista correntista, float taxaJuros, int taxaDeposito) {
		super(numero, status, dataAbertura);
		this.taxaJuros = taxaJuros;
		this.taxaDeposito = taxaDeposito;
		setCorrentista(correntista);
	}

	public float getTaxaJuros() {
		return taxaJuros;
	}

	public void setTaxaJuros(float taxaJuros) {
		this.taxaJuros = taxaJuros;
	}

	public int getTaxaDeposito() {
		return taxaDeposito;
	}

	public void setTaxaDeposito(int taxaDeposito) {
		this.taxaDeposito = taxaDeposito;
	}
	
	
}
