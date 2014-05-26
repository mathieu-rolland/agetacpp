package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.api.model.IGroupe;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.sit.framework.api.model.IRepresentation;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.JsonSerializer;

public class Groupe implements IGroupe, IMoyen {

	private String _id;
	private String _rev;
	private String nom;
	private List<Moyen> moyens;
	private transient Intervention intervention;
	private boolean isExpand = true;
	
	/**
	 * @param isExpand the isExpand to set
	 */
	public void setExpand(boolean isExpand) {
		this.isExpand = isExpand;
	}

	public Groupe(String nom){
		this._id 		= UUID.randomUUID().toString();
		this._rev 		= "";
		this.nom 		= nom;
		this.intervention = AgetacppApplication.getIntervention();
		this.addMoyens(new ArrayList<Moyen>());
	}
	
	public Groupe(String nom, List<Moyen> moyens){
		this._id 		= UUID.randomUUID().toString();
		this._rev 		= "";
		this.nom 		= nom;
		this.intervention = AgetacppApplication.getIntervention();
		this.addMoyens(moyens);
		
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
//			DataBaseCommunication.sendPut(this);
			if( !intervention.getGroupes().contains( this ) ) intervention.getGroupes().add(this);
			intervention.save();
		}
	}

	@Override
	public void update() {
//		DataBaseCommunication.sendPut(this);
		intervention.update();
	}

	@Override
	public void delete() {
//		DataBaseCommunication.sendDelete(this);
		intervention.delete(this);
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
		for(Moyen m : moyens) {
			m.setIsInGroup(true);
		}
		this.moyens = moyens;
	}

	@Override
	public void addMoyen(Moyen moyen) {
		moyen.setIsInGroup(true);
		if (moyen != null) {
			this.moyens.add(moyen);
		}
	} // method

	@Override
	public void deleteMoyen(Moyen moyen) {
		moyen.setIsInGroup(false);
		if (moyen != null) {
			this.moyens.remove(moyen);
		}
	} // method

	@Override
	public void addMoyens(List<Moyen> moyens) {
		if (moyens != null ) {
			if (moyens.size() > 0) {
				for(Moyen m : moyens) {
					m.setIsInGroup(true);
				}
				if (this.moyens == null) {
					this.moyens = new ArrayList<Moyen>();
				}
				this.moyens.addAll(moyens);
			}
		}
	} // method

	@Override
	public boolean isGroup() {
		return true;
	}
	
	@Override
	public String toString() {
		return this.getNom();
	}

	@Override
	public IRepresentation getRepresentationOK() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLibelle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRepresentationOK(IRepresentation representation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLibelle(String libelle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IRepresentation getRepresentationKO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getHDemande() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getHEngagement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getHArrival() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getHFree() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHDemande(Date hourDemande) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHEngagement(Date hourEngagement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHArrival(Date HourArrived) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHFree(Date HourFree) {
		// TODO Auto-generated method stub
	}
		
	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}
	
	public boolean isExpand() {
		return isExpand;
	}

}
