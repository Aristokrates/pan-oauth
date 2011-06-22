package org.pan.oauth.model;

/**
 * Hold data subject of interest
 * 
 * @author Pance.Isajeski
 *
 */
public class UserModel {
	
	private String name;
	
	private String username;
	
	private String email;

	public UserModel(String name, String username, String email) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String surname) {
		this.username = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
