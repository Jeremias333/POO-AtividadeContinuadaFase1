package br.com.cesarschool.poo.mediators;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import br.com.cesarschool.poo.entidades.Conta;
import br.com.cesarschool.poo.entidades.ContaPoupanca;
import br.com.cesarschool.poo.entidades.Correntista;
import br.com.cesarschool.poo.repositorios.RepositorioConta;

public class ContaMediator {

	private static RepositorioConta repositorioConta = RepositorioConta.getInstancia();

    private static final Scanner ENTRADA = new Scanner(System.in);
	private static final String MENSAGEM_NUMERO_NAO_VALIDO = "Numero invï¿½lido!";
	private static final String MENSAGEM_STATUS_NAO_PREENCHIDO = "Status nï¿½o preenchido!";
	private static final String MENSAGEM_CONTA_JA_CADASTRADA = "Conta jï¿½ cadastrada!";
	private static final String MENSAGEM_CONTA_NAO_ENCONTRADA = "conta nï¿½o encontrada!";
	private static final String MENSAGEM_CONTA_NAO_INFORMADA = "conta nï¿½o informada!";
	private static final String MENSAGEM_DATA_NAO_PREENCHIDA = "Data nï¿½o preenchida!";
	private static final String MENSAGEM_DATA_NAO_VALIDA = "Data invï¿½lidaa!";
	private static final String MENSAGEM_TAXA_INVALIDA = "Taxa invÃ¡lida preenchida!";
	private static final String MENSAGEM_NOME_NAO_PREENCHIDO = "Nome não preenchido";
	private static final String MENSAGEM_CPF_NAO_PREENCHIDO = "CPF não preenchido";
	private static final String MENSAGEM_CPF_INVALIDO = "CPF invalido";

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
			
			if (conta.getCorrentista() != null) {
				Correntista correntista = conta.getCorrentista();
				if(correntista.getCpf() == null) {
					codigoStatus[contErros++] = StatusValidacaoConta.CPF_NAO_PREENCHIDO;
					mensagensStatus[contErros] = MENSAGEM_CPF_NAO_PREENCHIDO;
				}
				if(correntista.getNome() == null) {
					codigoStatus[contErros++] = StatusValidacaoConta.NOME_NAO_PREENCHIDO;
					mensagensStatus[contErros] = MENSAGEM_NOME_NAO_PREENCHIDO;
				} 
				
				if(!validarCpf(correntista.getCpf())){
					codigoStatus[contErros++] = StatusValidacaoConta.CPF_INVALIDO;
					mensagensStatus[contErros] = MENSAGEM_CPF_INVALIDO;
				}
			}

			if (conta instanceof ContaPoupanca){
				ContaPoupanca contaPoupanca = (ContaPoupanca) conta;

				if (contaPoupanca.validarTaxaJuros() == false) {
					codigoStatus[contErros++] = StatusValidacaoConta.TAXA_INVALIDA;
					mensagensStatus[contErros] = MENSAGEM_TAXA_INVALIDA;
				}
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
	
	public static boolean validarCpf(String CPF) {
		
		CPF = removeCaracteresEspeciais(CPF);

		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222") || 
				CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555") || 
				CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888") || 
				CPF.equals("99999999999") || (CPF.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {     
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48);

			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}
	
	private static String removeCaracteresEspeciais(String doc) {
		if (doc.contains(".")) {
			doc = doc.replace(".", "");
		}
		if (doc.contains("-")) {
			doc = doc.replace("-", "");
		}
		if (doc.contains("/")) {
			doc = doc.replace("/", "");
		}
		return doc;
	}

    public static void creditar(long numero) {
    	Conta conta = repositorioConta.buscar(numero);
    	System.out.println("Saldo atual: " + conta.getSaldo());
    	System.out.println("");
    	System.out.println("Digite o valor que deve ser creditado: ");
    	double valor = ENTRADA.nextDouble();
        if (conta.getNumero() == numero) {
			if (conta instanceof ContaPoupanca) {
				ContaPoupanca contaPoupanca = (ContaPoupanca) conta;
				contaPoupanca.setTotalDeposito(contaPoupanca.getTotalDeposito() + 1);
				contaPoupanca.setSaldo(contaPoupanca.getSaldo() + (1+contaPoupanca.getTaxaJuros()/100)*valor);
			}else{
				conta.setSaldo(conta.getSaldo()+valor);
			}	
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
