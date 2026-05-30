package application;

import dao.ProductDAO;

public class Main {
	public static void main(String[] args) {

		System.out.println("Iniciando o sistema StockManager...");

		// 1. Instanciamos a classe que sabe executar os comandos SQL
		ProductDAO productDao = new ProductDAO();

		// 2. Mandamos ela criar as tabelas na ordem certa
		productDao.createTable();

	}
}


