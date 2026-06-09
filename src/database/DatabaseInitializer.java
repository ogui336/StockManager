package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

public static void initializeDatabase() {
        
        // 1. Tabela de Marcas (Independente)
        String sqlBrands = "CREATE TABLE IF NOT EXISTS brands ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL UNIQUE"
                + ");";

        // 2. Tabela de Produtos (Depende de Marcas)
        String sqlProducts = "CREATE TABLE IF NOT EXISTS products ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "barcode TEXT NOT NULL UNIQUE, "
                + "name TEXT NOT NULL, "
                + "price REAL NOT NULL, "
                + "brand_id INTEGER NOT NULL, "
                + "FOREIGN KEY (brand_id) REFERENCES brands(id)"
                + ");";

        // 3. Tabela de Lotes (Depende de Produtos)
        String sqlBatches = "CREATE TABLE IF NOT EXISTS batches (" 
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "batch_number TEXT NOT NULL, "
                + "product_id INTEGER NOT NULL, " 
                + "expiration_date TEXT NOT NULL, "
                + "quantity INTEGER NOT NULL CHECK (quantity >= 0), "
                + "FOREIGN KEY (product_id) REFERENCES products(id)"
                + ");";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Ativa o suporte a Chaves Estrangeiras no SQLite (padrão vem desativado)
            stmt.execute("PRAGMA foreign_keys = ON;");

            // Executa a criação das tabelas na ordem correta
            stmt.execute(sqlBrands);
            stmt.execute(sqlProducts);
            stmt.execute(sqlBatches);

            System.out.println("Estrutura do banco de dados verificada/criada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro crítico ao inicializar as tabelas do banco de dados.");
            e.printStackTrace();
        }
    }
}
