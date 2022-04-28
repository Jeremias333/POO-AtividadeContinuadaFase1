package br.com.cesarschool.poo.mediators;

import java.time.LocalDate;
import java.util.Scanner;

import br.com.cesarschool.poo.entidades.Conta;
import br.com.cesarschool.poo.repositorios.RepositorioConta;

public class ContaMediator {

	private static RepositorioConta repositorioConta = RepositorioConta.getInstancia();

    private static final Scanner ENTRADA = new Scanner(System.in);
	private static final String MENSAGEM_NUMERO_NAO_VALIDO = "Numero inválido!";
	private static final String MENSAGEM_STATUS_NAO_PREENCHIDO = "Status não preenchido!";
	private static final String MENSAGEM_CONTA_JA_CADASTRADA = "Conta já cadastrada!";
	private static final String MENSAGEM_CONTA_NAO_ENCONTRADA = "conta não encontrada!";
	private static final String MENSAGEM_CONTA_NAO_INFORMADA = "conta não informada!";
	private static final String MENSAGEM_DATA_NAO_PREENCHIDA = "Data não preenchida!";
	private static final String MENSAGEM_DATA_NAO_VALIDA = "Data inválidaa!";

	public StatusValidacaoConta incluir(Conta conta) {
		StatusValidacaoConta status = validar(conta);
		if (status.isValido()) {
			boolean retornoRepositorio = repositorioConta.incluir(conta);
			if (!retornoRepositorio) {
				status.getCodigosErros()[0] = StatusValidacaoConta.CONTA_JA_CADASTRADA;
				status.getMensagens()[0] = MENSAGEM_CONTA_JA_CADASTRADA;
				status.setValido(false);
			}
		}
		return status;
	}

	public StatusValidacaoConta alterar(Conta conta) {
		StatusValidacaoConta status = validar(conta);
		if (status.isValido()) {
			boolean retornoRepositorio = repositorioConta.alterar(conta);
			if (!retornoRepositorio) {
				status.getCodigosErros()[0] = StatusValidacaoConta.CONTA_NAO_ENCONTRADA;
				status.getMensagens()[0] = MENSAGEM_CONTA_NAO_ENCONTRADA;
				status.setValido(false);
			}
		}
		return status;
	}

	public boolean excluir(long numero) {
		return repositorioConta.excluir(numero);
	}

	public Conta buscar(long numero) {
		return repositorioConta.buscar(numero);
	}

	public static StatusValidacaoConta validar(Conta conta) {
		int[] codigoStatus = new int[StatusValidacaoConta.QTD_SITUACOES_EXCECAO];
		String[] mensagensStatus = new String[StatusValidacaoConta.QTD_SITUACOES_EXCECAO];
		int contErros = 0;
		if (conta == null) {
			codigoStatus[contErros++] = StatusValidacaoConta.CONTA_NAO_INFORMADA;
			mensagensStatus[contErros] = MENSAGEM_CONTA_NAO_INFORMADA;
		} else {
			if (!validarNumero(conta)) {
				codigoStatus[contErros++] = StatusValidacaoConta.NUMERO_NAO_VALIDO;
				mensagensStatus[contErros] = MENSAGEM_NUMERO_NAO_VALIDO;
			}

			if (conta.getStatus() == null) {
				codigoStatus[contErros++] = StatusValidacaoConta.STATUS_NAO_PREENCHIDO;
				mensagensStatus[contErros] = MENSAGEM_STATUS_NAO_PREENCHIDO;
			}
			
			if (conta.getDataAbertura() == null) {
				codigoStatus[contErros++] = StatusValidacaoConta.DATA_NAO_PREENCHIDA;
				mensagensStatus[contErros] = MENSAGEM_DATA_NAO_PREENCHIDA;
			}
			
			if (!validarDataAbertura(conta)) {
				codigoStatus[contErros++] = StatusValidacaoConta.DATA_NAO_VALIDA;
				mensagensStatus[contErros] = MENSAGEM_DATA_NAO_VALIDA;
			}
			
		}
		return new StatusValidacaoConta(codigoStatus, mensagensStatus, contErros == 0);
	}

	private static boolean validarNumero(Conta conta) {
		if (conta.getNumero() < 0) {
			return false;
		}
		return true;
	}
	
	public static boolean validarDataAbertura(Conta conta) {
		LocalDate data;
		data = conta.getDataAbertura();
		if(data.isBefore(LocalDate.now()) || data.isEqual(LocalDate.now()) ) {
			if(data.getMonthValue() > LocalDate.now().getMonthValue()-1) {
				return true;
			}
		}
		return false;
	}
	
    public static void creditar(long numero) {
    	Conta conta = repositorioConta.buscar(numero);
    	System.out.println("Saldo atual: " + conta.getSaldo());
    	System.out.println("");
    	System.out.println("Digite o valor que deve ser creditado: ");
    	double valor = ENTRADA.nextDouble();
        if (conta.getNumero() == numero) {
            conta.setSaldo(conta.getSaldo()+valor);
        }
    }

    public static void debitar(long numero) {
    	Conta conta = repositorioConta.buscar(numero);
    	System.out.println("Saldo atual: " + conta.getSaldo());
    	System.out.println("");
    	System.out.println("Digite o valor que deve ser debitado: ");
    	double valor = ENTRADA.nextDouble();
        if (conta.getNumero() == numero) {
            conta.setSaldo(conta.getSaldo()-valor);
        }
    }
}
