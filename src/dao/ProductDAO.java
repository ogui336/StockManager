package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import model.Brand;
import model.Product;

public class ProductDAO {

	// metodo para inserir dados na tabela product
	public void insertProduct(Product product) {

		String sql = "INSERT INTO products (barcode, name, price, brand_id) VALUES(?,?,?,?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

			pstmt.setString(1, product.getBarcode());
			pstmt.setString(2, product.getName());
			pstmt.setDouble(3, product.getPrice());

			pstmt.setInt(4, product.getBrand().getId());

			pstmt.executeUpdate();
			
			try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
				if (generatedKeys.next()) {
					product.setId(generatedKeys.getInt(1));
				}
				
			}
		} catch (SQLException e) {
			System.out.println("Erro ao tentar salvar o produto no banco.");
			e.printStackTrace();
		}
	}

	// Retorna uma lista 
		public List<Product> findAll() {
			List<Product> list = new ArrayList<>();
			
			
			String sql = "SELECT p.id AS product_id, p.name AS product_name, "
					   + "b.id AS brand_id, b.name AS brand_name "
					   + "FROM products p "
					   + "INNER JOIN brands b ON p.brand_id = b.id";

			
			try (Connection conn = DatabaseConnection.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					// 1. Monta o objeto Brand
					Brand brand = new Brand();
					brand.setId(rs.getInt("brand_id"));
					brand.setName(rs.getString("brand_name"));

					// 2. Monta o objeto Product e injeta a Brand nele
					Product product = new Product();
					product.setId(rs.getInt("product_id"));
					product.setName(rs.getString("product_name"));
					
					product.setPrice(rs.getDouble("price")); 
					
					product.setBrand(brand);

					// 3. Adiciona na lista
					list.add(product);
				}

			} catch (SQLException e) {
				System.out.println("Erro ao listar os produtos.");
				e.printStackTrace();
			}
			
			return list;
		}

	}


