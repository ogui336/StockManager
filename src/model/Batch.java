package model;

import java.time.LocalDate;

public class Batch {
	
	private int id; // PK
	private String batchNumber; // Guarda o código identificador do lote  Ex: "LOTE-2026-01"
	private int quantity; // Quantidade de unidades
	private LocalDate expirationDate; // Data de validade do lote
	private Product product; // FK
	
	public Batch() {
	}
	
	public Batch(String batchNumber, int quantity, LocalDate expirationDate, Product product) {
		this.batchNumber = batchNumber;
	    this.quantity = quantity;
	    this.expirationDate = expirationDate;
	    this.product = product;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
