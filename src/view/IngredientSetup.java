package view;

import javax.swing.*;
import javax.swing.event.*;

import controller.IngredientManager;
import model.Ingredient;
import model.Quantity;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

public class IngredientSetup extends JPanel implements ActionListener {
	private JTextField Ingredient_Name;
	private JSpinner Quantity;
	private JButton add_more;
	private JLabel title;
	
	public IngredientSetup() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel Button_Area = new JPanel();
		FlowLayout fl_Button_Area = (FlowLayout) Button_Area.getLayout();
		fl_Button_Area.setAlignment(FlowLayout.RIGHT);
		add(Button_Area, BorderLayout.SOUTH);
		
		add_more = new JButton("Add");
		add_more.setForeground(new Color(0, 0, 0));
		add_more.setBackground(new Color(90, 50, 215));
		add_more.addActionListener(this);
		Button_Area.add(add_more);
		
		JButton remove_all = new JButton("Cancel");
		remove_all.addActionListener(this);
		Button_Area.add(remove_all);
		
		JPanel Title_Area = new JPanel();
		add(Title_Area, BorderLayout.NORTH);
		
		title = new JLabel("Add Ingredient");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		Title_Area.add(title);
		
		JPanel container = new JPanel();
		add(container, BorderLayout.CENTER);
		container.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel First_Row = new JPanel();
		container.add(First_Row);
		First_Row.setLayout(new BoxLayout(First_Row, BoxLayout.X_AXIS));
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		First_Row.add(lblName);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		First_Row.add(horizontalStrut);
		
		Ingredient_Name = new JTextField();
		First_Row.add(Ingredient_Name);
		Ingredient_Name.setColumns(10);
		
		JPanel Second_Row = new JPanel();
		container.add(Second_Row);
		
		JLabel label_name_1 = new JLabel("Quantity");
		label_name_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		Second_Row.add(label_name_1);
		
		SpinnerModel model = new SpinnerNumberModel(0, 0, 100, 1);     
		Quantity = new JSpinner(model);
		Second_Row.add(Quantity);
	}
	
	public IngredientSetup(Ingredient ingredient) {
		this();
		Ingredient_Name.setText(ingredient.getName());
		Ingredient_Name.setEnabled(false);
		Quantity.setValue(ingredient.getQuantity());
		add_more.setText("Edit");
		title.setText("Edit Ingredient");
	}
	
	public void removeIngredient(Ingredient ingredient) {
		Ingredient_Name.setText(ingredient.getName());
		Ingredient_Name.setEnabled(false);
		Quantity.setValue(ingredient.getQuantity());
		Quantity.setEnabled(false);
		add_more.setText("Remove");
		title.setText("Remove this Ingredient?");
	}

	public void actionPerformed(ActionEvent e) {
		JComponent comp = (JComponent) e.getSource();
		Window win = SwingUtilities.getWindowAncestor(comp);
		Ingredient ingredient = new Ingredient(Ingredient_Name.getText());
		IngredientManager IM = new IngredientManager(ingredient);
		
		System.out.println();
		if(e.getActionCommand() != null) {
			switch(e.getActionCommand()) {
				case "Cancel":
						win.dispose();
					break;
					
				case "Add":
						ingredient.setQuantity(new Quantity((Integer) Quantity.getValue()));
						
						if(IM.insertDB()) {
							win.dispose();
							MamakOrderingSystem.refreshTab("Ingredient Manager");
						}else {
							Ingredient_Name.setText("");
							Quantity.setValue(0);
							Main.newPanel(new Message("Error adding ingredient to list!", "Try add the ingredient again"), "Error");
						}
					break;
					
				case "Edit":
					ingredient.setQuantity(new Quantity((Integer) Quantity.getValue()));
					IM.setDatabase(new HashMap<String, Ingredient>(){{
						put(ingredient.getName(), ingredient);
					}});
					for(Map.Entry<String, Ingredient> get_ingredient: IM.getDatabase().entrySet()) {
						if(get_ingredient.getKey().contentEquals(Ingredient_Name.getText())) {
							if(IM.editDB(get_ingredient.getKey())) {
								win.dispose();
								MamakOrderingSystem.refreshTab("Ingredient Manager");
							}else {
								Ingredient_Name.setText("");
								Quantity.setValue(0);
								Main.newPanel(new Message("Error edit ingredient to list!", "Try edit the ingredient again"), "Error");
							}
						}
					}
				break;
				
				case "Remove":
						if(IM.removeDB(ingredient.getName())) {
							win.dispose();
							MamakOrderingSystem.refreshTab("Ingredient Manager");
						} else {
							Main.newPanel(new Message("Error removing the ingredient from the list", "Try remove the ingredient again"), "Error");
						}
					break;
			}
		}
	}
	
	public void design() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				JFrame frame = new JFrame();
				frame.getContentPane().add(new IngredientSetup());
				frame.pack();
				frame.setVisible(true);
			}
			
		});
	}
}
