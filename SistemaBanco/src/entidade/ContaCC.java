package entidade;

import java.util.ArrayList;

import persistencia.ContaDAO;

public class ContaCC {

	private String nrConta;
	private String agencia;
	private String titular;
	private double saldo;
	private double limite;
	private String cpf;
	
	public ContaCC(String nrConta, String agencia, String titular, double saldo, double limite, String cpf) {
		super();
		this.nrConta = nrConta;
		this.agencia = agencia;
		this.titular = titular;
		this.saldo = saldo;
		this.limite = limite;
		this.cpf = cpf;
	}
	public ContaCC() {
		super();
	}
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
	public double getLimite() {
		return limite;
	}
	public void setLimite(double limite) {
		this.limite = limite;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public boolean verificaCpf(String cpf) {
		return new ContaDAO().verificaCpf(cpf);
	}
	
	public boolean verificaNrConta(String nrConta) {
		return new ContaDAO().verificaNrConta(nrConta);
	}
	
	public boolean verificaAgencia(String agencia) {
		return new ContaDAO().verificaAgencia(agencia);
	}
	
	public boolean verificaTitular(String titular) {
		return new ContaDAO().verificaTitular(titular);
	}
	
	public boolean verificaDigito(double digito) {
		return new ContaDAO().verificaDigito(digito);
	}
	
	public String criarDB() {	
		return new ContaDAO().criarTodoDB();
	}
	
	public String deletarDB() {
		return new ContaDAO().deletarDB();
	}
	
	public String cadastrarCC () {
		return new ContaDAO().cadastrarCC(this);		
	}
	
	public String sacarCC(double valorSaque, String cpf) {	
		
		if (valorSaque > 0 && new ContaDAO().verificaCpf(cpf)) {
			return new ContaDAO().sacarContaCC(valorSaque, cpf);
		} else {
			return "Valor de saque ou/e Cpf invÃ¡lidos!";
		}
		
	}
	
	public String depositarCC(double valorDeposito, String cpf) {
		
		if (new ContaDAO().verificaCpf(cpf) && valorDeposito > 0) {
			return new ContaDAO().depositarContaCC(valorDeposito, cpf);
		}else {
			return "Valor de deposito ou/e Cpf invÃ¡lidos!";
		}
		
	}
	
	public String verificarCC(String cpf) {
		
		if (new ContaDAO().verificaCpf(cpf)) {
			return new ContaDAO().veririficarConta(cpf);
		} else {
			return "Cpf invÃ¡lido!";
		}
		
	}
	
	public ArrayList<String> listarContasCC(){
		return new ContaDAO().listarContasCC();
	}
	
	
}
