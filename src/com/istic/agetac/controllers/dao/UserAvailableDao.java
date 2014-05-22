package com.istic.agetac.controllers.dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.model.UserAvailable;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.IPersistant;
import com.istic.sit.framework.couch.JsonSerializer;

public abstract class UserAvailableDao implements IPersistant {
	
	private static final String design = "agetacpp";
	private static final String view = "get_user_poubelle";

	@Override
	public void onResponse(JSONObject json){
		try {
			JSONArray rows = json.getJSONArray("rows");
			JSONObject value = rows.getJSONObject(0).getJSONObject("value");
			value.remove("type");
			UserAvailable userPoubelle = (UserAvailable) JsonSerializer.deserialize(UserAvailable.class, value);
			onResponse(userPoubelle);
		} catch (JSONException e) {
			Log.e("UserPoubelleDao", e.toString());
		}
	}
	
	public abstract void onResponse(UserAvailable users);

	@Override
	public abstract void onErrorResponse(VolleyError error); 

	@Override
	public String getId() {
		return null;
	}

	@Override
	public void setId(String id) {
		
	}

	@Override
	public String getRev() {
		return null;
	}

	@Override
	public void setRev(String rev) {
		
	}

	@Override
	public String getUrl(int method) {
		return DataBaseCommunication.BASE_URL + "_design/"+design+"/_view/"+view;
	}

	@Override
	public JSONObject getData() {
		return null;
	}

	@Override
	public void save() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void delete() {
		
	}
	
}
