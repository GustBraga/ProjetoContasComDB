package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import entidade.ContaCC;

public class ContaDAO {

	Connection conn = null;

	public boolean verificaCpf(String cpf) {

		String aux = cpf.replace(".", "").replace("-", "");
		int cont = 0;

		for (int i = 0; i < aux.length(); i++) {

			if (aux.charAt(i) != '0' || aux.charAt(i) != '1' || aux.charAt(i) != '2' || aux.charAt(i) != '3'
					|| aux.charAt(i) != '4' || aux.charAt(i) != '5' || aux.charAt(i) != '6' || aux.charAt(i) != '7'
					|| aux.charAt(i) != '8' || aux.charAt(i) != '9') {
				
				cont ++;
			}
		}
		
		if (cont == 0) {
			
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

				if (ps.execute()) {

					sql = "use SistemaBancario;";
					PreparedStatement ps2 = conn.prepareStatement(sql);

					if (ps2.execute()) {

						sql = "CREATE TABLE IF NOT EXISTS CCcontas(nrconta varchar(8) not null, agencia varchar(4) not null, titular varchar(30), saldo double not null (valor >= 0) , limite double not null check (valor >= 0), cpf varchar(14) unique );";
						PreparedStatement ps3 = conn.prepareStatement(sql);
						if (ps3.executeUpdate() == 1) {

							this.fecharConexao();
							return "Banco de Dados criado com sucesso!";

						} else {
							this.fecharConexao();
							return "Não foi possivel criar o Banco de Dados!";
						}

					} else {
						this.fecharConexao();
						return "Não foi possivel criar o Banco de Dados!";
					}

				} else {
					this.fecharConexao();
					return "Não foi possivel criar o Banco de Dados!";
				}

			} else {

				return "Não foi possivel realizar a conexão com o Banco de Dados!";
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

				if (ps.execute()) {

					this.fecharConexao();
					return "Banco de Dados deletado com sucesso!";

				} else {

					this.fecharConexao();
					return "Não foi possivel deletar o Banco de Dados!";

				}

			} else {

				return "Não foi possivel realizar a conexão com o Banco de Dados!";

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

				if (rs.getRow() == 0) {

					sql = "INSERT INTO CCcontas(nrConta, agencia, titular, saldo, limite, cpf) VALUES (?,?,?,?,?,?);";

					PreparedStatement ps2 = conn.prepareStatement(sql);

					ps2.setString(1, cc.getNrConta());
					ps2.setString(2, cc.getAgencia());
					ps2.setString(3, cc.getTitular());
					ps2.setDouble(4, cc.getSaldo());
					ps2.setDouble(5, cc.getLimite());
					ps2.setString(6, cc.getCpf());

					if (ps.executeUpdate() == 1) {

						this.fecharConexao();
						return "Conta Cadastrada com sucesso!";
					} else {

						this.fecharConexao();
						return "Não foi possivel cadastrar a sua conta!";

					}
				} else {

					this.fecharConexao();
					return "Cpf já cadastrado!";

				}

			} else {

				return "Não foi possivel realizar a conexão com o Banco de Dados!";

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

				Double saldo = rs.getDouble("saldo");
				Double limite = rs.getDouble("limite");

				if (rs.getRow() == 0) {

					this.fecharConexao();

					return "Não foi possivel acessar o cpf informado, por favor tente novamente!";

				} else {

					if (valorSaque <= (rs.getDouble("saldo") + rs.getDouble("limite"))) {

						double novoSaldo = (rs.getDouble("saldo") - valorSaque);

						sql = "Update CCcontas SET saldo ? where cpf like ?;";

						PreparedStatement ps2 = conn.prepareStatement(sql);
						ps2.setDouble(1, novoSaldo);
						ps2.setString(2, cpf);

						if (ps2.executeUpdate() == 1) {

							this.fecharConexao();
							return "Saque realizado com sucesso! \n Seu novo saldo é R$: " + novoSaldo;

						} else {

							this.fecharConexao();
							return "Não foi possivel realizar o seu saque!";

						}
					} else {

						this.fecharConexao();
						return "O seu limite para saque é R$: " + (saldo + limite);

					}

				}

			} else {

				return "Não foi possivel realizar a conexão com o Banco de Dados!";

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

				if (rs.getRow() > 0) {

					double novoSaldo = valorDeposito + rs.getDouble("saldo");

					sql = "Update CCcontas SET saldo ? where cpf like ?;";

					PreparedStatement ps2 = conn.prepareStatement(sql);
					ps2.setDouble(1, novoSaldo);
					ps2.setString(2, cpf);

					if (ps2.executeUpdate() == 1) {

						this.fecharConexao();
						return "Valor depositado com sucesso, seu novo saldo é R$: " + novoSaldo;

					} else {

						this.fecharConexao();
						return "Não foi possivel realizar o seu deposito!";
					}

				} else {

					this.fecharConexao();
					return "Não foi possivel acessar o cpf informado, por favor tente novamente!";

				}

			} else {

				return "Não foi possivel realizar o conexão com o Banco de Dados!";

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

				String conta = rs.getString("conta");
				String agencia = rs.getString("agencia");
				String titular = rs.getString("titular");
				Double saldo = rs.getDouble("saldo");
				Double limite = rs.getDouble("limite");

				if (rs.getRow() > 0) {

					this.fecharConexao();

					return "Conta: " + conta + "\n Agência: " + agencia + "\n Titular: " + titular + "\n Saldo: R$: "
							+ saldo + "\n Limite: R$: " + limite + "\n CPF: " + cpf;

				} else {

					this.fecharConexao();
					return "Não foi possivel acessar o cpf informado, por favor tente novamente!";

				}

			} else {

				return "Não foi possivel realizar o conexão com o Banco de Dados!";

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

					conta = rs.getString("conta");
					agencia = rs.getString("agencia");
					titular = rs.getString("titular");
					saldo = rs.getDouble("saldo");
					limite = rs.getDouble("limite");
					cpf = rs.getString("cpf");

					vetor.add("Conta: " + conta + "\n Agência: " + agencia + "\n Titular: " + titular + "\n Saldo: R$: "
							+ saldo + "\n Limite: R$: " + limite + "\n CPF: " + cpf + "   ||   ");
				}
				this.fecharConexao();
				return vetor;

			} else {

				vetor.add("Não foi possivel realizar o conexão com o Banco de Dados!");

				return vetor;

			}

		} catch (Exception e) {

			vetor.add(e + "");
			return vetor;
		}
	}
}
