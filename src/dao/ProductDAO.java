package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import database.DatabaseConnection;

public class ProductDAO {

	public void createTable() {
		// 1. Tabela de Marcas (Pai)
		String sqlBrands = "CREATE TABLE IF NOT EXISTS brands("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "name TEXT NOT NULL UNIQUE"
				+")";
		
		// 2. Tabela de Produtos (Filho da Marca, Pai do Lote)
		String sqlProducts = "CREATE TABLE IF NOT EXISTS products ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "barcode TEXT UNIQUE NOT NULL, "
				+ "name TEXT NOT NULL, "
				+ "price REAL NOT NULL CHECK (price >=0), "
				+ "brand_id INTEGER NOT NULL, "
				+ "FOREIGN KEY (brand_id) REFERENCES brands(id)"
				+")";
		
		// 3. Tabela de Lotes (Filho do Produto)
		String sqlBatches = "CREATE TABLE IF NOT EXISTS batches ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "product_id INTEGER NOT NULL, "
				+ "purchase_date TEXT NOT NULL, "
				+ "expiration_date TEXT NOT NULL, "
				+ "quantity INTEGER NOT NULL CHECK (quantity >= 0), "
				+ "FOREIGN KEY (product_id) REFERENCES products(id)"
				+ ")";
		
		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();
			
			//Executando criação na ordem correta 
			stmt.execute(sqlBrands);
			stmt.execute(sqlProducts);
			stmt.execute(sqlBatches);
			
			System.out.println("Banco de dados estruturado com Marcas, Produtos e Lotes com sucesso!");
			
		
		}catch(SQLException e) {
			System.out.println("Erro ao criar as tabelas dados.");
			e.printStackTrace();
		}
	}
}
