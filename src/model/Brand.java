package model;

public class Brand {
	private int id;
	private String name;

	// construtor vazio
	public Brand() {
	}

	// construtor completo
	public Brand(int id, String name) {
		this.id = id;
		this.name = name;
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

}
