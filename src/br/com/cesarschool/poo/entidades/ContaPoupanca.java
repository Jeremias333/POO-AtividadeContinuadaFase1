package br.com.cesarschool.poo.entidades;

import java.time.LocalDate;

public class ContaPoupanca extends Conta {

	private float taxaJuros;
	private int totalDeposito;
	
	public ContaPoupanca(long numero, TipoStatus status, LocalDate dataAbertura, 
			Correntista correntista, float taxaJuros, int totalDeposito) {
		super(numero, status, dataAbertura, correntista);
		this.taxaJuros = taxaJuros;
		this.totalDeposito = 0;
	}

	public float getTaxaJuros() {
		return taxaJuros;
	}

	public void setTaxaJuros(float taxaJuros) {
		this.taxaJuros = taxaJuros;
	}

	public int getTotalDeposito() {
		return totalDeposito;
	}

	public void setTotalDeposito(int totalDeposito) {
		this.totalDeposito = totalDeposito;
	}
	
	public boolean validarTaxaJuros() {
		return taxaJuros > 0;
	}
}
