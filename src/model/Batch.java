package model;

public class Batch {
	
	private int id; // PK
	private String batchNumber; // Guarda o código identificador do lote  Ex: "LOTE-2026-01"
	private int quantity; // Quantidade de unidades
	private String expirationDate; // Data de validade do lote
	private int productId; // FK
	
	public Batch() {
	}
	
	public Batch(String batchNumber, int quantity, String expirationDate, int productId) {
		this.batchNumber = batchNumber;
	    this.quantity = quantity;
	    this.expirationDate = expirationDate;
	    this.productId = productId;
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

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	
}
