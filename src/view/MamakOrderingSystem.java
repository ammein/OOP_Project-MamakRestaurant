package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.*;

import controller.FoodManager;
import controller.IngredientManager;
import model.Data_Access;
import model.Food;
import model.Ingredient;
import model.User;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;
import java.beans.*;
import java.util.*;
import javax.swing.JList;

public class MamakOrderingSystem extends JPanel  {
	private static JPanel Ingredient_Manager;
	private static JPanel Order_Item;
	private static JPanel Food_Manager;
	private static JTabbedPane tabbedPane;
	private User currentUser;
	
	public MamakOrderingSystem(User user) {
		this.currentUser = user;
		setLayout(new BorderLayout(0, 0));
		
		JPanel NORTH = new JPanel();
		FlowLayout flowLayout = (FlowLayout) NORTH.getLayout();
		flowLayout.setHgap(20);
		add(NORTH, BorderLayout.NORTH);
		String userName = user.getFullName() != null & user.getFullName().length() > 0 ? user.getFullName() : user.getUsername();
		
		JPanel panel = new JPanel();
		NORTH.add(panel);
		panel.setLayout(new CardLayout(0, 0));
		JLabel username_label = new JLabel("Welcome, " + userName + "!");
		username_label.setHorizontalAlignment(SwingConstants.LEFT);
		username_label.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		panel.add(username_label, "name_10182348785557");
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		NORTH.add(horizontalStrut);
		
		JLabel title = new JLabel("Restaurant Ordering System");
		title.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		NORTH.add(title);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		NORTH.add(horizontalStrut_1);
		
		JButton log_out = new JButton("Log Out");
		log_out.setHorizontalAlignment(SwingConstants.RIGHT);
		log_out.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		NORTH.add(log_out);
		log_out.setBackground(new Color(229, 229, 229));
		log_out.setSize(new Dimension(178, 43));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				MamakOrderingSystem.this.stateChanged(e, user);
			}
		});
		
		Order_Item = new JPanel();
		tabbedPane.addTab("Order Item", null, Order_Item, null);
		Order_Item.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		if(user.getPermission() == 0) {
			Food_Manager = new JPanel();
			tabbedPane.addTab("Food Manager", null, Food_Manager, null);
			Food_Manager.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			
			Ingredient_Manager = new JPanel();
			tabbedPane.addTab("Ingredient Manager", null, Ingredient_Manager, null);
			Ingredient_Manager.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		}
		
		JPanel left_pane = new JPanel();
		add(left_pane, BorderLayout.WEST);
		left_pane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton add_ingredient = new JButton("Add New Ingredient");
		left_pane.add(add_ingredient);
		
		add_ingredient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.newPanel(new IngredientSetup(), "Add More Ingredients");
			}
			
		});
		
		JButton addFood = new JButton("Add New Food Item");
		addFood.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.newPanel(new FoodSetup(), "Add Food Item");
			}
			
		});
		left_pane.add(addFood);
		
		JButton staff_member = new JButton("Staff Member");
		left_pane.add(staff_member);
		
		JButton btnNewButton_1 = new JButton("Bill");
		left_pane.add(btnNewButton_1);
		
		log_out.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				log_out(e);
			}
			
		});
		
	}
	
	public static void refreshTab(String tabName) {
		Ingredient_Manager.removeAll();
		int allTabs = tabbedPane.getTabCount();
		for(int checkTabOn = 0; checkTabOn < allTabs; checkTabOn++) {
			if(tabbedPane.getTitleAt(checkTabOn).contentEquals(tabName)){
				tabbedPane.setSelectedIndex(0);
				tabbedPane.setSelectedIndex(checkTabOn);
			}
		}
	}
	
	private void log_out(ActionEvent e) {
		JComponent comp = (JComponent) e.getSource();
		Window window = SwingUtilities.getWindowAncestor(comp);
		window.dispose();
		System.exit(0);
	}
	
	public static IngredientManager getInventory() {
		Data_Access DOA = new Data_Access();
    	ArrayList<Ingredient> getListIngredients = DOA.getIngredientsLists();
		return new IngredientManager(getListIngredients);
	}
	
	public static FoodManager getFoodInventory() {
		Data_Access DOA = new Data_Access();
		ArrayList<Food> foods = DOA.getFoodMenu();
		return new FoodManager(foods);
	}
	
	public void stateChanged(ChangeEvent e, User user) {
		// TODO Auto-generated method stub
		if(Ingredient_Manager != null) {
			Ingredient_Manager.removeAll();
		}
		
		if(Order_Item != null) {
			Order_Item.removeAll();
		}
		
		if(Food_Manager != null) {
			Food_Manager.removeAll();
		}
		
		JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
        int selectedIndex = tabbedPane.getSelectedIndex();
        if(tabbedPane.getTitleAt(selectedIndex).contentEquals("Ingredient Manager")) {
        	
        	// Insert into manager
        	IngredientManager inventory = getInventory();
        	if(inventory != null & inventory.getDatabase().size() > 0) {
        		switch(user.getPermission()) {
        			case 0:
        					for(Map.Entry<String, Ingredient> ingredient: inventory.getDatabase().entrySet()) {
        						Ingredient_Manager.add(displayIngredients(ingredient.getValue()));
        					}
        				break;
        			
        		}
        	} else {
        		JLabel Empty_States_Text = new JLabel("There are no ingredients at the moment");
        		Empty_States_Text.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        		Empty_States_Text.setHorizontalAlignment(SwingConstants.CENTER);
        		Ingredient_Manager.add(Empty_States_Text);
        	}
        } else if(tabbedPane.getTitleAt(selectedIndex).contentEquals("Order Item")) {

        } else if(tabbedPane.getTitleAt(selectedIndex).contentEquals("Food Manager")) {
    		// Insert into manager
        	try {
        		FoodManager foodInventory = getFoodInventory(); 
        		if(foodInventory != null & foodInventory.getDatabase().size()> 0) {
            		switch(user.getPermission()) {
            			case 0:
            					for(Map.Entry<String, Food> food: foodInventory.getDatabase().entrySet()) {
            						Food_Manager.add(displayFoods(food.getValue()));
            					}
            				break;
            			
            		}
            	}
        	}catch(Exception exception) {
        		JLabel Empty_States_Text = new JLabel("There are no food(s) available at the moment");
        		Empty_States_Text.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        		Empty_States_Text.setHorizontalAlignment(SwingConstants.CENTER);
        		Food_Manager.add(Empty_States_Text);
        	}
        }
	}
	
	public JPanel displayIngredients(Ingredient ingredient) {
		JPanel inventory_items = new JPanel();
		inventory_items.setLayout(new BoxLayout(inventory_items, BoxLayout.Y_AXIS));
		
		JPanel textArea = new JPanel();
		inventory_items.add(textArea);
		
		JLabel ingredient_item_name = new JLabel("Name: "+ ingredient.getName());
		textArea.add(ingredient_item_name);
		ingredient_item_name.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		ingredient_item_name.setHorizontalAlignment(SwingConstants.CENTER);
		
		Component ingredient_item_space = Box.createVerticalStrut(20);
		inventory_items.add(ingredient_item_space);
		
		JPanel buttonArea = new JPanel();
		inventory_items.add(buttonArea);
		
		JButton ingredient_assign_button = new JButton("Edit");
		JButton ingredient_remove_button = new JButton("Remove");
		buttonArea.add(ingredient_assign_button);
		buttonArea.add(ingredient_remove_button);
		
		ingredient_assign_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Main.newPanel(new IngredientSetup(ingredient), "Edit Ingredient: " + ingredient.getName());
			}
			
		});
		
		ingredient_remove_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				IngredientSetup remove_ingredient = new IngredientSetup();
				remove_ingredient.removeIngredient(ingredient);
				Main.newPanel(remove_ingredient, "Remove Ingredient: " + ingredient.getName());
			}
			
		});
		
		return inventory_items;
	}
	
	public JPanel displayFoods(Food food) {
		JPanel food_items = new JPanel();
		food_items.setLayout(new BoxLayout(food_items, BoxLayout.Y_AXIS));
		
		JPanel textArea = new JPanel();
		food_items.add(textArea);
		
		JLabel ingredient_item_name = new JLabel("Name: "+ food.getFoodName());
		textArea.add(ingredient_item_name);
		ingredient_item_name.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		ingredient_item_name.setHorizontalAlignment(SwingConstants.CENTER);
		
		Component ingredient_item_space = Box.createVerticalStrut(20);
		food_items.add(ingredient_item_space);
		
		JPanel buttonArea = new JPanel();
		food_items.add(buttonArea);
		
		JButton ingredient_edit_button = new JButton("Edit");
		JButton ingredient_remove_button = new JButton("Remove");
		buttonArea.add(ingredient_edit_button);
		buttonArea.add(ingredient_remove_button);
		
		ingredient_edit_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FoodSetup addFood = new FoodSetup(food);
				Main.newPanel(addFood, "Edit Ingredient: " + food.getFoodName());
			}
			
		});
		
		ingredient_remove_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FoodManager foodManager = new FoodManager(food);
				JComponent comp = (JComponent) e.getSource();
				Window win = SwingUtilities.getWindowAncestor(comp);
				
				if(foodManager.removeFoodDB(food)) {
					refreshTab("Food Manager");
				} else {
					Main.newPanel(new Message("Error deleting food item from the list!", "Please try again on adding your food item to the list"), "ERROR");
				}
			}
			
		});
		
		return food_items;
	}
	
	public void design() {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				MamakOrderingSystem window = new MamakOrderingSystem(null);
				JFrame frame = new JFrame();
				frame.getContentPane().add(window);
				frame.getContentPane().setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
				frame.pack();
				frame.setSize(screenSize.width , screenSize.height);
				frame.setVisible(true);
			}
		});
	}
}
