package model;

public class Brand {
	private int id;
	private String brandName;

	// construtor vazio
	public Brand() {
	}

	// construtor completo
	public Brand(int id, String name) {
		this.id = id;
		this.brandName = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return brandName;
	}

	public void setName(String name) {
		this.brandName = name;
	}

}
