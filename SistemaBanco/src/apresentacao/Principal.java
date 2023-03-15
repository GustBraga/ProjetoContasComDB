package apresentacao;

import java.util.Scanner;

import entidade.ContaCC;

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

				System.out.print("Digite o Número da sua Conta(Ex: 552018-0): ");
				String nrConta = ler.nextLine().trim();
				if (nrConta.isEmpty() || new ContaCC().verificaNrConta(nrConta) == false) {
					System.out.println("Número de conta inválido!");
					break;
				} else {
					System.out.print("Digite a sua Agência(Ex: 4605): ");
					String agencia = ler.nextLine().trim();
					if (agencia.isEmpty() || new ContaCC().verificaAgencia(agencia) == false) {
						System.out.println("Agência inválida! ");
						break;
					} else {
						System.out.print("Digite o nome do Titular: ");
						String titular = ler.nextLine().trim().replace("  ", " ");
						if (titular.isEmpty() || new ContaCC().verificaTitular(titular) == false) {
							System.out.println("Nome do Titular inválido!");
							break;
						} else {
							System.out.print("Digite o valor do seu Saldo: ");
							double saldo = Double.parseDouble(ler.nextLine());
							if (saldo < 0 || new ContaCC().verificaDigito(saldo) == false || Double.toString(saldo).charAt(0) == '.') {
								System.out.println("Valor de Saldo inválido!");
								break;
							} else {
								System.out.print("Digite o valor do seu Limite: ");
								double limite = Double.parseDouble(ler.nextLine());
								if (limite < 0 || new ContaCC().verificaDigito(limite) == false || Double.toString(saldo).charAt(0) == '.') {
									System.out.println("Valor de Saldo inválido!");
									break;
								} else {
									System.out.print("Digite o seu Cpf(Ex: 115.977.897-39): ");
									String cpf = ler.nextLine().trim();
									if (cpf.isEmpty() || new ContaCC().verificaCpf(cpf) == false) {
										System.out.println("Cpf inválido!");
										break;
									} else {
										
										ContaCC cc = new ContaCC(nrConta, agencia, titular, saldo, limite, cpf);
										
										System.out.println(cc.cadastrarCC());

										break;
									}
								}
							}
						}
					}
				}
			}

			case "2": {

				break;
			}

			case "3": {

				System.out.print("Digite o seu Cpf para exibir dados(Ex: 115.977.897-39): ");
				String cpf = ler.nextLine().trim();
				if (cpf.isEmpty() || new ContaCC().verificaCpf(cpf) == false) {
					System.out.println("Cpf inválido!");
					break;
				}

				System.out.println(new ContaCC().verificarCC(cpf));

				break;
			}

			case "4": {

				break;
			}

			case "5": {

				System.out.print("Digite o valor do Deposito: ");
				double valorDeposito = Double.parseDouble(ler.nextLine().trim());
				if (valorDeposito < 0) {
					System.out.println("Valor de Deposito inválido!");
					break;
				}
				System.out.print("Digite o Cpf paara Deposito(Ex: 115.977.897-39): ");
				String cpf = ler.nextLine();
				if (cpf.isEmpty() || new ContaCC().verificaCpf(cpf) == false) {
					System.out.println("Cpf inválido!");
					break;
				}

				System.out.println(new ContaCC().depositarCC(valorDeposito, cpf));

				break;
			}

			case "6": {

				break;
			}

			case "7": {

				System.out.print("Digite o valor do seu Saque: ");
				double valorSaque = Double.parseDouble(ler.nextLine().trim());
				if (valorSaque < 0) {
					System.out.println("Valor de Saque inválido!");
					break;
				}
				System.out.print("Digite o Cpf paara Deposito(Ex: 115.977.897-39): ");
				String cpf = ler.nextLine();
				if (cpf.isEmpty() || new ContaCC().verificaCpf(cpf) == false) {
					System.out.println("Cpf inválido!");
					break;
				}

				System.out.println(new ContaCC().sacarCC(valorSaque, cpf));

				break;
			}

			case "8": {

				break;
			}

			case "9": {

				System.out.println(new ContaCC().listarContasCC());

				break;
			}

			case "10": {

				break;
			}

			case "20": {

				System.out.println(new ContaCC().criarDB());

				break;
			}

			case "21": {

				System.out.println(new ContaCC().deletarDB());

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
			}
			}

		} while (!op.equalsIgnoreCase("s"));

		ler.close();
	}

}
