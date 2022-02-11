package controller;

import java.util.*;

import model.Data_Access;
import model.Ingredient;

public class IngredientManager {
	private HashMap<String, Ingredient> database;
	
	public IngredientManager(ArrayList<Ingredient> ingredients){
		database = new HashMap<String, Ingredient>();
		for(Ingredient ingredient : ingredients) {
			database.put(ingredient.getName(), ingredient);
		}
	}
	
	public IngredientManager(Ingredient ingredient) {
		database = new HashMap<String, Ingredient>();
		database.put(ingredient.getName(), ingredient);
	}
	
	/**
	 * @return the database
	 */
	public HashMap<String, Ingredient> getDatabase() {
		return database;
	}

	/**
	 * @param database the database to set
	 */
	public void setDatabase(HashMap<String, Ingredient> database) {
		this.database = database;
	}

	public boolean insertDB() {
		Data_Access DOA = new Data_Access();
		
		try {
			for(Map.Entry<String, Ingredient> ingredient: database.entrySet()) {
				if(!DOA.addIngredientToLists(ingredient.getValue())) {
					return false;
				}
			}
			
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean editDB(String ingredientName) {
		Data_Access DOA = new Data_Access();
		
		try {
			for(Map.Entry<String, Ingredient> myIngredient: database.entrySet()) {
				if(myIngredient.getValue().getName().contentEquals(ingredientName)) {
					DOA.editIngredientFromLists(myIngredient.getValue());
				}
			}
			
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean removeDB(String ingredientName) {
		Data_Access DOA = new Data_Access();
		
		try {
			for(Map.Entry<String, Ingredient> myIngredient: database.entrySet()) {
				if(myIngredient.getValue().getName().contentEquals(ingredientName)) {
					DOA.removeIngredientFromLists(myIngredient.getValue());
					database.remove(myIngredient.getKey());
				}
			}
			
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
