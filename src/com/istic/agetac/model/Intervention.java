package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.api.model.IIntervention;
import com.istic.agetac.api.model.IMessage;
import com.istic.agetac.pattern.observer.Observer;
import com.istic.agetac.pattern.observer.Subject;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.IPersistant;
import com.istic.sit.framework.couch.JsonSerializer;

public class Intervention implements IIntervention, Subject {

	private String _id;
	private String _rev;
	private String adresse;
	private String codeSinistre;
	private List<Moyen> moyens;
	private transient List<Intervenant> intervenants;
	private transient Codis codis;
	private transient List<Observer> observers;
	private transient List<IMessage> messages;
	
	public Intervention(){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.adresse = "";
		this.codeSinistre = "";
		this.moyens = new ArrayList<Moyen>();
		this.intervenants = new ArrayList<Intervenant>();
		this.observers = new ArrayList<Observer>();
		messages = new ArrayList<IMessage>();
	}
	
	public Intervention(String adresse, String codeSinistre){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.adresse = adresse;
		this.codeSinistre = codeSinistre;
		this.moyens = new ArrayList<Moyen>();
		this.intervenants = new ArrayList<Intervenant>();
		this.observers = new ArrayList<Observer>();
		messages = new ArrayList<IMessage>();
	}
	
	public Intervention(String adresse, String codeSinistre, List<Moyen> moyens){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.adresse = "";
		this.codeSinistre = "";
		this.moyens = moyens;
		this.intervenants = new ArrayList<Intervenant>();
		this.observers = new ArrayList<Observer>();
		messages = new ArrayList<IMessage>();
	}
	
	public Intervention(String adresse, String codeSinistre, List<Moyen> moyens, List<Intervenant> users){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.adresse = "";
		this.codeSinistre = "";
		this.moyens = moyens;
		this.intervenants = users;
		this.observers = new ArrayList<Observer>();
		messages = new ArrayList<IMessage>();
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
	public void getIntervenants(final Observer o) {
		DataBaseCommunication.sendGet(new IPersistant() {
			
			@Override
			public void onErrorResponse(VolleyError error) {
				if(error.getMessage() != null) {
					Log.e("Intervention", error.getMessage());
				}
				else{
					Log.e("Intervention", error.toString());
				};
			}
			
			@Override
			public void onResponse(JSONObject json) {
				try {
					JSONArray a = (JSONArray) json.get("rows");
					for(int i=0; i<a.length(); i++){
						JSONObject o = a.getJSONObject(i);
						o = (JSONObject) o.get("value");
						o.remove("type");
						Intervention.this.intervenants.add((Intervenant) JsonSerializer.deserialize(Intervenant.class, o));
					}
					notifyObserver(o);
				}
				catch(JSONException e){
					Log.e("Intervention", e.getMessage());
				}
			}
			
			@Override
			public void update() {
				
			}
			
			@Override
			public void setRev(String rev) {
				
			}
			
			@Override
			public void setId(String id) {
				
			}
			
			@Override
			public void save() {
				
			}
			
			@Override
			public String getUrl(int method) {
				return DataBaseCommunication.BASE_URL + "_design/agetacpp/_view/get_intervenants?key=\"" + Intervention.this._id + "\"";
			}
			
			@Override
			public String getRev() {
				return null;
			}
			
			@Override
			public String getId() {
				return null;
			}
			
			@Override
			public JSONObject getData() {
				return null;
			}
			
			@Override
			public void delete() {
				
			}
		});
		
	}
	
	public List<Intervenant> getIntervenants(){
		return intervenants;
	}

	/**
	 * @param users the users to set
	 */
	@Override
	public void setIntervenants(List<Intervenant> intervenants) {
		this.intervenants = intervenants;
	}
	
	/**
	 * @return the messages
	 */
	@Override
	public List<IMessage> getMessages() {
		return messages;
	}
	
	@Override
	public void getMessages(final Observer o){
		DataBaseCommunication.sendGet(new IPersistant() {
			
			@Override
			public void onErrorResponse(VolleyError error) {
				if(error.getMessage() != null) {
					Log.e("Intervention", error.getMessage());
				}
				else{
					Log.e("Intervention", error.toString());
				};
			}
			
			@Override
			public void onResponse(JSONObject json) {
				try {
					JSONArray a = (JSONArray) json.get("rows");
					for(int i=0; i<a.length(); i++){
						JSONObject o = a.getJSONObject(i);
						o = (JSONObject) o.get("value");
						o.remove("type");
						Intervention.this.messages.add((IMessage) JsonSerializer.deserialize(IMessage.class, o));
					}
					notifyObserver(o);
				}
				catch(JSONException e){
					Log.e("Intervention", e.getMessage());
				}
			}
			
			@Override
			public void update() {
				
			}
			
			@Override
			public void setRev(String rev) {
				
			}
			
			@Override
			public void setId(String id) {
				
			}
			
			@Override
			public void save() {
				
			}
			
			@Override
			public String getUrl(int method) {
				return DataBaseCommunication.BASE_URL + "_design/agetacpp/_view/get_messages?key=\"" + Intervention.this._id + "\"";
			}
			
			@Override
			public String getRev() {
				return null;
			}
			
			@Override
			public String getId() {
				return null;
			}
			
			@Override
			public JSONObject getData() {
				return null;
			}
			
			@Override
			public void delete() {
				
			}
		});
	}

	/**
	 * @param messages the messages to set
	 */
	@Override
	public void setMessages(List<IMessage> messages) {
		this.messages = messages;
	}
	
	@Override
	public void addMessage(IMessage message){
		this.messages.add(message);
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

	@Override
	public void registerObserver(Observer observer) {
		this.observers.add(observer);
	}

	@Override
	public void unregisterObserver(Observer observer) {
		this.observers.remove(observer);
	}

	@Override
	public void notifyObserver(Observer observer) {
		observer.update(this);
	}

	@Override
	public void notifyObservers() {
		for(Observer observer : this.observers){
			observer.update(this);
		}
	}

}
