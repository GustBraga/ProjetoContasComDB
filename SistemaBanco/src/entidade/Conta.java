package entidade;

import persistencia.ContaDAO;

public class Conta {
	private String nrConta;
	private String agencia;
	private String titular;
	private double saldo;
	private String cpf;

	public String getNrConta() {
		return nrConta;
	}

	public void setNrConta(String nrConta) {
		this.nrConta = nrConta;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Conta(String nrConta, String agencia, String titular, double saldo, String cpf) {
		super();
		this.nrConta = nrConta;
		this.agencia = agencia;
		this.titular = titular;
		this.saldo = saldo;
		this.cpf = cpf;
	}

	public Conta() {
		super();
	}

	public String criarDB() {
		return new ContaDAO().criarTodoDB();
	}

	public String deletarDB() {
		return new ContaDAO().deletarDB();
	}

	public boolean verificaCpf(String cpf) {

		String aux = cpf.replace(".", "").replace("-", "").trim();
		int cont = 0;

		if (cpf.length() < 14 || cpf.length() > 14) {
			return false;
		}

		if (cpf.charAt(3) != '.' || cpf.charAt(7) != '.' || cpf.charAt(11) != '-') {
			return false;
		}

		for (int i = 0; i < aux.length(); i++) {

			if (aux.charAt(i) != '0' || aux.charAt(i) != '1' || aux.charAt(i) != '2' || aux.charAt(i) != '3'
					|| aux.charAt(i) != '4' || aux.charAt(i) != '5' || aux.charAt(i) != '6' || aux.charAt(i) != '7'
					|| aux.charAt(i) != '8' || aux.charAt(i) != '9') {

				cont++;
			}
		}

		if (cont == aux.length()) {

			return true;

		} else {
			return false;
		}
	}

	public boolean verificaNrConta(String nrConta) {

		String aux = nrConta.replace("-", "").trim();
		int cont = 0;

		if (nrConta.trim().length() < 8 || nrConta.trim().length() > 8) {
			return false;
		}

		if (nrConta.charAt(6) != '-') {
			return false;
		}

		for (int i = 0; i < aux.length(); i++) {
			if (aux.charAt(i) != '0' || aux.charAt(i) != '1' || aux.charAt(i) != '2' || aux.charAt(i) != '3'
					|| aux.charAt(i) != '4' || aux.charAt(i) != '5' || aux.charAt(i) != '6' || aux.charAt(i) != '7'
					|| aux.charAt(i) != '8' || aux.charAt(i) != '9') {
				cont++;
			}
		}

		if (cont == aux.length()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean verificaAgencia(String agencia) {

		String aux = agencia.trim();
		int cont = 0;

		if (agencia.length() < 4 || agencia.length() > 4) {
			return false;
		}

		for (int i = 0; i < aux.length(); i++) {
			if (aux.charAt(i) != '0' || aux.charAt(i) != '1' || aux.charAt(i) != '2' || aux.charAt(i) != '3'
					|| aux.charAt(i) != '4' || aux.charAt(i) != '5' || aux.charAt(i) != '6' || aux.charAt(i) != '7'
					|| aux.charAt(i) != '8' || aux.charAt(i) != '9') {
				cont++;
			}
		}

		if (cont == aux.length()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean verificaTitular(String titular) {

		char letras[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
				'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' ' };

		int cont = 0;

		if (titular.length() > 30) {

			return false;
		}

		for (int i = 0; i < titular.length(); i++) {
			for (int j = 0; j < letras.length; j++) {

				if (titular.charAt(i) == letras[j]) {
					cont++;
				}

			}

		}

		if (cont == titular.length()) {
			return true;
		} else {
			return false;
		}

	}

	public boolean verificaDigito(double digito) {

		String aux = Double.toString(digito).trim();
		int cont = 0;
		int contPonto = 0;

		for (int i = 0; i < aux.length(); i++) {
			if (aux.charAt(i) != '0' || aux.charAt(i) != '1' || aux.charAt(i) != '2' || aux.charAt(i) != '3'
					|| aux.charAt(i) != '4' || aux.charAt(i) != '5' || aux.charAt(i) != '6' || aux.charAt(i) != '7'
					|| aux.charAt(i) != '8' || aux.charAt(i) != '9' || aux.charAt(i) == '.') {
				cont++;
			}
			if (aux.charAt(i) == '.') {
				contPonto++;
			}
		}

		if (cont == aux.length() && (contPonto == 1 || contPonto == 0)) {
			return true;
		} else {
			return false;
		}

	}

}
