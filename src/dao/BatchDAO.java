package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DatabaseConnection;
import model.Batch;

public class BatchDAO {

	public void insertBatch(Batch batch) {
		String sql = "INSERT INTO batches (batch_number, quantity, expiration_date, product_id) VALUES (?,?,?,?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, batch.getBatchNumber());
			stmt.setInt(2, batch.getQuantity());
			stmt.setString(3, batch.getExpirationDate());
			stmt.setInt(4, batch.getProductId()); // Conecta com o ID do produto

			stmt.executeUpdate();
			System.out.println("Sucesso: O lote '" + batch.getBatchNumber() + "' foi salvo no estoque!");
		
		} catch (SQLException e) {
			System.out.println("Erro ao salvar o lote no banco de dados.");
			e.printStackTrace();
		}

	}

	// Método para listar os lotes e os nomes dos produtos
	public void listBatches() {

		String sql = "SELECT b.id, b.batch_number, b.quantity, b.expiration_date, p.name AS product_name "
				+ "FROM batches b " + "INNER JOIN products p ON b.product_id = p.id";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			System.out.println("--- RELATÓRIO DE LOTES EM ESTOQUE ---");
			while (rs.next()) {
				int id = rs.getInt("id");
				String batchNumber = rs.getString("batch_number");
				int quantity = rs.getInt("quantity");
				String expiration = rs.getString("expiration_date");
				String productName = rs.getString("product_name");

				System.out.println("ID lote: " + id 
						+ " | Lote: " + batchNumber 
						+ " | Produto: " + productName
						+ " | Qtd: " + quantity 
						+ " | Vencimento: " + expiration);
			}
			System.out.println("-----------------------------------------\n");

		} catch (SQLException e) {
			System.out.println("Erro ao listar os lotes.");
			e.printStackTrace();

		}

	}
}
