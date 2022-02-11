package model;

import java.math.BigDecimal;
import java.util.*;

public class Food {
	
	private String foodName;
	private BigDecimal price;
	private ArrayList<Ingredient> ingredient;
	

	public Food(String name, ArrayList<Ingredient> ingredients) {
		this.ingredient = ingredients;
		this.foodName = name;
	}
	
	public Food(String name, Ingredient ingredient) {
		this.ingredient = this.ingredient != null ? this.ingredient : new ArrayList<Ingredient>();
		this.ingredient.add(ingredient);
		this.foodName = name;
	}
	
	public Food(String name) {
		this.ingredient = this.ingredient != null ? this.ingredient : new ArrayList<Ingredient>();
		this.foodName = name;
	}


	/**
	 * @return the foodName
	 */
	public String getFoodName() {
		return foodName;
	}


	/**
	 * @param foodName the foodName to set
	 */
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}


	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}


	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	/**
	 * @return the ingredient
	 */
	public ArrayList<Ingredient> getIngredient() {
		return ingredient;
	}


	/**
	 * @param ingredient the ingredient to set
	 */
	public void setIngredient(ArrayList<Ingredient> ingredient) {
		this.ingredient = ingredient;
	}
	
	/**
	 * @param ingredient the ingredient to add
	 */
	public void addIngredient(Ingredient ingredient) {
		this.ingredient.add(ingredient);
	}

}
