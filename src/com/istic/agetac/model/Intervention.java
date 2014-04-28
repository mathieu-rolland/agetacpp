package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.api.model.IIntervention;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.IPersistant;
import com.istic.sit.framework.couch.JsonSerializer;

public class Intervention implements IIntervention, IPersistant {

	private String _id;
	private String _rev;
	private String adresse;
	private String codeSinistre;
	private List<Moyen> moyens;
	
	public Intervention(){
		this._id = "";
		this._rev = "";
		this.adresse = "";
		this.codeSinistre = "";
		this.moyens = new ArrayList<Moyen>();
	}
	
	public Intervention(String adresse, String codeSinistre){
		this._id = "";
		this._rev = "";
		this.adresse = adresse;
		this.codeSinistre = codeSinistre;
		this.moyens = new ArrayList<Moyen>();
	}
	
	public Intervention(String adresse, String codeSinistre, List<Moyen> moyens){
		this._id = "";
		this._rev = "";
		this.adresse = "";
		this.codeSinistre = "";
		this.moyens = moyens;
	}

	/**
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the codeSinistre
	 */
	public String getCodeSinistre() {
		return codeSinistre;
	}

	/**
	 * @param codeSinistre the codeSinistre to set
	 */
	public void setCodeSinistre(String codeSinistre) {
		this.codeSinistre = codeSinistre;
	}

	/**
	 * @return the moyens
	 */
	public List<Moyen> getMoyens() {
		return moyens;
	}

	/**
	 * @param moyens the moyens to set
	 */
	public void setMoyens(List<Moyen> moyens) {
		this.moyens = moyens;
	}

	@Override
	public void onResponse(JSONObject json) {
		Log.i("User - RESPONSE", json.toString());
		try {
			if(_id.equals("")){
				// the Intervention has just been created in database 
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
			Log.v("agetacpp - Intervention", error.getMessage());
			if(error.getCause() != null){
				Log.v("agetacpp - Intervention", error.getCause().toString());
			}
		}
		else{
			Log.v("agetacpp - Intervention", error.toString());
		}
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
}
