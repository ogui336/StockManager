package application;

import model.Batch;
import model.Brand;
import model.Product;

import java.time.LocalDate;

import dao.BatchDAO;
import dao.BrandDAO;
import dao.ProductDAO;
import database.DatabaseInitializer;

public class Main {
	public static void main(String[] args) {
		DatabaseInitializer.initializeDatabase();;
		// 1. Instanciando os DAOs
		BrandDAO brandDAO = new BrandDAO();
		ProductDAO productDAO = new ProductDAO();
		BatchDAO batchDAO = new BatchDAO();

		System.out.println("=== INICIANDO TESTE DO SISTEMA DE ESTOQUE ===\n");

		// 2. Criando e Inserindo a Marca (O banco vai gerar o ID automaticamente)
		Brand marca = new Brand();
		marca.setName("Coca-Cola Company");
		brandDAO.insertBrand(marca); // Aqui o objeto 'marca' ganha o ID do banco!

		// 3. Criando e Inserindo o Produto (Passando o objeto 'marca' completo)
		// Construtor: Product(id, barcode, name, price, brand)
		// Passamos 0 no ID porque o SQLite vai gerar o ID real no autoincremento
		Product produto = new Product(0, "7891234567890", "Coca-Cola Zero 350ml", 4.50, marca);
		productDAO.insertProduct(produto); // Aqui o objeto 'produto' ganha o ID do banco!

		// 4. Criando e Inserindo o Lote (FALHAS 4 e 7 CORRIGIDAS AQUI!)
		// Usamos LocalDate.of(Ano, Mês, Dia) para criar uma data real
		LocalDate validade = LocalDate.of(2026, 12, 31);
		
		// Construtor atualizado: Batch(batchNumber, quantity, expirationDate, product)
		Batch lote = new Batch("LOTE-2026-01", 150, validade, produto);
		batchDAO.insertBatch(lote);

		System.out.println("\n=== CONSULTANDO OS DADOS DO BANCO ===");
		
		// 5. Listando os produtos e lotes para garantir que o INNER JOIN funcionou
		System.out.println("\n> Listagem de Produtos:");
		productDAO.listProducts();

		System.out.println("\n> Listagem de Lotes:");
		batchDAO.listBatches();
		}

	
}
