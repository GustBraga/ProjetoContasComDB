package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ContaDAO {

	Connection conn = null;

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

}
