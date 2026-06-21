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

// Marca-DAO
public class BrandDAO {
	
	//Insere uma nova MARCA/brand no banco 
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
			// Erro 1062: entrada duplicada
			if (e.getErrorCode() == 1062 || "23000".equals(e.getSQLState())) {
				throw new RuntimeException("Não foi possível cadastrar: A marca '" + brand.getName() + "' já existe!");
			}
			throw new RuntimeException("Erro ao tentar salvar a marca no banco de dados.",e);
		}
	}
	
	// Retorna todas as marcas cadastradas 
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
			throw new RuntimeException("Erro ao buscar a lista de marcas no sistema.", e);
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
			throw new RuntimeException("Erro ao buscar a marca com o ID: " + id, e);
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
			// Adicionado tratamento para o caso de alterarem o nome para um que já existe
			if (e.getErrorCode() == 1062 || "23000".equals(e.getSQLState())) {
				throw new RuntimeException("Não foi possível atualizar: O nome '" + brand.getName() + "' já está em uso por outra marca!");
				}
			throw new RuntimeException("Erro ao tentar atualizar os dados da marca de ID: " + brand.getId(), e);
					}
	}
	
	// Deleta uma marca pelo ID
	public void delete(int id) {
		String sql = "DELETE FROM brands WHERE id = ?";
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
	        // Erro 1451: Violação de integridade (Item pai possui filhos cadastrados)
	        if (e.getErrorCode() == 1451 || "23503".equals(e.getSQLState())) {
	            throw new RuntimeException("Não é possível excluir esta marca, pois existem produtos vinculados a ela.");
	        }
	        throw new RuntimeException("Erro ao tentar deletar a marca.", e);
	    }
	}
}
