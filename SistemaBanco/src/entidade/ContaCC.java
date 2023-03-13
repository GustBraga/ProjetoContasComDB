package entidade;

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
	
	
	
}
