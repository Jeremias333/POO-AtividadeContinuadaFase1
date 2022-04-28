package br.com.cesarschool.poo.telas;
import java.time.LocalDate;
import br.com.cesarschool.poo.repositorios.RepositorioConta;
import br.com.cesarschool.poo.entidades.Conta;
import br.com.cesarschool.poo.entidades.TipoStatus;
import br.com.cesarschool.poo.mediators.ContaMediator;
import br.com.cesarschool.poo.mediators.StatusValidacaoConta;
import java.util.Scanner;


public class TelaConta {
	public static final int CODIGO_DESCONHECIDO = -1;
    private static final Scanner ENTRADA = new Scanner(System.in);
    private RepositorioConta repositorioConta = RepositorioConta.getInstancia();

    private void printMenu() {		
		System.out.println("1- Incluir");
		System.out.println("2- Alterar");
        System.out.println("3- Encerrar");
        System.out.println("4- Bloquear");
        System.out.println("5- Desbloquear");
		System.out.println("6- Excluir");
		System.out.println("7- Buscar");
        System.out.println("8- Creditar");
        System.out.println("9- Debitar");
		System.out.println("10- Sair");
		System.out.print("Digite a opção: ");
	}
    
    public void executaTelaConta() {
        while (true) {
            long codigo = CODIGO_DESCONHECIDO;
            printMenu();
            int call = ENTRADA.nextInt();
            if (call == 1) {
                processaIncluir();
            } else if (call == 2) {
            	codigo = processaBusca();
            	if (codigo != CODIGO_DESCONHECIDO) {
					processaAlterar(codigo);
				}
            } else if (call == 3) {
            	codigo = processaBusca();
            	if (codigo != CODIGO_DESCONHECIDO) {
            		processaEncerrar(codigo);
            		printValores(codigo);
				}
            } else if (call == 4) {
            	codigo = processaBusca();
            	if (codigo != CODIGO_DESCONHECIDO) {
            		processaBloquear(codigo);
            		printValores(codigo);
				}
            } else if (call == 5) {
            	codigo = processaBusca();
            	if (codigo != CODIGO_DESCONHECIDO) {
            		processaDesbloquear(codigo);
            		printValores(codigo);
				}
            } else if (call == 6) {
            	codigo = processaBusca();
            	if (codigo != CODIGO_DESCONHECIDO) {
            		processaExclusao(codigo);
				}
            } else if (call == 7) {
            	printValores(processaBusca());
            } else if (call == 8) {
            	codigo = processaBusca();
            	if (codigo != CODIGO_DESCONHECIDO) {
            		ContaMediator.creditar(codigo);
				}
            } else if (call == 9) {
            	codigo = processaBusca();
            	if (codigo != CODIGO_DESCONHECIDO) {
            		ContaMediator.debitar(codigo);
				}
            } else if (call == 10) {
                System.out.println("Saindo do cadastro de conta");
                System.exit(0);
            } else {
                System.out.println("Opção inválida!!");
            }
        }
    }
    
    //incluir
    private void processaIncluir() {
		Conta conta = capturaConta(CODIGO_DESCONHECIDO);
		StatusValidacaoConta retornoValidacao = ContaMediator.validar(conta);
		if (retornoValidacao.isValido() == false) {
			boolean retornoRepositorio = repositorioConta.incluir(conta);
			if (retornoRepositorio) {
				System.out.println("Conta incluída com sucesso!");
			} else {
				System.out.println("Erro na inclusão de conta!");
			}
		} else {
			for (String obj : retornoValidacao.getMensagens()) {
				System.out.println(obj);
			}
		}		
	}
    
    //alterar (só pode alterar a data de abertura)
    private void processaAlterar (long numero) {
		Conta conta = capturaConta(numero);
		StatusValidacaoConta retornoValidacao = ContaMediator.validar(conta);
		if (retornoValidacao.isValido() == false) {
			boolean retornoRepositorio = repositorioConta.alterar(conta);
			if (retornoRepositorio) {
				System.out.println("Conta alterado com sucesso!");
			} else {
				System.out.println("Erro na altera��o de conta!");
			}
		} else {
			for (String obj : retornoValidacao.getMensagens()) {
				System.out.println(obj);
			}	
		}		
	}


    //encerrar (altera o status, contas já encerradas não podem ser encerradas novamente)
    private void processaEncerrar(long numero) {
    	Conta conta = repositorioConta.buscar(numero);
		 if (conta.getStatus() != TipoStatus.ENCERRADA) {
	        conta.setStatus(TipoStatus.ENCERRADA);
	    }
    }
    
    //bloquear (altera o status, contas encerradas ou já bloqueadas não podem ser bloqueadas)
    private void processaBloquear(long numero) {
    	Conta conta = repositorioConta.buscar(numero);
        if (conta.getStatus() != TipoStatus.ENCERRADA && conta.getStatus() != TipoStatus.BLOQUEADA) {
            conta.setStatus(TipoStatus.BLOQUEADA);
        }
    }
    
    //desbloquear (altera o status, contas encerradas ou já bloqueadas não podem ser bloqueadas)
    private void processaDesbloquear(long numero) {
    	Conta conta = repositorioConta.buscar(numero);
        if (conta.getStatus() == TipoStatus.BLOQUEADA) {
            conta.setStatus(TipoStatus.ATIVA);
        }
    }
     
    //excluir
    private void processaExclusao(long numero) {
		boolean retornoRepositorio = repositorioConta.excluir(numero);
		if (retornoRepositorio) {
			System.out.println("Conta excluído com sucesso!");
		} else {
			System.out.println("Erro na exclusão de conta!");
		}
	}
    
    //buscar (mostrar os atributos e o escore) 
    private long processaBusca() {
		System.out.println("Digite o numero: ");
		long numero = ENTRADA.nextLong();
		Conta conta = repositorioConta.buscar(numero);
		if (conta == null) {
			System.out.println("Conta não encontrado!");
			return CODIGO_DESCONHECIDO;
		} else {
			return numero;
		}
	}
    public Conta capturaConta(long codigoDaAlteracao) {
		long numero;
		LocalDate dataAbertura;
		Conta conta;
		int day;
		int year;
		int month;
			
		if (codigoDaAlteracao == CODIGO_DESCONHECIDO) {
			System.out.print("Digite o numero: ");
			numero = ENTRADA.nextLong();
			dataAbertura = LocalDate.now();
			System.out.print("Digite o Status da conta (1, 2 ou 3): ");
		    int statusTipo = ENTRADA.nextInt();
		    TipoStatus status = TipoStatus.obterPorCodigo(statusTipo);
		    return new Conta(numero, status, dataAbertura);
			} else {
				numero = codigoDaAlteracao;
				printValores(numero);
				conta = repositorioConta.buscar(numero);
				System.out.print("Digite o dia da abertura: ");
				day = ENTRADA.nextInt();
				System.out.print("Digite o m�s da abertura: ");
				month = ENTRADA.nextInt();
				System.out.print("Digite o ano da abertura: ");
				year = ENTRADA.nextInt();
				dataAbertura = LocalDate.of(year, month, day);
				System.out.print("nova data: "+day+"/"+month+"/"+year);
				conta.setDataAbertura(dataAbertura);
		        return(conta);
			}
	    }
	    public void printValores(long numero) {
	    	if(numero != CODIGO_DESCONHECIDO) {
	    		Conta conta = repositorioConta.buscar(numero);
	        	System.out.println("Numero: " + conta.getNumero());
	    		System.out.println("Data de abertura: " + conta.getDataAbertura());
	    		System.out.println("Saldo: " + conta.getSaldo());
	    		System.out.println("Status: " + conta.getStatus().getDescricao());
	    	}
	    }
}
