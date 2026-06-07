package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.DatabaseConnection;
import model.Brand;

public class BrandDAO {
	public void inserBrand(Brand brand) {
		String sql = "INSERT INTO brands (name) VALUES (?)";

		try {
			Connection conn = DatabaseConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, brand.getName());

			pstmt.executeUpdate();

			System.out.println("Sucesso: A marca '" + brand.getName() + "' foi salva no banco de dados!");
		} catch (SQLException e) {
			System.out.println("Erro ao tentar salvar no banco...");
			e.printStackTrace();
		}
	}
}
