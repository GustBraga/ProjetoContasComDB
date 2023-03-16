package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import entidade.ContaCC;
import entidade.ContaCP;

public class ContaDAO {

	Connection conn = null;

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

	public boolean conectarGeral() {

		try {

			String driver = "org.mariadb.jdbc.Driver";
			Class.forName(driver);

			String url = "jdbc:mariadb://localhost:3306/";
			String user = "root";
			String password = "";

			conn = DriverManager.getConnection(url, user, password);

			return true;

		} catch (Exception e) {

			return false;

		}

	}

	public boolean conectarDB() {

		try {

			String driver = "org.mariadb.jdbc.Driver";
			Class.forName(driver);

			String url = "jdbc:mariadb://localhost:3306/SistemaBancario";
			String user = "root";
			String password = "";

			conn = DriverManager.getConnection(url, user, password);

			return true;

		} catch (Exception e) {

			return false;

		}
	}

	public boolean fecharConexao() {

		try {

			conn.close();

			return true;

		} catch (Exception e) {

			return false;

		}

	}

	public String criarTodoDB() {

		try {

			if (this.conectarGeral()) {

				String sql = "CREATE DATABASE IF NOT EXISTS SistemaBancario;";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.execute();

				sql = "use SistemaBancario;";
				ps = conn.prepareStatement(sql);
				ps.execute();

				sql = "CREATE TABLE IF NOT EXISTS CCcontas(nrconta varchar(8) not null, agencia varchar(4) not null, titular varchar(30), saldo double not null, limite double not null, cpf varchar(14) unique );";
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				
				sql = "CREATE TABLE IF NOT EXISTS CPcontas(nrconta varchar(8) not null, agencia varchar(4) not null, titular varchar(30), saldo double not null, rendimento double not null, cpf varchar(14) unique );";
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();

				this.fecharConexao();
				return "Banco de Dados criado com sucesso!";

			} else {

				return "NÃ£o foi possivel realizar a conexÃ£o com o Banco de Dados!";
			}

		} catch (Exception e) {
			return "" + e;
		}

	}

	public String deletarDB() {

		try {

			if (this.conectarGeral()) {

				String sql = "DROP DATABASE IF EXISTS SistemaBancario;";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.execute();

				this.fecharConexao();
				return "Banco de Dados deletado com sucesso!";

			} else {

				return "NÃ£o foi possivel realizar a conexÃ£o com o Banco de Dados!";

			}

		} catch (Exception e) {
			return e + "";
		}

	}

	public String cadastrarCC(ContaCC cc) {

		try {

			if (this.conectarDB()) {

				String sql = "SELECT cpf FROM CCcontas WHERE cpf like ?;";
				PreparedStatement ps = conn.prepareStatement(sql);

				ps.setString(1, cc.getCpf());

				ResultSet rs = ps.executeQuery();

				if (rs.next() == false) {

					ps = conn.prepareStatement(
							"INSERT INTO CCcontas(nrConta, agencia, titular, saldo, limite, cpf) VALUES (?,?,?,?,?,?);");

					ps.setString(1, cc.getNrConta());
					ps.setString(2, cc.getAgencia());
					ps.setString(3, cc.getTitular());
					ps.setDouble(4, cc.getSaldo());
					ps.setDouble(5, cc.getLimite());
					ps.setString(6, cc.getCpf());

					if (ps.executeUpdate() == 1) {
						this.fecharConexao();
						return "Conta Cadastrada com sucesso!";

					} else {
						return "Não foi possivel Cadastrar sua conta!";
					}
				} else {

					this.fecharConexao();
					return "Cpf jÃ¡ cadastrado!";
				}

			} else {

				return "NÃ£o foi possivel realizar a conexÃ£o com o Banco de Dados!";

			}

		} catch (Exception e) {

			return e + "";
		}

	}

	public String sacarContaCC(double valorSaque, String cpf) {

		try {
			if (this.conectarDB()) {

				String sql = "SELECT saldo, limite, cpf FROM CCcontas WHERE cpf like ?;";
				PreparedStatement ps = conn.prepareStatement(sql);

				ps.setString(1, cpf);

				ResultSet rs = ps.executeQuery();

				double saldo;
				double limite;
				double novoSaldo;

				if (rs.next()) {

					saldo = rs.getDouble("saldo");
					limite = rs.getDouble("limite");
					
					if (valorSaque <= (saldo + limite)) {

						novoSaldo = (saldo - valorSaque);

						sql = "Update CCcontas SET saldo = ? where cpf like ?;";

						ps = conn.prepareStatement(sql);
						ps.setDouble(1, novoSaldo);
						ps.setString(2, cpf);

						if (ps.executeUpdate() == 1) {

							this.fecharConexao();
							return "Saque realizado com sucesso, Seu novo saldo Ã© R$: " + novoSaldo;

						} else {

							this.fecharConexao();
							return "NÃ£o foi possivel realizar o seu saque!";

						}
					} else {

						this.fecharConexao();
						return "O seu limite para saque Ã© R$: " + (saldo + limite);

					}

				} else {
					this.fecharConexao();
					return "Cpf não cadastrado!";
				}
			} else {

				return "NÃ£o foi possivel realizar a conexÃ£o com o Banco de Dados!";

			}

		} catch (Exception e) {

			return e + "";

		}

	}

	public String depositarContaCC(double valorDeposito, String cpf) {

		try {

			if (this.conectarDB()) {

				String sql = "SELECT saldo, cpf FROM CCcontas WHERE cpf like ?;";
				PreparedStatement ps = conn.prepareStatement(sql);

				ps.setString(1, cpf);

				ResultSet rs = ps.executeQuery();
				
				double novoSaldo = valorDeposito;

				if (rs.next()) {

					novoSaldo = novoSaldo + rs.getDouble("saldo");

					sql = "UPDATE CCcontas set SALDO = ? WHERE cpf like ?;";

					ps = conn.prepareStatement(sql);
					ps.setDouble(1, novoSaldo);
					ps.setString(2, cpf);

					if (ps.executeUpdate() == 1) {

						this.fecharConexao();
						return "Valor depositado com sucesso, seu novo saldo Ã© R$: " + novoSaldo;

					} else {

						this.fecharConexao();
						return "NÃ£o foi possivel realizar o seu deposito!";
					}

				} else {

					this.fecharConexao();
					return "NÃ£o foi possivel acessar o cpf informado, por favor tente novamente!";

				}

			} else {

				return "NÃ£o foi possivel realizar o conexÃ£o com o Banco de Dados!";

			}

		} catch (Exception e) {

			return e + "";

		}

	}

	public String veririficarConta(String cpf) {

		try {

			if (this.conectarDB()) {

				String sql = "SELECT nrConta, agencia, titular, saldo, limite, cpf FROM CCcontas WHERE cpf like ?;";
				PreparedStatement ps = conn.prepareStatement(sql);

				ps.setString(1, cpf);

				ResultSet rs = ps.executeQuery();

				String nrConta;
				String agencia;
				String titular;
				Double saldo;
				Double limite;

				if (rs.next()) {
					
					nrConta = rs.getString("nrConta");
					agencia = rs.getString("agencia");
					titular = rs.getString("titular");
					saldo = rs.getDouble("saldo");
					limite = rs.getDouble("limite");

					this.fecharConexao();
					
					 return " Conta: " + nrConta + "\n Agência: " + agencia + "\n Titular: " +
					 titular + "\n Saldo: R$: "
					 + saldo + "\n Limite: R$: " + limite + "\n CPF: " + cpf;

				} else {

					this.fecharConexao();
					return "NÃ£o foi possivel acessar o cpf informado, por favor tente novamente!";

				}

			} else {

				return "NÃ£o foi possivel realizar o conexÃ£o com o Banco de Dados!";

			}

		} catch (Exception e) {

			return e + "";

		}

	}

	public ArrayList<String> listarContasCC() {

		ArrayList<String> vetor = new ArrayList<String>();

		try {

			if (this.conectarDB()) {

				String sql = "SELECT nrConta, agencia, titular, saldo, limite, cpf FROM CCcontas;";
				PreparedStatement ps = conn.prepareStatement(sql);

				ResultSet rs = ps.executeQuery();

				String conta;
				String agencia;
				String titular;
				Double saldo;
				Double limite;
				String cpf;

				while (rs.next()) {

					conta = rs.getString("nrConta");
					agencia = rs.getString("agencia");
					titular = rs.getString("titular");
					saldo = rs.getDouble("saldo");
					limite = rs.getDouble("limite");
					cpf = rs.getString("cpf");

					vetor.add("\nConta: " + conta + "\n AgÃªncia: " + agencia + "\n Titular: " + titular
							+ "\n Saldo: R$: " + saldo + "\n Limite: R$: " + limite + "\n CPF: " + cpf + "\n\n\n");
					
					
				}
				this.fecharConexao();
				return vetor;
			} else {

				vetor.add("NÃ£o foi possivel realizar o conexÃ£o com o Banco de Dados!");
				
				return vetor;
			}

		} catch (Exception e) {

			vetor.add(e + "");
			return vetor;
		}
	}
	
	public String cadastrarCP(ContaCP cp) {

		try {

			if (this.conectarDB()) {

				String sql = "SELECT cpf FROM CPcontas WHERE cpf like ?;";
				PreparedStatement ps = conn.prepareStatement(sql);

				ps.setString(1, cp.getCpf());

				ResultSet rs = ps.executeQuery();

				if (rs.next() == false) {

					ps = conn.prepareStatement(
							"INSERT INTO CPcontas(nrConta, agencia, titular, saldo, rendimento, cpf) VALUES (?,?,?,?,?,?);");

					ps.setString(1, cp.getNrConta());
					ps.setString(2, cp.getAgencia());
					ps.setString(3, cp.getTitular());
					ps.setDouble(4, cp.getSaldo());
					ps.setDouble(5, cp.getRendimento());
					ps.setString(6, cp.getCpf());

					if (ps.executeUpdate() == 1) {
						this.fecharConexao();
						return "Conta Cadastrada com sucesso!";

					} else {
						return "Não foi possivel Cadastrar sua conta!";
					}
				} else {

					this.fecharConexao();
					return "Cpf jÃ¡ cadastrado!";
				}

			} else {

				return "NÃ£o foi possivel realizar a conexÃ£o com o Banco de Dados!";

			}

		} catch (Exception e) {

			return e + "";
		}

	}
	
	public String sacarContaCP(double valorSaque, String cpf) {

		try {
			if (this.conectarDB()) {

				String sql = "SELECT saldo, cpf FROM CPcontas WHERE cpf like ?;";
				PreparedStatement ps = conn.prepareStatement(sql);

				ps.setString(1, cpf);

				ResultSet rs = ps.executeQuery();

				double saldo;
				double novoSaldo;

				if (rs.next()) {

					saldo = rs.getDouble("saldo");
					
					if (valorSaque <= saldo) {

						novoSaldo = (saldo - valorSaque);

						sql = "Update CPcontas SET saldo = ? where cpf like ?;";

						ps = conn.prepareStatement(sql);
						ps.setDouble(1, novoSaldo);
						ps.setString(2, cpf);

						if (ps.executeUpdate() == 1) {

							this.fecharConexao();
							return "Saque realizado com sucesso, Seu novo saldo Ã© R$: " + novoSaldo;

						} else {

							this.fecharConexao();
							return "NÃ£o foi possivel realizar o seu saque!";

						}
					} else {

						this.fecharConexao();
						return "O seu limite para saque Ã© R$: " + saldo;

					}

				} else {
					this.fecharConexao();
					return "Cpf não cadastrado!";
				}
			} else {

				return "NÃ£o foi possivel realizar a conexÃ£o com o Banco de Dados!";

			}

		} catch (Exception e) {

			return e + "";

		}

	}
	
	public String depositarContaCP(double valorDeposito, String cpf) {

		try {

			if (this.conectarDB()) {

				String sql = "SELECT saldo, cpf FROM CPcontas WHERE cpf like ?;";
				PreparedStatement ps = conn.prepareStatement(sql);

				ps.setString(1, cpf);

				ResultSet rs = ps.executeQuery();
				
				double novoSaldo = valorDeposito;

				if (rs.next()) {

					novoSaldo = novoSaldo + rs.getDouble("saldo");

					sql = "UPDATE CPcontas set SALDO = ? WHERE cpf like ?;";

					ps = conn.prepareStatement(sql);
					ps.setDouble(1, novoSaldo);
					ps.setString(2, cpf);

					if (ps.executeUpdate() == 1) {

						this.fecharConexao();
						return "Valor depositado com sucesso, seu novo saldo Ã© R$: " + novoSaldo;

					} else {

						this.fecharConexao();
						return "NÃ£o foi possivel realizar o seu deposito!";
					}

				} else {

					this.fecharConexao();
					return "NÃ£o foi possivel acessar o cpf informado, por favor tente novamente!";

				}

			} else {

				return "NÃ£o foi possivel realizar o conexÃ£o com o Banco de Dados!";

			}

		} catch (Exception e) {

			return e + "";

		}

	}
	
	public String veririficarContaCP(String cpf) {

		try {

			if (this.conectarDB()) {

				String sql = "SELECT nrConta, agencia, titular, saldo, rendimento, cpf FROM CPcontas WHERE cpf like ?;";
				PreparedStatement ps = conn.prepareStatement(sql);

				ps.setString(1, cpf);

				ResultSet rs = ps.executeQuery();

				String nrConta;
				String agencia;
				String titular;
				Double saldo;
				Double rendimento;

				if (rs.next()) {
					
					nrConta = rs.getString("nrConta");
					agencia = rs.getString("agencia");
					titular = rs.getString("titular");
					saldo = rs.getDouble("saldo");
					rendimento = rs.getDouble("rendimento");

					this.fecharConexao();
					
					 return " Conta: " + nrConta + "\n Agência: " + agencia + "\n Titular: " +
					 titular + "\n Saldo: R$: "
					 + saldo + "\n Rendimento: " + rendimento + "%\n CPF: " + cpf;

				} else {

					this.fecharConexao();
					return "NÃ£o foi possivel acessar o cpf informado, por favor tente novamente!";

				}

			} else {

				return "NÃ£o foi possivel realizar o conexÃ£o com o Banco de Dados!";

			}

		} catch (Exception e) {

			return e + "";

		}

	}

	public ArrayList<String> listarContasCP() {

		ArrayList<String> vetor = new ArrayList<String>();

		try {

			if (this.conectarDB()) {

				String sql = "SELECT nrConta, agencia, titular, saldo, rendimento, cpf FROM CCcontas;";
				PreparedStatement ps = conn.prepareStatement(sql);

				ResultSet rs = ps.executeQuery();

				String conta;
				String agencia;
				String titular;
				Double saldo;
				Double rendimento;
				String cpf;

				while (rs.next()) {

					conta = rs.getString("nrConta");
					agencia = rs.getString("agencia");
					titular = rs.getString("titular");
					saldo = rs.getDouble("saldo");
					rendimento = rs.getDouble("rendimento");
					cpf = rs.getString("cpf");

					vetor.add("\nConta: " + conta + "\n AgÃªncia: " + agencia + "\n Titular: " + titular
							+ "\n Saldo: R$: " + saldo + "\n Rendimento: R$: " + rendimento + "\n CPF: " + cpf + "\n\n\n");
					
					
				}
				this.fecharConexao();
				return vetor;
			} else {

				vetor.add("NÃ£o foi possivel realizar o conexÃ£o com o Banco de Dados!");
				
				return vetor;
			}

		} catch (Exception e) {

			vetor.add(e + "");
			return vetor;
		}
	}

}
