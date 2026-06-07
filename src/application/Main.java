package application;

import model.Brand;
import model.Product;
import dao.BrandDAO;
import dao.ProductDAO;

public class Main {
	public static void main(String[] args) {

		// 1. "Recriando" a marca Nestlé na memória, avisando ao Java que o ID dela é 1
				Brand marcaNestle = new Brand();
				marcaNestle.setId(1); // Esse é o ID que o banco gerou quando salvamos ela antes!
				marcaNestle.setName("Nestlé");

				// 2. Instanciando o nosso primeiro Produto
				Product produto = new Product();
				produto.setBarcode("22024566598");
				produto.setName("Bis Branco");
				produto.setPrice(5.80);
				
				// AQUI ESTÁ A CHAVE ESTRANGEIRA! Colocamos o objeto marca dentro do produto
				produto.setBrand(marcaNestle); 

				// 3. Instanciando o DAO de Produtos e mandando salvar
				ProductDAO productDao = new ProductDAO();

				System.out.println("Iniciando a inserção do produto no banco...");
				productDao.insertProduct(produto);
				productDao.listProducts();
			}

                
}


