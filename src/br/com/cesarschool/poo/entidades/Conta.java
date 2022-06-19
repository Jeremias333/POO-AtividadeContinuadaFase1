package br.com.cesarschool.poo.entidades;
import java.time.LocalDate;

public class Conta {
	private long numero;
	private TipoStatus status;
	private LocalDate dataAbertura;
	private double saldo;
	private Correntista correntista;

	public Conta(long numero, TipoStatus status, LocalDate dataAbertura, Correntista correntista) {
		this.numero = numero;
		this.status = status;
		this.dataAbertura = dataAbertura;
		this.saldo = 0;
		this.correntista = correntista;
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
	
	public int calcularEscoreConta() {
		if (this.status != TipoStatus.BLOQUEADA) {
			if(this.status == TipoStatus.ENCERRADA) {
				return 0;
			}
			int dataDiff = LocalDate.now().getDayOfYear() - dataAbertura.getDayOfYear();
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

	public Correntista getCorrentista() {
		return correntista;
	}

	public void setCorrentista(Correntista correntista) {
		this.correntista = correntista;
	}
}
