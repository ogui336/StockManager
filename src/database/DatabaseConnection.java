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
			System.out.println("Erro: O arquivo .jar do SQLite não foi encontrado no Classpath!");
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			System.out.println("Erro ao tentar conectar com o banco de dados!");
			e.printStackTrace();
			return null;

		}

	}
}
