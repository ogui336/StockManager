package dao;

import model.Brand;
import model.Product;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

	// Insere dados na tabela product
	public void insertProduct(Product product) {

		String sql = "INSERT INTO products (barcode, name, price, brand_id) VALUES(?,?,?,?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, product.getBarcode());
			pstmt.setString(2, product.getName());
			pstmt.setBigDecimal(3, product.getPrice());
			pstmt.setInt(4, product.getBrand().getId());

			pstmt.executeUpdate();

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					product.setId(generatedKeys.getInt(1));
				}

			}
		} catch (SQLException e) {
			
			// Erro 1062: Código de barras duplicado (coluna UNIQUE)
				if (e.getErrorCode() == 1062 || "23000".equals(e.getSQLState())) {
					throw new RuntimeException("Não foi possível cadastrar: O código de barras '" + product.getBarcode() + "' já está cadastrado!");
					}
				
				// Erro 1452: Tentar inserir um brand_id que não existe na tabela de marcas
				if (e.getErrorCode() == 1452) {
					throw new RuntimeException("Não foi possível cadastrar: A marca associada (ID: " + product.getBrand().getId() + ") não existe.");
					}
				
				throw new RuntimeException("Erro inesperado ao tentar salvar o produto no banco de dados.", e);
					}
	}

	// Retorna todos os PRODUTOS/products cadastrados
	public List<Product> findAll() {
		List<Product> list = new ArrayList<>();

		String sql = "SELECT p.id AS product_id, p.barcode, p.name AS product_name, p.price, "
				+ "b.id AS brand_id, b.name AS brand_name " + "FROM products p "
				+ "INNER JOIN brands b ON p.brand_id = b.id";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {

				Brand brand = new Brand();
				brand.setId(rs.getInt("brand_id"));
				brand.setName(rs.getString("brand_name"));

				Product product = new Product();
				product.setId(rs.getInt("product_id"));
				product.setBarcode(rs.getString("barcode"));
				product.setName(rs.getString("product_name"));
				product.setPrice(rs.getBigDecimal("price"));
				product.setBrand(brand);

				list.add(product);
			}

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar a lista de produtos no sistema.", e);
		}

		return list;
	}

	// Busca um produto específico pelo ID
	public Product findByID(int id) {
		String sql = "SELECT p.id AS product_id, p.barcode, p.name AS product_name, p.price, "
				+ "b.id AS brand_id, b.name As brand_name " 
				+ "FROM products p "
				+ "INNER JOIN brands b ON p.barnd_id = b.id " 
				+ "WHERE p.id = ?";
		Product product = null;

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);
			
			try (ResultSet rs = pstmt.executeQuery()){
				if (rs.next()) {
					Brand brand = new Brand();
					brand.setId(rs.getInt("brand_id"));
					brand.setName(rs.getString("brand_name"));
					
					product = new Product();
					product.setId(rs.getInt("product_id"));
					product.setBarcode(rs.getString("barcode"));
					product.setName(rs.getString("product_name"));
					product.setPrice(rs.getBigDecimal("price"));
					product.setBrand(brand);
					
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar o produto com o ID: " + id, e);
		}
		return product;
	}
	
	// Busca um produto pelo código de barras
	public Product findByBarcode(String barcode) {
		String sql = "SELECT p.id AS product_id, p.barcode, p.name AS product_name, p.price, "
				   + "b.id AS brand_id, b.name AS brand_name "
				   + "FROM products p "
				   + "INNER JOIN brands b ON p.brand_id = b.id "
				   + "WHERE p.barcode = ?";
		Product product = null;
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, barcode);
			
			try (ResultSet rs = pstmt.executeQuery()){
				if (rs.next()) {
					Brand brand = new Brand();
					brand.setId(rs.getInt("brand_id"));
					brand.setName(rs.getString("brand_name"));

					product = new Product();
					product.setId(rs.getInt("product_id"));
					product.setBarcode(rs.getString("barcode"));
					product.setName(rs.getString("product_name"));
					product.setPrice(rs.getBigDecimal("price"));
					product.setBrand(brand);
				}
			}
			
		}catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar o produto com o código de barras: " + barcode, e);
		}
		return product;
	}
	
	public void update(Product product) {
		String sql = "UPDATE products SET barcode = ?, name = ?, price = ?, brand_id = ? "
				+ "WHERE id = ?";
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, product.getBarcode());
			pstmt.setString(2, product.getName());
			pstmt.setBigDecimal(3, product.getPrice());
			pstmt.setInt(4, product.getBrand().getId());
			pstmt.setInt(5, product.getId());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			
			// Impede clonar o código de barras de outro produto existente na alteração
			if (e.getErrorCode() == 1062 || "23000".equals(e.getSQLState())) {
				throw new RuntimeException("Não foi possível atualizar: O código de barras '" + product.getBarcode() + "' já pertence a outro produto!");
				}
					
			if (e.getErrorCode() == 1452) {
				throw new RuntimeException("Não foi possível atualizar: A nova marca informada (ID: " + product.getBrand().getId() + ") não foi encontrada.");
				}
						
			throw new RuntimeException("Erro ao tentar atualizar os dados do produto de ID: " + product.getId(), e);
				}	
	}
	
	// Deleta um produto pelo ID
	public void delete(int id) {
		String sql = "DELETE FROM products WHERE id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			
			// Erro 1451: Impede a exclusão se houver algum lote atrelado a este produto
			if (e.getErrorCode() == 1451 || "23503".equals(e.getSQLState())) {
				throw new RuntimeException("Não é possível excluir o produto. Existem lotes ativos associados a ele no estoque.");
				}
						
			throw new RuntimeException("Erro ao tentar deletar o produto de ID: " + id, e);
					}
	}
}
