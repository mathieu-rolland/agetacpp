/**
 * 
 */
package com.istic.agetac.api.model;

/**
 * @author Christophe
 *
 */
public interface IUser {

	public String getName();
	public void setName(String name);
	public String getUsername();
	public void setUsername(String username);
	public String getPassword();
	public void setPassword(String password);
	public String getRole();
	public void setRole(String role);
}
