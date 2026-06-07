package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;

import database.DatabaseConnection;
import model.Product;

public class ProductDAO {

	// Metodo para criar as tabelas
	public void createTable() {
	// 1. Tabela de Marcas (Pai)
		String sqlBrands = "CREATE TABLE IF NOT EXISTS brands(" + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "name TEXT NOT NULL UNIQUE" + ")";

		// 2. Tabela de Produtos (Filho da Marca, Pai do Lote)
		String sqlProducts = "CREATE TABLE IF NOT EXISTS products (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "barcode TEXT UNIQUE NOT NULL, " + "name TEXT NOT NULL, " + "price REAL NOT NULL CHECK (price >=0), "
				+ "brand_id INTEGER NOT NULL, " + "FOREIGN KEY (brand_id) REFERENCES brands(id)" + ")";

		// 3. Tabela de Lotes (Filho do Produto)
		String sqlBatches = "CREATE TABLE IF NOT EXISTS batches (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "product_id INTEGER NOT NULL, " + "purchase_date TEXT NOT NULL, " + "expiration_date TEXT NOT NULL, "
				+ "quantity INTEGER NOT NULL CHECK (quantity >= 0), "
				+ "FOREIGN KEY (product_id) REFERENCES products(id)" + ")";

		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement stmt = conn.createStatement();

			// Executando criação na ordem correta
			stmt.execute(sqlBrands);
			stmt.execute(sqlProducts);
			stmt.execute(sqlBatches);

			System.out.println("Banco de dados estruturado com Marcas, Produtos e Lotes com sucesso!");

		} catch (SQLException e) {
			System.out.println("Erro ao criar as tabelas dados.");
			e.printStackTrace();
		}
	}

	// metodo para inserir dados na tabela product
	public void insertProduct(Product product) {

		String sql = "INSERT INTO products (barcode, name, price, brand_id) VALUES(?,?,?,?)";
		
		
		try {
			Connection conn = DatabaseConnection.getConnection();
		    PreparedStatement pstmt = conn.prepareStatement(sql);
		    
		    
		    pstmt.setString(1, product.getBarcode());
		    pstmt.setString(2, product.getName());
		    pstmt.setDouble(3, product.getPrice());
		    
		    pstmt.setInt(4, product.getBrand().getId());
		    
		    pstmt.executeUpdate();
		    
		    System.out.println("Sucesso: O produto '" + product.getName() + "' foi salvo no estoque!");
		}catch(SQLException e){
			System.out.println("Erro ao tentar salvar o produto no banco.");
			e.printStackTrace();}
	}
			
		
		
		//Metod para buscar e mostrar os dados da tabela
		public void listProducts() {
			String sql = "SELECT p.id, p.barcode, p.name, p.price, b.name AS brand_name "
					+ "FROM products p "
					+ "INNER JOIN brands b ON p.brand_id = b.id";
			
			try {
				Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
					
				while(rs.next()) {
					int id = rs.getInt("id");
					String barcode = rs.getString("barcode");
					String name = rs.getString("name");
					double price = rs.getDouble("price");
					String brandName = rs.getString("brand_name");
					
					System.out.println("ID: " + id 
							+ " | Código: " + barcode 
							+ " | Produto: " + name 
							+ " | Preço: R$ " + price 
							+ " | Marca: " + brandName);
				
					System.out.println("--------------------------------\n");
					
				
					
				}
			}catch(SQLException e){
					System.out.println("Erro ao listar os produtos.");
					e.printStackTrace();
			}
		

		
	
}

		//private Connection DatabaseConnection() {
			//// TODO Auto-generated method stub
			//return null;
	//	}
	}
