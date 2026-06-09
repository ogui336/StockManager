package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import database.DatabaseConnection;
import model.Brand;

public class BrandDAO {
	public void insertBrand(Brand brand) {
		String sql = "INSERT INTO brands (name) VALUES (?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, brand.getName());
			pstmt.executeUpdate();

			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					int idGerado = rs.getInt(1);
					brand.setId(idGerado);
				}
			}
			System.out.println("Sucesso: A marca '" + brand.getName() + "' foi salva no banco de dados!");
		} catch (SQLException e) {
			System.out.println("Erro ao tentar salvar no banco...");
			e.printStackTrace();
		}
	}
}
