package model;

public class User {
	private String username;
	private char[] password;
	private String fullName;
	private int permission;
	/**
	 * @param username
	 * @param password
	 * @param fullName
	 */
	public User(String username, char[] password) {
		this.username = username;
		this.password = password;
		this.fullName = "";
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public char[] getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(char[] password) {
		this.password = password;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
	public int getPermission() {
		return permission;
	}
	
	/**
	 * 
	 * @param permission value to determine the permission of accessing the apps. See what's allow for value to set permission for
	 * {@value 0} master 
	 */
	public void setPermission(int permission) {
		this.permission = permission;
	}
}
