package model;

public class Product {
	private int id;
	private String barcode;
	private String name;
	private double price;
	private int stockQuantity;
	private Brand brand;

	// construtor vazio
	public Product() {
	}

	// construtor completo
	public Product(int id, String barcode, String name, double price, Brand brand) {
		this.id = id;
		this.barcode = barcode;
		this.name = name;
		this.price = price;
		this.brand = brand;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

}
