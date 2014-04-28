/**
 * 
 */
package com.istic.agetac.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.api.model.IUser;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.IPersistant;
import com.istic.sit.framework.couch.JsonSerializer;

/**
 * @author Christophe
 *
 */
public class User implements IUser, IPersistant {

	private String _id;
	private String _rev;
	private String name;
	private String username;
	private String password;
	private Role role;
	
	public User(String name, String username, Role role){
		this._id = "";
		this._rev  = "";
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

	@Override
	public void onResponse(JSONObject json) {
		Log.i("User - RESPONSE", json.toString());
		try {
			if(_id.equals("")){
				// the Entity has just been created in database 
				_id = (String) json.get("id");
			}
			_rev = (String) json.get("rev");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		if(error.getMessage() != null){
			Log.v("agetacpp - User", error.getMessage());
			if(error.getCause() != null){
				Log.v("agetacpp - User", error.getCause().toString());
			}
		}
		else{
			Log.v("agetacpp - User", "error");
		}
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public void setId(String id) {
		this._id = id;
	}

	@Override
	public String getRev() {
		return _rev;
	}

	@Override
	public void setRev(String rev) {
		this._rev = rev;
	}

	@Override
	public String getUrl(int method) {
		return DataBaseCommunication.BASE_URL + _id;
	}

	@Override
	public JSONObject getData() {
		try {
			return JsonSerializer.serialize(this);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save() {
		if(this.getId().isEmpty()){
			DataBaseCommunication.sendPost(this);
		}
		else{
			DataBaseCommunication.sendPut(this);
		}
	}

	@Override
	public void update() {
		DataBaseCommunication.sendPut(this);
	}

	@Override
	public void delete() {
			DataBaseCommunication.sendDelete(this);
	}
}
