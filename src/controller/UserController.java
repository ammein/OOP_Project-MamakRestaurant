package controller;

import model.Data_Access;
import model.User;

public class UserController {
	private Data_Access DOA;

	public UserController() {
		this.DOA = new Data_Access();
	}

	public boolean createUser(User user) {
		String encryptedPassword = DOA.encryptPass(new String(user.getPassword()));
		user.setPassword(encryptedPassword.toCharArray());
		return DOA.createUser(user);
	}

	public User getUser(User user) {
		String encryptedPassword = DOA.encryptPass(new String(user.getPassword()));
		user.setPassword(encryptedPassword.toCharArray());
		return this.DOA.getUser(user);
	}
}
