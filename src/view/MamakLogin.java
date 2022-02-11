package view;

import javax.swing.*;

import controller.UserController;
import model.User;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MamakLogin extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField username;
	private JPasswordField passwordField;

	/**
	 * Create the panel.
	 */
	public MamakLogin() {
		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel buttonArea = new JPanel();
		buttonArea.setLayout(new CardLayout(5, 5));

		JButton login = new JButton("Login");
		login.setFont(UIManager.getFont("Button.font"));
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login(e);
			}
		});
		login.setBackground(new Color(0, 0, 0, 0));
		buttonArea.add(login, "name_9843496864916");
		buttonArea.setBackground(new Color(0, 0, 0, 0));
		setLayout(new GridLayout(0, 1, 20, 20));
		add(lblNewLabel);
		
				JPanel Username_Container = new JPanel();
				add(Username_Container);
				Username_Container.setLayout(new BoxLayout(Username_Container, BoxLayout.X_AXIS));
				Username_Container.setBackground(new Color(0, 0, 0, 0));
				
						JLabel username_label = new JLabel("Username:");
						username_label.setHorizontalAlignment(SwingConstants.LEFT);
						Username_Container.add(username_label);
						
								Component spaceUsername = Box.createHorizontalStrut(20);
								Username_Container.add(spaceUsername);
								
										username = new JTextField();
										Username_Container.add(username);
										username.setColumns(10);
		
				JPanel Password_Container = new JPanel();
				add(Password_Container);
				Password_Container.setLocation(63, 236);
				Password_Container.setLayout(new BoxLayout(Password_Container, BoxLayout.X_AXIS));
				
						JLabel password_label = new JLabel("Password:");
						Password_Container.add(password_label);
						Password_Container.setBackground(new Color(0, 0, 0, 0));
						
								Component spaceUsername_1 = Box.createHorizontalStrut(20);
								Password_Container.add(spaceUsername_1);
								
								passwordField = new JPasswordField();
								Password_Container.add(passwordField);
		add(buttonArea);
	}
	
	private void login(ActionEvent e) {
		UserController userControl = new UserController();
		User getUser = userControl.getUser(new User(username.getText(), passwordField.getPassword()));
		JComponent comp = (JComponent) e.getSource();
		Window win = SwingUtilities.getWindowAncestor(comp);
		if(getUser != null) {
			System.out.println("User LoggedIn");
			win.dispose();
			Main.mainPanel(new MamakOrderingSystem(getUser));
		} else {
			username.setText("");
			passwordField.setText("");
			Message message = new Message("Login Error", "User not available to login. Please try to log in again.");
			Main.newPanel(message, "Something went wrong");
			System.out.println("User not available");
		}
	}

	public void design() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					JFrame mainFrame = new JFrame();
					MamakLogin window = new MamakLogin();
					mainFrame.getContentPane().add(window);
					mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					mainFrame.pack();
					mainFrame.setLocationRelativeTo(null);
					mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}
}
