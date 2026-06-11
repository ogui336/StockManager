package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	public static Connection getConnection() {
		String url = "jdbc:sqlite:stockmanager.db";
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection(url);

			try (Statement stmt = connection.createStatement()) {
				stmt.execute("PRAGMA foreign_keys = ON;");

			}
			return connection;

		} catch (ClassNotFoundException e) {
		throw new RuntimeException("Erro crítico: Driver do SQLite não foi encontrado!", e);
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro crítico: Falha ao conectar ao banco de dados stockmanager.db!", e);

		}

	}
}
