package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import entidade.ContaCP;

public class ContaCPDAO extends ContaDAO {

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

					return " Conta: " + nrConta + "\n Agência: " + agencia + "\n Titular: " + titular + "\n Saldo: R$: "
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

				String sql = "SELECT nrConta, agencia, titular, saldo, rendimento, cpf FROM CPcontas;";
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

					vetor.add("\n Conta: " + conta + "\n AgÃªncia: " + agencia + "\n Titular: " + titular
							+ "\n Saldo: R$: " + saldo + "\n Rendimento: R$: " + rendimento + "\n CPF: " + cpf);

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
