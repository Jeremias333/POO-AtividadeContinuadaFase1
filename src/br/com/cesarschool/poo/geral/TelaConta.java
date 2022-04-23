package br.com.cesarschool.poo.geral;
import java.util.Scanner;

public class TelaConta {
    private static final int COMANDO_INIC = -1;
    private static final Scanner ENTRADA = new Scanner(System.in);

    
    public void executaTelaConta {
        while (true) {
            long comand = COMANDO_INIC;
            printMenu();
            int call = ENTRADA.nextint();
            if (call == 1) {
                processaIncluir();
            } else if (call == 2) {
                alterar();
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
                processaSair();
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
    

    //incluir //elder fez
    public boolean incluir(Conta conta) {
		if (buscarIndice(conta.getNumero()) != -1) {
			return false;
		} else if (tamanhoAtual == TAMANHO_MAX_CONTA - 1) {
			return false;
		} else {
			for (int i = 0; i < cadastroConta.length; i++) {
				if (cadastroConta[i] == null) {
					cadastroConta[i] = conta;
					break;
				}
			}
			tamanhoAtual++; 
			return true; 
		}
	}
    
    //alterar (só pode alterar a data de abertura) //elder fez

	public boolean processoAlterar(Conta conta) {
		int indice = buscarIndice(conta.getNumero()); 
		if (indice == -1) {
			return false;
		} else {
			cadastroConta[indice] = conta;
			return true; 
		}
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
    
    
    
    
    //excluir //elder fez
    public boolean excluir(long numero) {
		int indice = buscarIndice(numero);
		if (indice == -1) {
			return false;
		} else {
			cadastroConta[indice] = null;
			tamanhoAtual--;
			return true;
		}		
	}
    
    //buscar (mostrar os atributos e o escore) //elder fez
    
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

}


