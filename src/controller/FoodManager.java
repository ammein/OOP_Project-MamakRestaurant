package controller;

import java.util.*;

import model.Data_Access;
import model.Food;
import model.Ingredient;
import view.MamakOrderingSystem;

public class FoodManager {
	private HashMap<String, Food> database;
	public FoodManager(Food food) {
		database = new HashMap<String, Food>();
		database.put(food.getFoodName(), food);
	}
	
	public FoodManager(ArrayList<Food> foods) {
		database = new HashMap<String, Food>();
		for(Food food : foods) {
			database.put(food.getFoodName(), food);
		}
	}
	
	/**
	 * @return the database
	 */
	public HashMap<String, Food> getDatabase() {
		return database;
	}

	/**
	 * @param database the database to set
	 */
	public void setDatabase(HashMap<String, Food> database) {
		this.database = database;
	}

	public boolean addFoodDB() {
		Data_Access DOA = new Data_Access();
		
		try {
			for(Map.Entry<String, Food> food: database.entrySet()) {
				if(!DOA.addFoodMenu(food.getValue())) {
					return false;
				}
			}
			
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean editFoodDB(Food food) {
		Data_Access DOA = new Data_Access();
		try {
			return DOA.editFoodMenu(food);
		} catch(Exception e) {
			return false;
		}
	}
	
	public boolean removeFoodDB(Food food) {
		Data_Access DOA = new Data_Access();
		
		try {
			return DOA.removeFoodMenu(food);
		}catch(Exception e) {
			return false;
		}
	}
	
	public ArrayList<Food> getFoodDB(){
		Data_Access DOA = new Data_Access();
		
		try {
			return DOA.getFoodMenu();
		}catch(Exception e) {
			return null;
		}
	}
}
