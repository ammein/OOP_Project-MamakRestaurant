package view;

import javax.security.auth.callback.Callback;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import controller.UserController;
import model.User;

import java.awt.*;
import java.awt.event.*;
import javax.swing.GroupLayout.Alignment;

public class MamakSignUpMaster extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel signUpPanel;
	private JTextField username;
	private JPasswordField passwordField;
	private boolean signUpStatus;
	
	
	public MamakSignUpMaster() {
		setSize(379,297);
		JLabel SignUp_Title = new JLabel("SIGN UP");
		SignUp_Title.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		SignUp_Title.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel Username_Container = new JPanel();

		JPanel buttonArea = new JPanel();
		buttonArea.setLayout(new CardLayout(5, 5));

		JButton signUpButton = new JButton("Sign Up");
		signUpButton.setBackground(new Color(0, 0, 0, 0));
		buttonArea.add(signUpButton, "name_9843496864916");
		buttonArea.setBackground(new Color(0, 0, 0, 0));
		signUpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				signUp(e);
			}
			
		});
		
		Username_Container.setLayout(new BoxLayout(Username_Container, BoxLayout.X_AXIS));
		Username_Container.setBackground(new Color(0, 0, 0, 0));
		JLabel username_label = new JLabel("Username:");
		username_label.setHorizontalAlignment(SwingConstants.LEFT);
		Username_Container.add(username_label);

		Component spaceUsername = Box.createHorizontalStrut(20);
		Username_Container.add(spaceUsername);

		username = new JTextField();
		username.setBorder(null);
		Username_Container.add(username);
		username.setColumns(10);
		
		JLabel info_signup = new JLabel("<html><div style=\"width:80% text-align=\"center\"\">Please sign up your new account by setting up your new username & password that will be created as \"Master\" user of this application</div></html>");
		info_signup.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel Password_Container = new JPanel();
		Password_Container.setLocation(63, 236);
		Password_Container.setLayout(new BoxLayout(Password_Container, BoxLayout.X_AXIS));

		JLabel password_label = new JLabel("Password:");
		Password_Container.add(password_label);
		Password_Container.setBackground(new Color(0, 0, 0, 0));

		Component spaceUsername_1 = Box.createHorizontalStrut(20);
		Password_Container.add(spaceUsername_1);
		
		passwordField = new JPasswordField();
		passwordField.setBorder(null);
		Password_Container.add(passwordField);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(SignUp_Title, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
				.addComponent(info_signup, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
				.addComponent(Username_Container, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
				.addComponent(Password_Container, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
				.addComponent(buttonArea, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(1)
					.addComponent(SignUp_Title, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
					.addGap(20)
					.addComponent(info_signup, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(20)
					.addComponent(Username_Container, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(20)
					.addComponent(Password_Container, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(20)
					.addComponent(buttonArea, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(1))
		);
		setLayout(groupLayout);
	}
	
	public void signUp(ActionEvent e) {
		User newUser = new User(username.getText(), passwordField.getPassword());
		newUser.setPermission(0);
		boolean createUserStatus = new UserController().createUser(newUser);
		if(createUserStatus) {
			signUpStatus = true;
			JComponent comp = (JComponent) e.getSource();
			Window win = SwingUtilities.getWindowAncestor(comp);
			win.dispose();
			Main.newPanel(new MamakLogin(), "Login");
		}else {
			signUpStatus = false;
			System.out.println("Unsuccessful sign up new user");
		}
	}

	public boolean isSignUpStatus() {
		return signUpStatus;
	}

	public void design() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					JFrame mainFrame = new JFrame();
					MamakSignUpMaster window = new MamakSignUpMaster();
					mainFrame.getContentPane().add(window.signUpPanel);
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
