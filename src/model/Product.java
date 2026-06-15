package model;

import java.math.BigDecimal;

public class Product {
	private int id;
	private String barcode;
	private String name;
	private BigDecimal price;
	private Brand brand;

	// construtor vazio
	public Product() {
	}

	// construtor completo
	public Product(int id, String barcode, String name, BigDecimal price, Brand brand) {
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	

}
