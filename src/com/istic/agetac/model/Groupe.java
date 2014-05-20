package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.api.model.IGroupe;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.JsonSerializer;

public class Groupe implements IGroupe {

	private String _id;
	private String _rev;
	private String nom;
	private List<Moyen> moyens;
	
	public Groupe(String nom){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.nom = nom;
		this.moyens = new ArrayList<Moyen>();
	}
	
	public Groupe(String nom, List<Moyen> moyens){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.nom = nom;
		this.moyens = moyens;
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
		return this._rev;
	}

	@Override
	public void setRev(String rev) {
		this._rev = rev;
	}

	@Override
	public String getUrl(int method) {
		return DataBaseCommunication.BASE_URL + this._id;
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
		if(this.getId().isEmpty()) {
			Log.e("Groupe", "_id ne doit pas être vide !");
		}
		else {
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

	@Override
	public void onResponse(JSONObject json) {
		Log.i("Groupe - RESPONSE", json.toString());
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
			Log.v("agetacpp - Groupe", error.getMessage());
			if(error.getCause() != null){
				Log.v("agetacpp - Groupe", error.getCause().toString());
			}
		}
		else{
			Log.v("agetacpp - Groupe", "error");
		}
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public List<Moyen> getMoyens() {
		return this.moyens;
	}

	@Override
	public void setMoyens(List<Moyen> moyens) {
		this.moyens = moyens;
	}

	@Override
	public void addMoyen(Moyen moyen) {
		this.moyens.add(moyen);
	}

	@Override
	public void deleteMoyen(Moyen moyen) {
		this.moyens.remove(moyen);
	}

	@Override
	public void addMoyens(List<Moyen> moyens) {
		this.moyens.addAll(moyens);
	}

}
