package br.com.cesarschool.poo.geral;
import java.time.LocalDate;
import java.util.Scanner;

public class TelaConta {
	private static final int CODIGO_DESCONHECIDO = -1;
    private static final Scanner ENTRADA = new Scanner(System.in);
    private RepositorioConta repositorioConta = new RepositorioConta();

    
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
				}
            } else if (call == 4) {
            	codigo = processaBusca();
            	if (codigo != CODIGO_DESCONHECIDO) {
            		processaBloquear(codigo);
				}
            } else if (call == 5) {
            	codigo = processaBusca();
            	if (codigo != CODIGO_DESCONHECIDO) {
            		processaDesbloquear(codigo);
				}
            } else if (call == 6) {
            	codigo = processaBusca();
            	if (codigo != CODIGO_DESCONHECIDO) {
            		processaExclusao(codigo);
				}
            } else if (call == 7) {
                processaBusca();
            } else if (call == 8) {
            	codigo = processaBusca();
            	if (codigo != CODIGO_DESCONHECIDO) {
            		processaCreditar(codigo);
				}
            } else if (call == 9) {
            	codigo = processaBusca();
            	if (codigo != CODIGO_DESCONHECIDO) {
            		processaDebitar(codigo);
				}
            } else if (call == 10) {
                System.out.println("Saindo do cadastro de conta");
                System.exit(0);
            } else {
                System.out.println("Opção inválida!!");
            }
            
        }
    }

  
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
    
    //incluir
    private void processaIncluir() {
		Conta conta = capturaConta(CODIGO_DESCONHECIDO);
		String retornoValidacao = validar(conta);
		if (retornoValidacao == null) {
			boolean retornoRepositorio = repositorioConta.incluir(conta);
			if (retornoRepositorio) {
				System.out.println("Conta incluída com sucesso!");
			} else {
				System.out.println("Erro na inclusão de conta!");
			}
		} else {
			System.out.println(retornoValidacao);
		}		
	}
    
    //alterar (só pode alterar a data de abertura)
    private void processaAlterar (long numero) {
		Conta conta = capturaConta(numero);
		String retornoValidacao = validar(conta);
		if (retornoValidacao == null) {
			boolean retornoRepositorio = repositorioConta.alterar(conta);
			if (retornoRepositorio) {
				System.out.println("Conta alterado com sucesso!");
			} else {
				System.out.println("Erro na altera��o de conta!");
			}
		} else {
			System.out.println(retornoValidacao);
		}		
	}

    private Conta capturaConta(long codigoDaAlteracao) {
		long numero;
		LocalDate dataAbertura;
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
			System.out.print("Digite o Status da conta (1, 2 ou 3): ");
	        int statusTipo = ENTRADA.nextInt();
	        TipoStatus status = TipoStatus.obterPorCodigo(statusTipo);
	        return new Conta(numero, status);
		}
    }

    //encerrar (altera o status, contas já encerradas não podem ser encerradas novamente)
    public void processaEncerrar(long numero) {
    	Conta conta = repositorioConta.buscar(numero);
		 if (conta.getStatus() != TipoStatus.ENCERRADA) {
	        conta.setStatus(TipoStatus.ENCERRADA);
	    }
    }
    
    //bloquear (altera o status, contas encerradas ou já bloqueadas não podem ser bloqueadas)
    public void processaBloquear(long numero) {
    	Conta conta = repositorioConta.buscar(numero);
        if (conta.getStatus() != TipoStatus.ENCERRADA && conta.getStatus() != TipoStatus.BLOQUEADA) {
            conta.setStatus(TipoStatus.BLOQUEADA);
        }
    }
    
    //desbloquear (altera o status, contas encerradas ou já bloqueadas não podem ser bloqueadas)
    public void processaDesbloquear(long numero) {
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
		System.out.print("Digite o numero: ");
		long numero = ENTRADA.nextLong();
		Conta conta = repositorioConta.buscar(numero);
		if (conta == null) {
			System.out.println("Conta não encontrado!");
			return CODIGO_DESCONHECIDO;
		} else {
			System.out.println("Numero: " + conta.getNumero());
			System.out.println("Data de abertura: " + conta.getDataAbertura());
			System.out.println("Saldo: " + conta.getSaldo());
			System.out.println("Status: " + conta.getStatus().getDescricao());
			return numero;
		}
	}
    
    //creditar ---> chamada de metodos correspondentes na atualização do saldo 
    public void processaCreditar(long numero) {
    	Conta conta = repositorioConta.buscar(numero);
    	System.out.println("Saldo atual: " + conta.getSaldo());
    	System.out.println("");
    	System.out.println("Digite o valor que deve ser creditado: ");
    	double valor = ENTRADA.nextDouble();
        if (conta.getNumero() == numero) {
            conta.setSaldo(conta.getSaldo()+valor);
        }
    }

    //debitar ---> chamada de metodos correspondentes na atualização do saldo
    public void processaDebitar(long numero) {
    	Conta conta = repositorioConta.buscar(numero);
    	System.out.println("Saldo atual: " + conta.getSaldo());
    	System.out.println("");
    	System.out.println("Digite o valor que deve ser debitado: ");
    	double valor = ENTRADA.nextDouble();
        if (conta.getNumero() == numero) {
            conta.setSaldo(conta.getSaldo()-valor);
        }
    }

    private String validar(Conta conta) {
		boolean validacaoNumero = conta.validarNumero();
		String erros = "";
        if (validacaoNumero == false) {
            erros+=" Numero inválido, ";
        }
        boolean statusPreenchido = conta.statusPreenchido();
        if (statusPreenchido == false) {
           erros+=" Status inválido, ";
        }
        boolean dataAberturaPreenchido = conta.dataAberturaPreenchido();
        if (dataAberturaPreenchido == false) {
            erros+=" Data de abertura não preenchida, ";
        }
        boolean validarDataAbertura = conta.validarDataAbertura();
        if (validarDataAbertura == false) {
            erros+=" Data de abertura inválida, ";
        }
        return erros;
	}
}
