package model;

public class Ingredient {
	private int id;
	private String name;
	private int quantity;
	
	public Ingredient(String name){
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param quantity to set
	 */
	public void setQuantity(Quantity quantity) {
		this.quantity = quantity.getCount();
	}
	
	public int getQuantity() {
		return this.quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
