/**
 * 
 */
package com.istic.agetac.model;

import java.util.UUID;

import com.istic.agetac.api.model.IUser;

/**
 * @author Christophe
 *
 */
public abstract class User implements IUser {

	private String _id;
	private String name;
	private String username;
	private String password;
	private Role role;
	private transient Intervention intervention;
	
	public User(String name, String username, Role role){
		this._id = UUID.randomUUID().toString();
		this.name = name;
		this.username = username;
		this.role = role;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	public String getId() {
		return _id;
	}

	public void setId(String id) {
		this._id = id;
	}


	public abstract void save();

	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

}
