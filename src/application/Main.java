package application;

import model.Batch;
import model.Brand;
import model.Product;

import java.time.LocalDate;

import dao.BatchDAO;
import dao.BrandDAO;
import dao.ProductDAO;
import database.DatabaseInitializer;


import java.util.List;


public class Main {
	public static void main(String[] args) {
		
		// 1. Inicializando os nossos DAOs
		BrandDAO brandDAO = new BrandDAO();
		ProductDAO productDAO = new ProductDAO();
		BatchDAO batchDAO = new BatchDAO();

		

		
		// ==================================================================
				// 📊 EXECUTANDO O MÉTODO findAll()
				// ==================================================================
				System.out.println("=== 📊 EXECUTANDO O NOVO MÉTODO findAll() ===");
				List<Batch> allBatches = batchDAO.findAll();
				System.out.println(">>> SUCESSO! Total de lotes carregados do banco: " + allBatches.size() + " <<<");
				System.out.println("------------------------------------------------------------------");
				
				for (Batch b : allBatches) {
					System.out.printf("Lote: %-13s | Qtd: %-4d | Vencimento: %s | Produto: %-25s | Marca: %s%n",
							b.getBatchNumber(), 
							b.getQuantity(), 
							b.getExpirationDate(), 
							b.getProduct().getName(),
							b.getProduct().getBrand().getName());
				}
				System.out.println("------------------------------------------------------------------\n");


				// ==================================================================
				// 🚨 TESTANDO RELATÓRIO DE RISCO DE VALIDADE (3 Meses de Antecedência)
				// ==================================================================
				System.out.println("=== 🚨 TESTANDO RELATÓRIO DE RISCO (Vencidos ou Próximos de Vencer) ===");
				int monthsAhead = 3;
				List<Batch> expiringBatches = batchDAO.findExpiringBatches(monthsAhead);
				
				System.out.println("Lotes que precisam de atenção urgente (Janela de " + monthsAhead + " meses): " + expiringBatches.size());
				System.out.println("------------------------------------------------------------------");
				
				if (expiringBatches.isEmpty()) {
					System.out.println("Nenhum lote em estado crítico encontrado para o período selecionado.");
				} else {
					for (Batch b : expiringBatches) {
						System.out.printf("ALERTA -> Lote: %-10s | Vencimento: %s | Produto: %-22s | Marca: %s%n",
								b.getBatchNumber(), 
								b.getExpirationDate(), 
								b.getProduct().getName(),
								b.getProduct().getBrand().getName());
					}
				}
				System.out.println("------------------------------------------------------------------");
	}
}