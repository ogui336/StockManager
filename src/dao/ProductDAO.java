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
		} catch (SQLException e) {
			System.out.println("Erro ao tentar salvar o produto no banco.");
			e.printStackTrace();
		}
	}

	// Metod para buscar e mostrar os dados da tabela
	public void listProducts() {
		String sql = "SELECT p.id, p.barcode, p.name, p.price, b.name AS brand_name " + "FROM products p "
				+ "INNER JOIN brands b ON p.brand_id = b.id";

		try {
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String barcode = rs.getString("barcode");
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				String brandName = rs.getString("brand_name");

				System.out.println("ID: " + id + " | Código: " + barcode + " | Produto: " + name + " | Preço: R$ "
						+ price + " | Marca: " + brandName);

				System.out.println("--------------------------------\n");

			}
		} catch (SQLException e) {
			System.out.println("Erro ao listar os produtos.");
			e.printStackTrace();
		}

	}

}
