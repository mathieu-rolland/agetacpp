/**
 * 
 */
package com.istic.agetac.api.model;

import com.istic.sit.framework.couch.IPersistant;

/**
 * @author Christophe
 *
 */
public interface IUser extends IPersistant {

	public enum Role {codis, intervenant}
	public String getName();
	public void setName(String name);
	public String getUsername();
	public void setUsername(String username);
	public String getPassword();
	public void setPassword(String password);
	public Role getRole();
	public void setRole(Role role);
}
