package application;

import model.Batch;
import model.Brand;
import model.Product;
import dao.BatchDAO;
import dao.BrandDAO;
import dao.ProductDAO;
import database.DatabaseInitializer;

public class Main {
	public static void main(String[] args) {

		ProductDAO productDao = new ProductDAO();
		BatchDAO batchDao = new BatchDAO();

		// Chamamos as duas listagens para ver o banco completo!
		System.out.println("\n=== LISTAGEM DE PRODUTOS ===");
		productDao.listProducts();

		System.out.println("\n=== LISTAGEM DE LOTES ===");
		batchDao.listBatches();
	}

}
