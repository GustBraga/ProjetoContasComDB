package entidade;

import java.util.ArrayList;

import persistencia.ContaDAO;

public class ContaCP {

	private String nrConta;
	private String agencia;
	private String titular;
	private double saldo;
	private double rendimento;
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
	public double getRendimento() {
		return rendimento;
	}
	public void setRendimento(double rendimento) {
		this.rendimento = rendimento;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public ContaCP(String nrConta, String agencia, String titular, double saldo, double rendimento, String cpf) {
		super();
		this.nrConta = nrConta;
		this.agencia = agencia;
		this.titular = titular;
		this.saldo = saldo;
		this.rendimento = rendimento;
		this.cpf = cpf;
	}
	public ContaCP() {
		super();
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
	

	public String cadastrarCP(){
		return new ContaDAO().cadastrarCP(this);
	}
	
	public String sacarCP(double valorSaque, String cpf) {	
		
		if (valorSaque > 0 && new ContaDAO().verificaCpf(cpf)) {
			return new ContaDAO().sacarContaCP(valorSaque, cpf);
		} else {
			return "Valor de saque ou/e Cpf inválidos!";
		}
		
	}
	
	public String depositarCP(double valorDeposito, String cpf) {
		
		if (new ContaDAO().verificaCpf(cpf) && valorDeposito > 0) {
			return new ContaDAO().depositarContaCP(valorDeposito, cpf);
		}else {
			return "Valor de deposito ou/e Cpf inválidos!";
		}
		
	}
	
	public String verificarCP(String cpf) {
		
		if (new ContaDAO().verificaCpf(cpf)) {
			return new ContaDAO().veririficarContaCP(cpf);
		} else {
			return "Cpf inválido!";
		}
		
	}
	
	public ArrayList<String> listarContasCP(){
		return new ContaDAO().listarContasCP();
	}
}
