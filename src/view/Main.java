package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import model.*;

public class Main {
	
	private static MamakSignUpMaster signup;
	private static MamakLogin login;
	
	public static JFrame mainPanel(JPanel panel) {
		JFrame mainFrame = new JFrame("Restaurant Ordering System");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.getContentPane().add(panel);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setSize(screenSize.width , screenSize.height);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		return mainFrame;
	}
	
	public static JFrame newPanel(JPanel panel, String title) {
		JFrame newFrame = new JFrame(title);
		newFrame.getContentPane().add(panel);
		
		if(title.contentEquals("Login") || title.contentEquals("Sign Up")) {
			newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
		newFrame.pack();
		newFrame.setLocationRelativeTo(null);
		newFrame.setAlwaysOnTop(true);
		newFrame.setVisible(true);
		return newFrame;
	}
	
	public static void main(String[] args) {
		// First step always check user;
		Data_Access DOA = new Data_Access();
		
		UIManager.getLookAndFeelDefaults().put("Panel.border", new EmptyBorder(5, 5, 5, 5));
		UIManager.getLookAndFeelDefaults().put("Panel.background", new Color(240, 248, 255));
		UIManager.getLookAndFeelDefaults().put("TextField.border", new EmptyBorder(0,0,0,0));
		UIManager.getLookAndFeelDefaults().put("PasswordField.border", new EmptyBorder(0,0,0,0));
		UIManager.getLookAndFeelDefaults().put("Button.font", new Font("Lucida Grande", Font.PLAIN, 18));
		
		int numberOfUsers = DOA.checkAllUsersInDB();
		if(numberOfUsers > 0) {
			login = new MamakLogin();
			newPanel(login, "Login");
		}else if(numberOfUsers == 0) {
			signup = new MamakSignUpMaster();
			newPanel(signup, "Sign Up");
		}
	}
}