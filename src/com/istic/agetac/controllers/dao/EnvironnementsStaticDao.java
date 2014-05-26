package com.istic.agetac.controllers.dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.model.EnvironnementsStatic;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.IPersistant;
import com.istic.sit.framework.couch.JsonSerializer;

public abstract class EnvironnementsStaticDao implements IPersistant {

	private static final String design = "agetacpp";
	private static final String view = "get_environnements_static";
	
	@Override
	public void onResponse(JSONObject json) {
		try {
			JSONArray rows = json.getJSONArray("rows");
			JSONObject value = rows.getJSONObject(0).getJSONObject("value");
			value.remove("type");
			EnvironnementsStatic environnementsStatic = (EnvironnementsStatic) JsonSerializer.deserialize(EnvironnementsStatic.class, value);
			onResponse(environnementsStatic);
		} catch (JSONException e) {
			Log.e("EnvironnementsStaticDao", e.toString());
		}
	}
	
	public abstract void onResponse(EnvironnementsStatic environnementsStatic);
	
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
