package br.com.cesarschool.poo.geral;
import java.util.Scanner;

public class TelaConta {
    private static final int COMANDO_INIC = -1;
    private static final Scanner ENTRADA = new Scanner(System.in);
    private RepositorioConta repositorioConta = new RepositorioConta();

    
    public void executaTelaConta() {
        while (true) {
            long comand = COMANDO_INIC;
            printMenu();
            int call = ENTRADA.nextInt();
            if (call == 1) {
                processaIncluir();
            } else if (call == 2) {
                processaAlterar();
            } else if (call == 3) {
                processaEncerrar();
            } else if (call == 4) {
                processaBloquear();
            } else if (call == 5) {
                processarDesbloquear();
            } else if (call == 6) {
                processaExcluir();
            } else if (call == 7) {
                processarBuscar();
            } else if (call == 8) {
                processaCreditar();
            } else if (call == 9) {
                processarDebitar();
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
		Conta conta = capturaProduto(COMANDO_INIC);
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
    private void processaAlterar (long codigo) {
		Conta conta = capturaConta(codigo);
		String retornoValidacao = validar(conta);
		if (retornoValidacao == null) {
			boolean retornoRepositorio = repositorioConta.alterar(conta);
			if (retornoRepositorio) {
				System.out.println("Conta alterado com sucesso!");
			} else {
				System.out.println("Erro na altera��o de produto!");
			}
		} else {
			System.out.println(retornoValidacao);
		}		
	}

    private Produto capturaConta(long codigoDaAlteracao) {
		long numero; 
		if (codigoDaAlteracao == COMANDO_INIC) {
			System.out.print("Digite o numero: ");
			numero = ENTRADA.nextLong();			
		} else {
			numero = codigoDaAlteracao;
		}
        System.out.print("Digite sua data de Abertura: ");
        LocalDate dataAbertura = ENTRADA.nextLong();   //nao temos certeza do nextlong
        System.out.print("Digite o Status da conta (1, 2 ou 3): ");
        int statusTipo = ENTRADA.nextInt();
        TipoStatus status = TipoStatus.obterPorCodigo(statusTipo);
        return new conta(numero, statusTipo, dataAbertura);
    }

    //encerrar (altera o status, contas já encerradas não podem ser encerradas novamente)
    public void processaEncerrar(Conta conta) {
        if (conta.getStatus() != ENCERRADA) {
            conta.setStatus(ENCERRADA);
        }
    }
    
    //bloquear (altera o status, contas encerradas ou já bloqueadas não podem ser bloqueadas)
    public void processaBloquear(Conta conta) {
        if (conta.getStatus() != ENCERRADA && conta.getStats() != BLOQUEADA) {
            conta.setStatus(BLOQUEADA);
        }
    }
    
    //desbloquear (altera o status, contas encerradas ou já bloqueadas não podem ser bloqueadas)
    public void processaDesbloquear(Conta conta) {
        if (conta.getStats() == BLOQUEADA) {
            conta.setStatus(ATIVA);
        }
    }
     
    //excluir
    private void processaExclusao(long numero) {
		boolean retornoRepositorio = repositorioConta.excluir(numero);
		if (retornoRepositorio) {
			System.out.println("Produto excluído com sucesso!");
		} else {
			System.out.println("Erro na exclusão de produto!");
		}
	}
    
    //buscar (mostrar os atributos e o escore) 
    private long processaBusca() {
		System.out.print("Digite o numero: ");
		long numero = ENTRADA.nextLong();
		Conta conta = repositorioConta.buscar(conta);
		if (conta == null) {
			System.out.println("Conta não encontrado!");
			return COMANDO_INIC;
		} else {
			System.out.println("Numero: " + conta.getNumero());
			System.out.println("Data de abertura: " + conta.getDataAbertura());
			System.out.println("Saldo: " + conta.getSaldo());
			System.out.println("Status: " + conta.getCodigo().getDescricao());
			return numero;
		}
	}
    
    //creditar ---> chamada de metodos correspondentes na atualização do saldo 
    public void processaCreditar(Conta conta, long numero, double valor) {
        if (conta.getNumero() == numero) {
            creditar(valor);
        }
    }

    //debitar ---> chamada de metodos correspondentes na atualização do saldo
    public void processaDebitar(Conta conta, long numero, double valor) {
        double value = ENTRADA.nextDouble();
        if (conta.getNumero() == numero) {
            debitar(valor);
        }
    }

    private String validar(Conta conta) {
		boolean validacaoNumero = conta.validarNumero();
        if (validacaoNumero == false) {
            System.out.println("Numero inválido");
        }
        boolean statusPreenchido = conta.statusPreenchido();
        if (statusPreenchido == false) {
            System.out.println("Status inválido");
        }
        boolean dataAberturaPreenchido = conta.dataAberturaPreenchido();
        if (dataAberturaPreenchido == false) {
            System.out.println("Data de abertura não preenchida");
        }
        boolean validarDataAbertura = conta.validarDataAbertura();
        if (validarDataAbertura == false) {
            System.out.println("Data de abertura inválida");
        }
        
	}
}
