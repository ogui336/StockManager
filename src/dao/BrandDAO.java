package dao;

import model.Brand;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


public class BrandDAO {
	//Insere uma nova MARCA no banco 
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Retorna todas as marcas 
	public List<Brand> findAll(){
		List<Brand> list = new ArrayList<>();
		String sql = "SELECT * FROM brands";
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			
			while (rs.next()) {
				Brand brand = new Brand();
				brand.setId(rs.getInt("id"));
				brand.setName(rs.getString("name"));
				
				list.add(brand);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
			
	}
	
	// Retorna uma marca especifica pelo ID
	public Brand findById(int id) {
		String sql = "SELECT * FROM brands WHERE id = ? ";
		Brand brand = null;
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setInt(1, id);
			
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					brand = new Brand();
					brand.setId(rs.getInt("id"));
					brand.setName(rs.getString("name"));
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return brand;
	}
	
	//Atualiza uma marca existente 
	public void update(Brand brand) {
		String sql = "UPDATE brands SET name = ? WHERE id = ?";
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, brand.getName());
			pstmt.setInt(2, brand.getId());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Deleta uma marca pelo ID
	public void delete(int id) {
		String sql = "DELETE FROM brands WHERE id = ?";
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
