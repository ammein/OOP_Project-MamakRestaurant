package model;

import java.beans.Statement;
import java.lang.System.Logger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import view.Main;
import view.MamakOrderingSystem;
import view.Message;

public class Data_Access {
	
	private Connection connection;
	private MamakDatabase database;
	
	public Data_Access() {
		try {
			database = new MamakDatabase();
			connection = database.connectDatabase();
		} catch(Exception e) {
			e.getMessage();
		}
	}
	
	public boolean createUser(User user) {
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO staffMember (username, passwordHashed, permission) VALUES(?, ?, ?)");
			statement.setString(1, user.getUsername());
			statement.setString(2, new String(user.getPassword()));
			statement.setString(3, String.valueOf(user.getPermission()));
			statement.execute();
			return true;
		}catch(Exception e) {
			java.util.logging.Logger log =java.util.logging.Logger.getGlobal();
			log.warning("Unable to create new user. \nError Message:" + e.getMessage() + "\n\n");
			return false;
		}
	}

	public User getUser(User user) {
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM staffMember WHERE username=? AND passwordHashed=?");
			statement.setString(1, user.getUsername());
			statement.setString(2, new String(user.getPassword()));
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				User theUser = new User(result.getString("username"),result.getString("passwordHashed").toCharArray());
				theUser.setPermission(result.getInt("permission"));
				return theUser;
			}else {
				return null;
			}
		}catch(Exception e) {
			return null;
		}
	}
	
	public int checkAllUsersInDB() {
		int numberOfUsers = 0;
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM staffMember");
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				numberOfUsers++;
			}
			
			return numberOfUsers;
		}catch(Exception e) {
			return 0;
		}
	}

	public String encryptPass(String password) {
		try {
			// retrieve instance of the encryptor of SHA-256
			MessageDigest digestor = MessageDigest.getInstance("SHA-256");
			//	retrieve bytes to encrypt
			byte[] encodedhash = digestor.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuilder encryptionValue = new StringBuilder(2 * encodedhash.length);
			//	perform encryption
			for (int i = 0; i < encodedhash.length; i++) {
				String hexVal = Integer.toHexString(0xff & encodedhash[i]);
				if (hexVal.length() == 1) {
					encryptionValue.append('0');
				}
				encryptionValue.append(hexVal);
			}
			//	return encrypted value
			return encryptionValue.toString();
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}
	
	public boolean addFoodMenu(Food food) {
		try {
			for(int i = 0; i < food.getIngredient().size(); i++) {
				PreparedStatement statement = connection.prepareStatement("INSERT INTO mamakFoods (foodName, foodPrice, INGREDIENT_ID) VALUES(?, ?, ?)");
				statement.setString(1, food.getFoodName());
				statement.setBigDecimal(2, food.getPrice());
				statement.setInt(3, food.getIngredient().get(i).getId());
				statement.executeUpdate();
			}
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public ArrayList<Food> getFoodMenu() {
		try {
			ArrayList<Food> foods = new ArrayList<Food>(); 
			ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
			
			PreparedStatement statement = connection.prepareStatement("SELECT foodName, foodPrice, INGREDIENT_ID, ingredientName, ingredientCount FROM mamakFoods INNER JOIN mamakIngredients ON mamakFoods.INGREDIENT_ID=mamakIngredients.ingredientId");
			
			ResultSet result = statement.executeQuery();
			
			Food collectFood = null;
			String currentFoodName = "";
			
			while(result.next()) {
				if(!result.getString("foodName").contentEquals(currentFoodName)) {
					currentFoodName = result.getString("foodName");
					collectFood = new Food(result.getString("foodName"), MamakOrderingSystem.getInventory().getDatabase().get(result.getString("ingredientName")));
					collectFood.setPrice(result.getBigDecimal("foodPrice"));
					foods.add(collectFood);
				} else {
					collectFood.addIngredient(MamakOrderingSystem.getInventory().getDatabase().get(result.getString("ingredientName")));
				}
			}
			foods.add(collectFood);
			return foods;
		}catch(Exception e) {
			return null;
		}
	}
	
	public boolean removeFoodMenu(Food food) {
		try {
			
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM mamakIngredients WHERE ingredientName=?");
			statement.setString(1,food.getFoodName());
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				try{
					PreparedStatement s = connection.prepareStatement("DELETE FROM mamakFoods WHERE foodName=?");
					s.setString(1, food.getFoodName());
					s.executeUpdate();
					return true;
				}catch(Exception e) {
					return false;
				}
			}
			
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean addIngredientToLists(Ingredient ingredient) {
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO mamakIngredients VALUES(?, ?)");
			statement.setString(1, ingredient.getName());
			statement.setInt(2, ingredient.getQuantity());
			statement.executeUpdate();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean editIngredientFromLists(Ingredient ingredient) {
		try {
			
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM mamakIngredients WHERE ingredientName=?");
			statement.setString(1,ingredient.getName());
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				if(result.getInt("ingredientCount") != ingredient.getQuantity()) {
					try{
						PreparedStatement s = connection.prepareStatement("UPDATE mamakIngredients SET ingredientCount=? WHERE ingredientName=?");
						s.setInt(1, ingredient.getQuantity());
						s.setString(2, ingredient.getName());
						s.executeUpdate();
						return true;
					}catch(Exception e) {
						return false;
					}
				} 
			}
			
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean removeIngredientFromLists(Ingredient ingredient) {
		try {
			
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM mamakIngredients WHERE ingredientName=?");
			statement.setString(1,ingredient.getName());
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				if(result.getInt("ingredientCount") != ingredient.getQuantity()) {
					try{
						PreparedStatement s = connection.prepareStatement("DELETE FROM mamakIngredients WHERE ingredientName=?");
						s.setString(1, ingredient.getName());
						s.executeUpdate();
						return true;
					}catch(Exception e) {
						return false;
					}
				} 
			}
			
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	
	public ArrayList<Ingredient> getIngredientsLists() {
		try {
			ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>(); 
			
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM mamakIngredients");
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				Ingredient collectIngredient = new Ingredient(result.getString("ingredientName"));
				collectIngredient.setQuantity(new Quantity(result.getInt("ingredientCount")));
				collectIngredient.setId(result.getInt("ingredientId"));
				ingredients.add(collectIngredient);
			}
			
			return ingredients;
		}catch(Exception e) {
			return null;
		}
	}
}
