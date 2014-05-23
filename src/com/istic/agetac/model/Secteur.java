package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.istic.agetac.api.model.ISecteur;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.IPersistant;
import com.istic.sit.framework.couch.JsonSerializer;

/**
 * Classe Secteur : Mod�le qui repr�sente un secteur (i.e. SAP/INC/ALIM/...)
 * 
 * @author Anthony LE MEE - 10003134
 */
public class Secteur implements ISecteur, IPersistant, Parcelable { 
	
	
	private String _id;
	private String _rev;
	private boolean lock;
	private String libelle;
	private String color;
	private List<Moyen> moyens;

	private transient Intervention intervention;
	
	public Secteur()
	{
		color = "";
		libelle = "";
		lock = false;
		_id = "";
		_rev = "";
		moyens = new ArrayList<Moyen>();
	}
	
	public Secteur(Parcel source) {
		String serializedJson = source.readString();
		try {
			Secteur secteur = (Secteur) JsonSerializer.deserialize(Secteur.class, new JSONObject(serializedJson));
			_id = "";
			_rev = "";
			this.setId( secteur.getId() );
			this.setRev(secteur.getRev());
			this.lock = secteur.lock;
			this.libelle = secteur.libelle;
			this.color = secteur.color;
			moyens = new ArrayList<Moyen>();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void lock() {
		lock = true;
	}

	@Override
	public void unlock() {
		lock = false;
	}

	@Override
	public void save() {
//		DataBaseCommunication.sendPost(this);
		if( !intervention.getSecteurs().contains(this) ) intervention.getSecteurs().add(this);
		intervention.save();
	}

	@Override
	public void update() {
//		DataBaseCommunication.sendPut(this);
		intervention.update();
	}

	@Override
	public void onResponse(JSONObject json) {
		try {
			if( ((Boolean) json.get("ok")) == true ){
				this._id = json.getString("id");
				this._rev = json.getString("rev");
				this.color = json.getString("color");
				this.libelle = json.getString("libelle");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Log.e("Agetac++","[Failed : "+ 
				(error.networkResponse == null ? "Unknown error" : 
				"Error HTTP("+error.networkResponse.statusCode +")" ) 
		+" ]");
	}

	@Override
	public String getUrl(int method) {
		if( method ==  Request.Method.DELETE ){
			return DataBaseCommunication.BASE_URL + _id + "?rev="+_rev;
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
	public void delete() {
		intervention.delete(this);
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
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		try {
			String message = JsonSerializer.serialize(this).toString();
			dest.writeString(message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static final Parcelable.Creator<Secteur> CREATOR = new Parcelable.Creator<Secteur>()
	{
		@Override
		public Secteur createFromParcel(Parcel source)
		{
			return new Secteur(source);
		}

		@Override
		public Secteur[] newArray(int size)
		{
			return new Secteur[size];
		}
	};

	@Override
	public boolean isLock() {
		return lock;
	}

	@Override
	public void setName(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String getName() {
		return this.libelle;
	}

	@Override
	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String getColor() {
		return this.color;
	}
	
	public String toString() {
		return this.libelle;
	}

	@Override
	public void addMoyen(Moyen moyen) {
		this.moyens.add(moyen);
	}

	@Override
	public List<Moyen> getMoyens() {
		return moyens;
	}
	
	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}
	
}
