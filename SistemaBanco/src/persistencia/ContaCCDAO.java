package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import entidade.ContaCC;

public class ContaCCDAO extends ContaDAO {

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
						return "N�o foi possivel Cadastrar sua conta!";
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
							return "Saque realizado com sucesso, Seu novo saldo é R$: " + novoSaldo;

						} else {

							this.fecharConexao();
							return "Não foi possivel realizar o seu saque!";

						}
					} else {

						this.fecharConexao();
						return "O seu limite para saque é R$: " + (saldo + limite);

					}

				} else {
					this.fecharConexao();
					return "Cpf n�o cadastrado!";
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

				double novoSaldo = valorDeposito;

				if (rs.next()) {

					novoSaldo = novoSaldo + rs.getDouble("saldo");

					sql = "UPDATE CCcontas set SALDO = ? WHERE cpf like ?;";

					ps = conn.prepareStatement(sql);
					ps.setDouble(1, novoSaldo);
					ps.setString(2, cpf);

					if (ps.executeUpdate() == 1) {

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

					return " Conta: " + nrConta + "\n Ag�ncia: " + agencia + "\n Titular: " + titular + "\n Saldo: R$: "
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

					conta = rs.getString("nrConta");
					agencia = rs.getString("agencia");
					titular = rs.getString("titular");
					saldo = rs.getDouble("saldo");
					limite = rs.getDouble("limite");
					cpf = rs.getString("cpf");

					vetor.add("\n Conta: " + conta + "\n Agência: " + agencia + "\n Titular: " + titular
							+ "\n Saldo: R$: " + saldo + "\n Limite: R$: " + limite + "\n CPF: " + cpf);

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
