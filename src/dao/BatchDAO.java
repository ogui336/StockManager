package dao;

import model.Batch;
import model.Brand;
import model.Product;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

public class BatchDAO {

	// Insere um novo lote sem poluir o console do sistema
	public void insertBatch(Batch batch) {
		String sql = "INSERT INTO batches (batch_number, quantity, expiration_date, product_id) VALUES (?,?,?,?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, batch.getBatchNumber());
			stmt.setInt(2, batch.getQuantity());
			stmt.setString(3, batch.getExpirationDate().toString());
			stmt.setInt(4, batch.getProduct().getId()); 

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Retorna todos os lotes do banco 
	public List<Batch> findAll() {
		List<Batch> list = new ArrayList<>();
		String sql = "SELECT b.id AS batch_id, b.batch_number, b.quantity, b.expiration_date, "
				+ "p.id AS product_id, p.name AS product_name, "
				+ "br.id AS brand_id, br.name AS brand_name "
				+ "FROM batches b "
				+ "INNER JOIN products p ON b.product_id = p.id "
				+ "INNER JOIN brands br ON p.brand_id = br.id";
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
				Brand brand = new Brand();
				brand.setId(rs.getInt("brand_id"));
				brand.setName(rs.getString("brand_name"));
				
				Product product = new Product();
				product.setId(rs.getInt("product_id"));
				product.setName(rs.getString("product_name"));
				product.setBrand(brand);
				
				Batch batch = new Batch();
			    batch.setId(rs.getInt("batch_id"));
			    batch.setBatchNumber(rs.getString("batch_number"));
			    batch.setQuantity(rs.getInt("quantity"));
			    batch.setExpirationDate(LocalDate.parse(rs.getString("expiration_date")));
			    batch.setProduct(product);
			    
			    list.add(batch);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}
	
	// Filtra e retorna lotes vencidos ou na janela de risco (X meses antes de vencer)
	public List<Batch> findExpiringBatches(int monthAhead) {
		List<Batch> list = new ArrayList<>();
		String sql = "SELECT b.id AS batch_id, b.batch_number, b.quantity, b.expiration_date, "
				+ "p.id AS product_id, p.name AS product_name, "
				+ "br.id AS brand_id, br.name AS brand_name "
				+ "FROM batches b "
				+ "INNER JOIN products p ON b.product_id = p.id "
				+ "INNER JOIN brands br ON p.brand_id = br.id "
				+ "WHERE b.expiration_date <= ? "
				+ "ORDER BY b.expiration_date ASC";
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) { 
			
			LocalDate limitDate = LocalDate.now().plusMonths(monthAhead);
			pstmt.setString(1, limitDate.toString());
			
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Brand brand = new Brand();
					brand.setId(rs.getInt("brand_id"));
					brand.setName(rs.getString("brand_name"));

					Product product = new Product();
					product.setId(rs.getInt("product_id"));
					product.setName(rs.getString("product_name"));
					product.setBrand(brand);

					Batch batch = new Batch();
					batch.setId(rs.getInt("batch_id"));
					batch.setBatchNumber(rs.getString("batch_number"));
					batch.setQuantity(rs.getInt("quantity"));
					batch.setExpirationDate(LocalDate.parse(rs.getString("expiration_date")));
					batch.setProduct(product);

					list.add(batch);
				}
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}