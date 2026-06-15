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

//LOTE-DAO
public class BatchDAO {

	// Insere dados na tabela batch/Lotes
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

	// Retorna todos os LOTES/batch cadastrados
	public List<Batch> findAll() {
		List<Batch> list = new ArrayList<>();
		String sql = "SELECT b.id AS batch_id, b.batch_number, b.quantity, b.expiration_date, "
				+ "p.id AS product_id, p.name AS product_name, " + "br.id AS brand_id, br.name AS brand_name "
				+ "FROM batches b " + "INNER JOIN products p ON b.product_id = p.id "
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

	// Busca um lote espesifico pelo seu ID
	public Batch findById(int id) {
		String sql = "SELECT b.id AS batch_id, b.batch_number, b.quantity, b.expiration_date, "
				+ "p.id AS product_id, p.name AS product_name, " + "br.id AS brand_id, br.name AS brand_name "
				+ "FROM batches b " + "INNER JOIN products p ON b.product_id = p.id "
				+ "INNER JOIN brands br ON p.brand_id = br.id " + "WHERE b.id = ?";
		Batch batch = null;

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				Brand brand = new Brand();
				brand.setId(rs.getInt("brand_id"));
				brand.setName(rs.getString("brand_name"));

				Product product = new Product();
				product.setId(rs.getInt("product_id"));
				product.setName(rs.getString("product_name"));
				product.setBrand(brand);

				batch = new Batch();
				batch.setId(rs.getInt("batch_id"));
				batch.setBatchNumber(rs.getString("batch_number"));
				batch.setQuantity(rs.getInt("quantity"));
				batch.setExpirationDate(LocalDate.parse(rs.getString("expiration_date")));
				batch.setProduct(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return batch;
	}

	// Filtra e retorna lotes vencidos ou na janela de risco (X meses antes de vencer)
	public List<Batch> findExpiringBatches(int monthAhead) {
		List<Batch> list = new ArrayList<>();
		String sql = "SELECT b.id AS batch_id, b.batch_number, b.quantity, b.expiration_date, "
				+ "p.id AS product_id, p.name AS product_name, " + "br.id AS brand_id, br.name AS brand_name "
				+ "FROM batches b " + "INNER JOIN products p ON b.product_id = p.id "
				+ "INNER JOIN brands br ON p.brand_id = br.id " + "WHERE b.expiration_date <= ? "
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

	// Atualiza a quantidade de itens de um lote
	public void updateQuantity(int id, int newQuantity) {
		String sql = "UPDATE batches SET quantity = ?"
				+ "WHERE id = ?";
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, newQuantity);
			pstmt.setInt(2, id);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Retorna a soma total em estoque de um produto específico somando todos os seus lotes
	public int getTotalStock(int productId) {
		String sql = "SELECT SUM(quantity) AS total_stock FROM batches WHERE product_id= ?";
		int totalStock = 0;
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, productId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					totalStock = rs.getInt("total_stock");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalStock;
	}
	
	// Deleta um lote do banco pelo ID
		public void delete(int id) {
			String sql = "DELETE FROM batches WHERE id = ?";

			try (Connection conn = DatabaseConnection.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql)) {

				pstmt.setInt(1, id);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
}