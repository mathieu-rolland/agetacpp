package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.IPersistant;
import com.istic.sit.framework.couch.JsonSerializer;

public class EnvironnementsStatic implements IPersistant {

	private String _id;
	private String _rev;
	private List<EnvironnementStatic> listEnvironnement;
	
	public EnvironnementsStatic(){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.listEnvironnement = new ArrayList<EnvironnementStatic>();
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
			Log.v("agetacpp - EnvironnementStatic", error.getMessage());
			if(error.getCause() != null){
				Log.v("agetacpp - EnvironnementStatic", error.getCause().toString());
			}
		}
		else{
			Log.v("agetacpp - EnvironnementStatic", error.toString());
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
	 * @return the listEnvironnement
	 */
	public List<EnvironnementStatic> getListEnvironnement() {
		return listEnvironnement;
	}

	/**
	 * @param listEnvironnement the listEnvironnement to set
	 */
	public void setListEnvironnement(List<EnvironnementStatic> listEnvironnement) {
		this.listEnvironnement = listEnvironnement;
	}


}
