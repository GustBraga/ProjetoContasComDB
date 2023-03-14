package apresentacao;

import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {

		Scanner ler = new Scanner(System.in);
		String op;

		do {

			System.out.println("Menu");
			System.out.println("1 - Para CADASTRAR Conta Corrente");
			System.out.println("2 - Para CADASTRAR Conta Poupança");
			System.out.println("3 - EXIBIR os Dados de Conta Corrente");
			System.out.println("4 - EXIBIR os Dados de Conta Poupança");
			System.out.println("5 - DEPOSITAR em Conta Corrente");
			System.out.println("6 - DEPOSITAR em Conta Poupança");
			System.out.println("7 - SACAR em Conta Corrente");
			System.out.println("8 - SACAR em Conta Poupança");
			System.out.println("9 – Lista TODAS as Conta Correntes cadastradas");
			System.out.println("10 - Lista TODAS as Conta Poupanças cadastradas");
			System.out.println("Modo ADMIN");
			System.out.println("20 – Criar BD e tabelas");
			System.out.println("21 – Deletar BD");
			System.out.println("s   – Sair");
			System.out.print("Escolha uma opção: ");

			op = ler.nextLine();

			switch (op) {
			case "1": {

				System.out.print("Digite o Número da sua conta(Ex: 552018-0): ");
				String nrConta = ler.nextLine();
				if (nrConta.charAt(6) != '-') {
					System.out.println("Valor de conta inválido!");
					break;
				}
				System.out.println();
				
				break;
			}

			case "2": {

				break;
			}
			
			case "3": {

				break;
			}
			
			case "4": {

				break;
			}
			
			case "5": {

				break;
			}
			
			case "6": {

				break;
			}
			
			case "7": {

				break;
			}
			
			case "8": {

				break;
			}
			
			case "9": {

				break;
			}
			
			case "10": {

				break;
			}
			
			case "20": {

				break;
			}
			
			case "21": {

				break;
			}
			
			case "s": {
				System.out.println("Você escolheu sair!");
				break;
			}
			
			case "S": {
				System.out.println("Você escolheu sair!");
				break;
			}
			
			default: {
				System.out.println("Opção inválida!");
				break;
			}}
			
		} while (!op.equalsIgnoreCase("s"));

		ler.close();
	}

}
