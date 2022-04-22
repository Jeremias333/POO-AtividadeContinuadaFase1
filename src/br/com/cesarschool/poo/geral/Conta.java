package br.com.cesarschool.poo.geral;
import java.time.LocalDate;

public class Conta {
	private long numero;
	private TipoStatus status;
	private LocalDate dataAbertura;
	private double saldo;

	public Conta(long numero, TipoStatus status, LocalDate dataAbertura) {
		this.numero = numero;
		this.status = status;
		this.dataAbertura = dataAbertura;
		this.saldo = 0;
	}
	
	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public TipoStatus getStatus() {
		return status;
	}

	public void setStatus(TipoStatus status) {
		this.status = status;
	}

	public LocalDate getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	boolean validarNumero(){
		if(this.numero > 0) {
			return true;
		}
		return false;
	}
	
	public int calcularEscoreConta() {
		if (this.status != TipoStatus.BLOQUEADA) {
			if(this.status == TipoStatus.ENCERRADA) {
				return 0;
			}
			
			int dataDiff = LocalDate.now().getDayOfYear() - dataAbertura.getDayOfYear();
			//conta está ativa caso não entre no segundo if
			double scoreValue = this.saldo*3+(dataDiff*2);
			
			if(scoreValue < 5800) {
				return 1;
			}else if(scoreValue >= 5800 && scoreValue <= 1300) {
				return 2;
			}else if(scoreValue >= 13001 && scoreValue <= 39000) {
				return 3;
			}else if(scoreValue > 3900) {
				return 4;
			}
		}
		return 0;
	}
	
	public void creditar(double valor) {
		if(this.status != TipoStatus.ENCERRADA) {
			if(valor > 0) {
				this.saldo += valor;
			}
		}
	}
	
	public void debitar(double valor) {
		if(this.status != TipoStatus.BLOQUEADA) {
			if(valor > 0) {
				this.saldo -= valor;
			}
		}
	}
	
	boolean statusPreenchido() {
		return this.status != null;
	}
	
	boolean dataAberturaPreenchido() {
		return this.dataAbertura != null;
	}
	
	boolean validarDataAbertura() {
		if(this.dataAbertura.isBefore(LocalDate.now())) {
			if(this.dataAbertura.getMonthValue()-1 > LocalDate.now().getMonthValue()) {
				return true;
			}
		}
		return false;
	}
}
