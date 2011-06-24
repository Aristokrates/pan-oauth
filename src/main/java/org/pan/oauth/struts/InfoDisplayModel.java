package org.pan.oauth.struts;

import org.apache.struts.action.ActionForm;

/**
 * Holds data pulled from external services
 * 
 * @author Pance.Isajeski
 *
 */
public class InfoDisplayModel extends ActionForm {
	
	private static final long serialVersionUID = 2470355682032905527L;

	private String name;
	
	private String username;
	
	private String email;

	public InfoDisplayModel() {
		super();
	}

	public InfoDisplayModel(String name, String username, String email) {
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
		username = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
