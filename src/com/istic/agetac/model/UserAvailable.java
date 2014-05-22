package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.IPersistant;
import com.istic.sit.framework.couch.JsonSerializer;

public class UserAvailable implements IPersistant {
	
	private String _id;
	private String _rev;
	private List<User> users;
	private transient Intervention intervention;
	
	public UserAvailable(){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.users = new ArrayList<User>();
	}

	@Override
	public void onResponse(JSONObject json) {
		try {
			_rev = (String) json.get("rev");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		if(error.getMessage() != null){
			Log.v("agetacpp - UserPoubelle", error.getMessage());
			if(error.getCause() != null){
				Log.v("agetacpp - UserPoubelle", error.getCause().toString());
			}
		}
		else{
			Log.v("agetacpp - UserPoubelle", error.toString());
		}
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public void setId(String id) {
		_id = id;
	}

	@Override
	public String getRev() {
		return _rev;
	}

	@Override
	public void setRev(String rev) {
		_rev = rev;
	}

	@Override
	public String getUrl(int method) {
		if(method == Request.Method.DELETE){
			return DataBaseCommunication.BASE_URL + _id + "?rev=" + _rev;
		}
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
		DataBaseCommunication.sendPut(this);
	}

	@Override
	public void update() {
		DataBaseCommunication.sendPut(this);
	}

	@Override
	public void delete() {
		DataBaseCommunication.sendDelete(this);
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public void addUser(User user){
		this.users.add(user);
	}
	
	public void removeUser(User user){
		this.users.remove(user);
	}

	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}
	
	public void removeIntervenants(){
		List<User> intervenants = new ArrayList<User>();
		for(User u : users){
			if(u.getRole().equals(Role.intervenant)){
				intervenants.add(u);
			}
		}
		users.removeAll(intervenants);
	}
}
