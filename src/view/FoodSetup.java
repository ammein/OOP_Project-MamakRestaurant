package view;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.text.*;

import controller.FoodManager;
import controller.IngredientManager;
import model.Data_Access;
import model.Food;
import model.Ingredient;
import model.Quantity;

import java.awt.*;
import java.awt.event.ActionListener;
import java.math.*;
import java.text.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class FoodSetup extends JPanel {
	private JTextField Food_Name;
	private JList IngredientList;
	private JFormattedTextField price;
	private JButton add_more;
	private JLabel title;
	private String splitLater;
	
	@SuppressWarnings("unchecked")
	public FoodSetup() {
		Data_Access DOA = new Data_Access();
		ArrayList<Ingredient> ingredients = DOA.getIngredientsLists();
		splitLater = "";
		for(Ingredient ingredient: ingredients) {
			splitLater = splitLater.concat(ingredient.getName()) + ",";
		}
		final String[] split= splitLater.split(",");
		setLayout(new BorderLayout(0, 0));
		
		JPanel Button_Area = new JPanel();
		FlowLayout fl_Button_Area = (FlowLayout) Button_Area.getLayout();
		fl_Button_Area.setAlignment(FlowLayout.RIGHT);
		add(Button_Area, BorderLayout.SOUTH);
		
		add_more = new JButton("Add");
		add_more.setForeground(new Color(0, 0, 0));
		add_more.setBackground(new Color(90, 50, 215));
		add_more.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
				IngredientManager inventoryData = MamakOrderingSystem.getInventory();
				for(Map.Entry<String, Ingredient> ingredient: inventoryData.getDatabase().entrySet()) {
					for(int i = 0; i < IngredientList.getSelectedValuesList().size(); i++) {
						if(IngredientList.getSelectedValuesList().get(i).toString().contentEquals(ingredient.getKey())) {
							ingredients.add(ingredient.getValue());
						}
					}
				}
				
				Food newFood = new Food(Food_Name.getText(), ingredients);
				newFood.setPrice(new BigDecimal(price.getText().replace(",","")));
				addFood(newFood, e);
			}
			
		});
		Button_Area.add(add_more);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComponent comp = (JComponent) e.getSource();
				Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
			}
			
		});
		Button_Area.add(cancel);
		
		JPanel Title_Area = new JPanel();
		add(Title_Area, BorderLayout.NORTH);
		
		title = new JLabel("Add Food Item");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		Title_Area.add(title);
		
		JPanel container = new JPanel();
		add(container, BorderLayout.CENTER);
		
		JPanel First_Row = new JPanel();
		First_Row.setLayout(new BoxLayout(First_Row, BoxLayout.X_AXIS));
		
		JLabel food_name_label = new JLabel("Name:");
		food_name_label.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		First_Row.add(food_name_label);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		First_Row.add(horizontalStrut);
		
		Food_Name = new JTextField();
		First_Row.add(Food_Name);
		Food_Name.setColumns(10);
		
		JPanel Second_Row = new JPanel();
		Second_Row.setLayout(new BoxLayout(Second_Row, BoxLayout.X_AXIS));
		
		JLabel ingredient_selection = new JLabel("Choose Ingredient(s):");
		Second_Row.add(ingredient_selection);
		ingredient_selection.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		
		Component ingredient_space = Box.createHorizontalStrut(20);
		Second_Row.add(ingredient_space);
		
		IngredientList = new JList(split);
		IngredientList.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(IngredientList);
		Second_Row.add(scrollPane);
		GroupLayout gl_container = new GroupLayout(container);
		gl_container.setHorizontalGroup(
			gl_container.createParallelGroup(Alignment.LEADING)
				.addComponent(First_Row, GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
				.addComponent(Second_Row, GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
		);
		gl_container.setVerticalGroup(
			gl_container.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_container.createSequentialGroup()
					.addComponent(First_Row, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(Second_Row, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel price_container = new JPanel();
		First_Row.add(price_container);
		price_container.setLayout(new BoxLayout(price_container, BoxLayout.X_AXIS));
		
		JLabel price_label = new JLabel("Price:");
		price_label.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		price_container.add(price_label);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		price_container.add(horizontalStrut_1);
		
		price = new JFormattedTextField(new BigDecimal(0.00));
		price.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("#,###.00"))));
		price_container.add(price);
		container.setLayout(gl_container);
	}
	
	public FoodSetup(Food food) {
		this();
		Food_Name.setText(food.getFoodName());
		price.setText(food.getPrice().toString());
		int[] select = new int[food.getIngredient().size()];
		for(int i = 0; i < food.getIngredient().size(); i++) {
			String[] split= splitLater.split(",");
			int ingredientCount = 0;
			for(String ingredientName: split) {
				if(food.getIngredient().get(i).getName().contentEquals(ingredientName)) {
					select[i] = ingredientCount;
				}
				ingredientCount++;
			}
		}
		
		IngredientList.setSelectedIndices(select);
	}
	
	public void addFood(Food food, ActionEvent e) {
		FoodManager foodManager = new FoodManager(food);
		JComponent comp = (JComponent) e.getSource();
		Window win = SwingUtilities.getWindowAncestor(comp);
		if(foodManager.addFoodDB()) {
			win.dispose();
		} else {
			Food_Name.setText("");
			IngredientList.clearSelection();
			Main.newPanel(new Message("Error adding food item to the list!", "Please try again on adding your food item to the list"), "ERROR");
		}
	}
	
	public void removeFood(Food food, ActionEvent e) {
		FoodManager foodManager = new FoodManager(food);
		JComponent comp = (JComponent) e.getSource();
		Window win = SwingUtilities.getWindowAncestor(comp);
		
		if(foodManager.addFoodDB()) {
			win.dispose();
		} else {
			Food_Name.setText("");
			IngredientList.clearSelection();
			Main.newPanel(new Message("Error adding food item to the list!", "Please try again on adding your food item to the list"), "ERROR");
		}
	}
	
	public void design() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				JFrame frame = new JFrame();
				frame.getContentPane().add(new FoodSetup());
				frame.pack();
				frame.setVisible(true);
			}
			
		});
	}
}
