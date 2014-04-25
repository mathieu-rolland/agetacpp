package com.istic.agetac.model;

import java.util.Date;

import org.json.JSONObject;

import android.os.Parcel;

import com.android.volley.VolleyError;
import com.istic.sit.framework.api.model.IPosition;
import com.istic.sit.framework.api.model.IProperty;
import com.istic.sit.framework.api.model.IRepresentation;
import com.istic.sit.framework.model.Entity;
import com.istic.sit.framework.model.Property;

/**
 * Classe Moyen : Modèle qui représente un moyen (i.e. Véhicule)
 * 
 * @author Anthony LE MEE - 10003134
 */
public class Moyen extends Entity {

	private Date mHourDemand;	
	private Date mHourEngagement;
	private Date mHourArrivedOnSite;
	private Date mHourFree;
	private String mSector;
	
	/**
	 * Constructeur de la classe Moyen
	 * @param typeValue : Type of moyen
	 */
	public Moyen (String typeValue) {
		super();
		IProperty typePropertie;
		typePropertie = new Property();
		typePropertie.setNom("type");
		typePropertie.setValeur(typeValue);
		mHourDemand = new Date();
		super.addPropriete(typePropertie);
	}
	
	/**
	 * Constructeur de la classe Moyen
	 * @param typeValue : Type of moyen
	 * @param position : position of moyen
	 */
	public Moyen (String typeValue, IPosition position) {
		super(position);
		IProperty typePropertie;
		typePropertie = new Property();
		typePropertie.setNom("type");
		typePropertie.setValeur(typeValue);
		mHourDemand = new Date();
		super.addPropriete(typePropertie);
	}
	
	/**
	 * Constructeur de la classe Moyen
	 * @param source : moyen
	 */
	public Moyen (Parcel source) {
		super(source);
		mHourDemand = new Date();
	}
	
	/**
	 * Method which return type of moyen
	 * @return String : Type of moyen
	 */
	public String getType () {
		for (IProperty propertie : super.getProprietes()) {
			if (propertie.getNom().equals("type")) {
				return propertie.getValeur();
			}
		}
		return null;
	} // method
	
	@Override
	public String getUrl(int method) {
		return super.getUrl(method);
	}

	@Override
	public JSONObject getData() {
		return super.getData();
	}

	@Override
	public void save() {
		super.save();
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void delete() {
		super.delete();
	}

	@Override
	public void onResponse(JSONObject response) {
		super.onResponse(response);
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		super.onErrorResponse(error);
	}

	@Override
	public int describeContents() {
		return super.CONTENTS_FILE_DESCRIPTOR;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO
	}

	@Override
	public String getId() {
		return super.getId();
	}

	@Override
	public void setId(String id) {
		super.setId(id);
	}

	@Override
	public String getRev() {
		return super.getRev();
	}

	@Override
	public void setRev(String rev) {
		super.setRev(rev);
	}

	@Override
	public String getLibelle() {
		return super.getLibelle();
	}

	@Override
	public void setLibelle(String label) {
		super.setLibelle(label);
	}

	@Override
	public void addPropriete(IProperty property) {
		super.addPropriete(property);
	}

	@Override
	public IRepresentation getRepresentation() {
		return super.getRepresentation();
	}

	@Override
	public void setRepresentation(IRepresentation representation) {
		setRepresentation(representation);
	}

	@Override
	public IPosition getPosition() {
		return super.getPosition();
	}

	@Override
	public void setPosition(IPosition positon) {
		super.setPosition(positon);
	}

	@Override
	public boolean isFrozen() {
		return super.isFrozen();
	}

	@Override
	public void setFrozen(boolean frozen) {
		super.setFrozen(frozen);
	}
	
	public Date getmHourDemand() {
		return mHourDemand;
	}

	public void setmHourDemand(Date mHourDemand) {
		this.mHourDemand = mHourDemand;
	}

	public Date getmHourEngagement() {
		return mHourEngagement;
	}

	public void setmHourEngagement(Date mHourEngagement) {
		this.mHourEngagement = mHourEngagement;
	}

	public Date getmHourArrivedOnSite() {
		return mHourArrivedOnSite;
	}

	public void setmHourArrivedOnSite(Date mHourArrivedOnSite) {
		this.mHourArrivedOnSite = mHourArrivedOnSite;
	}

	public Date getmHourFree() {
		return mHourFree;
	}

	public void setmHourFree(Date mHourFree) {
		this.mHourFree = mHourFree;
	}

	public String getmSector() {
		return mSector;
	}

	public void setmSector(String mSector) {
		this.mSector = mSector;
	}


}
