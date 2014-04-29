package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.api.model.IIntervention;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.JsonSerializer;

public class Intervention implements IIntervention {

	private String _id;
	private String _rev;
	private String adresse;
	private String codeSinistre;
	private List<Moyen> moyens;
	private transient List<Intervenant> intervenants;
	private transient Codis codis;
	
	public Intervention(){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.adresse = "";
		this.codeSinistre = "";
		this.moyens = new ArrayList<Moyen>();
		this.intervenants = new ArrayList<Intervenant>();
	}
	
	public Intervention(String adresse, String codeSinistre){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.adresse = adresse;
		this.codeSinistre = codeSinistre;
		this.moyens = new ArrayList<Moyen>();
		this.intervenants = new ArrayList<Intervenant>();
	}
	
	public Intervention(String adresse, String codeSinistre, List<Moyen> moyens){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.adresse = "";
		this.codeSinistre = "";
		this.moyens = moyens;
		this.intervenants = new ArrayList<Intervenant>();
	}
	
	public Intervention(String adresse, String codeSinistre, List<Moyen> moyens, List<Intervenant> users){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.adresse = "";
		this.codeSinistre = "";
		this.moyens = moyens;
		this.intervenants = users;
	}

	/**
	 * @return the adresse
	 */
	@Override
	public String getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */
	@Override
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the codeSinistre
	 */
	@Override
	public String getCodeSinistre() {
		return codeSinistre;
	}

	/**
	 * @param codeSinistre the codeSinistre to set
	 */
	@Override
	public void setCodeSinistre(String codeSinistre) {
		this.codeSinistre = codeSinistre;
	}

	/**
	 * @return the moyens
	 */
	@Override
	public List<Moyen> getMoyens() {
		return moyens;
	}

	/**
	 * @param moyens the moyens to set
	 */
	@Override
	public void setMoyens(List<Moyen> moyens) {
		this.moyens = moyens;
	}

	/**
	 * @return the users
	 */
	@Override
	public List<Intervenant> getIntervenants() {
		return intervenants;
	}

	/**
	 * @param users the users to set
	 */
	@Override
	public void setIntervenants(List<Intervenant> intervenants) {
		this.intervenants = intervenants;
	}
	
	@Override
	public void addIntervenant(Intervenant intervenant) {
		this.intervenants.add(intervenant);
	}

	/**
	 * @return the codis
	 */
	public Codis getCodis() {
		return codis;
	}

	/**
	 * @param codis the codis to set
	 */
	public void setCodis(Codis codis) {
		this.codis = codis;
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
