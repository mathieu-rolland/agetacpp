package com.istic.agetac.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import android.os.Parcel;
import android.text.format.Formatter;

import com.android.volley.VolleyError;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.sit.framework.api.model.IPosition;
import com.istic.sit.framework.api.model.IProperty;
import com.istic.sit.framework.api.model.IRepresentation;
import com.istic.sit.framework.model.Entity;
import com.istic.sit.framework.model.Property;

/**
 * Classe Moyen : Modéle qui représente un moyen (i.e. Véhicule)
 * 
 * @author Anthony LE MEE - 10003134
 */
public class Moyen extends Entity implements IMoyen {

	public static final SimpleDateFormat FORMATER = new SimpleDateFormat(
			"ddMM '-' hhmm"); // ("ddMM '-' hhmm");

	/** Constante string which defines name of property of type of moyen */
	public static final String NAME_PROPERTY_TYPE 				= "moyen_type";
	/** Constante string which defines name of property of moyen demand hour */
	public static final String NAME_PROPERTY_HOUR_DEMAND 		= "moyen_hour_demand";
	/** Constante string which defines name of property of moyen engagement hour */
	public static final String NAME_PROPERTY_HOUR_ENGAGEMENT 	= "moyen_hour_engagement";
	/** Constante string which defines name of property of moyen arrival hour */
	public static final String NAME_PROPERTY_HOUR_ARRIVAL 		= "moyen_hour_arrival";
	/** Constante string which defines name of property of moyen free hour */
	public static final String NAME_PROPERTY_HOUR_FREE 			= "moyen_hour_free";
	/** Constante string which defines name of property of moyen secteur hour */
	public static final String NAME_PROPERTY_SECTEUR 			= "moyen_secteur";

	/**
	 * Constructeur de la classe Moyen
	 * 
	 * @param typeValue
	 *            : Type of moyen
	 */
	public Moyen(TypeMoyen typeValue) {
		super();
		this.setRepresentationKO(typeValue.getRepresentationKO());
		this.setRepresentationOK(typeValue.getRepresentationOK());
		IProperty typeProperty = creatProperty(NAME_PROPERTY_TYPE,
				typeValue.toString());
		IProperty hDemandProperty = creatProperty(NAME_PROPERTY_HOUR_DEMAND,
				FORMATER.format(new Date()));
		IProperty hEngagementProperty = creatProperty(
				NAME_PROPERTY_HOUR_ENGAGEMENT, null);
		IProperty hArrivalProperty = creatProperty(NAME_PROPERTY_HOUR_ARRIVAL,
				null);
		IProperty hFreeProperty = creatProperty(NAME_PROPERTY_HOUR_FREE, null);
		IProperty secteurProperty = creatProperty(NAME_PROPERTY_SECTEUR, null);
		super.addPropriete(typeProperty);
		super.addPropriete(hDemandProperty);
		super.addPropriete(hEngagementProperty);
		super.addPropriete(hArrivalProperty);
		super.addPropriete(hFreeProperty);
		super.addPropriete(secteurProperty);
	}

	/**
	 * Constructeur de la classe Moyen
	 * 
	 * @param typeValue
	 *            : Type of moyen
	 * @param position
	 *            : position of moyen
	 */
	public Moyen(TypeMoyen typeValue, IPosition position) {

		super(position);

		IProperty typeProperty = creatProperty(NAME_PROPERTY_TYPE,
				typeValue.toString());
		IProperty hDemandProperty = creatProperty(NAME_PROPERTY_HOUR_DEMAND,
				FORMATER.format(new Date()));
		IProperty hEngagementProperty = creatProperty(
				NAME_PROPERTY_HOUR_ENGAGEMENT, null);
		IProperty hArrivalProperty = creatProperty(NAME_PROPERTY_HOUR_ARRIVAL,
				null);
		IProperty hFreeProperty = creatProperty(NAME_PROPERTY_HOUR_FREE, null);
		IProperty secteurProperty = creatProperty(NAME_PROPERTY_SECTEUR, null);

		super.addPropriete(typeProperty);
		super.addPropriete(hDemandProperty);
		super.addPropriete(hEngagementProperty);
		super.addPropriete(hArrivalProperty);
		super.addPropriete(hFreeProperty);
		super.addPropriete(secteurProperty);
	}

	/**
	 * Constructeur de la classe Moyen
	 * 
	 * @param source
	 *            : moyen
	 */
	public Moyen(Parcel source) {
		super(source);
	}

	public TypeMoyen getType() {

		if (super.getProperty(NAME_PROPERTY_TYPE).getValeur()
				.equals(TypeMoyen.VSAV_INC.toString())) {
			return TypeMoyen.VSAV_INC;
		} else if (super.getProperty(NAME_PROPERTY_TYPE).getValeur()
				.equals(TypeMoyen.FPT_ALIM.toString())) {
			return TypeMoyen.FPT_ALIM;
		} else if (super.getProperty(NAME_PROPERTY_TYPE).getValeur()
				.equals(TypeMoyen.FPT_INC.toString())) {
			return TypeMoyen.FPT_INC;
		} else if (super.getProperty(NAME_PROPERTY_TYPE).getValeur()
				.equals(TypeMoyen.FPT_SAP.toString())) {
			return TypeMoyen.FPT_SAP;
		} else if (super.getProperty(NAME_PROPERTY_TYPE).getValeur()
				.equals(TypeMoyen.VSAV_ALIM.toString())) {
			return TypeMoyen.VSAV_ALIM;
		} else if (super.getProperty(NAME_PROPERTY_TYPE).getValeur()
				.equals(TypeMoyen.VSAV_SAP.toString())) {
			return TypeMoyen.VSAV_SAP;
		} else {
			return null; // FIXME add throw WARNING  
		}



	} 

	public Date getHDemande() {
		try
		{
			return FORMATER.parse(super.getProperty(NAME_PROPERTY_HOUR_DEMAND).getValeur());
		}
		catch ( Exception e )
		{
			return null;
		}
	}

	public Date getHArrival() {
		try
		{
			return FORMATER.parse(super.getProperty(NAME_PROPERTY_HOUR_ARRIVAL).getValeur());
		}
		catch ( Exception e )
		{
			return null;
		}
	}

	public Date getHEngagement() {
		try
		{
			return FORMATER.parse(super.getProperty(NAME_PROPERTY_HOUR_ENGAGEMENT).getValeur());
		}
		catch ( Exception e )
		{
			return null;
		}
	} 

	public Date getHFree() {
		try
		{
			return FORMATER.parse(super.getProperty(NAME_PROPERTY_HOUR_FREE).getValeur());
		}
		catch ( Exception e )
		{
			return null;
		}
	}

	public String getSecteur() {
		return super.getProperty(NAME_PROPERTY_SECTEUR).getValeur();
	} // method

	public void setType(String valeur) {
		super.getProperty(NAME_PROPERTY_TYPE).setValeur(valeur);
	} // method

	public void setHDemande(Date valeur) {
		super.getProperty(NAME_PROPERTY_HOUR_DEMAND).setValeur(
				FORMATER.format(valeur));
	} // method

	public void setHArrival(Date valeur) {
		super.getProperty(NAME_PROPERTY_HOUR_ARRIVAL).setValeur(
				FORMATER.format(valeur));
		super.setOk(true);
	} // method

	public void setHEngagement(Date dateEngage) {
		super.getProperty(NAME_PROPERTY_HOUR_ENGAGEMENT).setValeur(
				FORMATER.format(dateEngage));
	} // method

	public void setHFree(Date valeur) {
		super.getProperty(NAME_PROPERTY_HOUR_FREE).setValeur(
				FORMATER.format(valeur));
	} // method

	/**
	 * Method which return secteur of moyen
	 * 
	 * @return String : secteur of moyen
	 */
	public void setSecteur(String valeur) {
		super.getProperty(NAME_PROPERTY_SECTEUR).setValeur(valeur);
	} // method

	public IProperty creatProperty(String name, String value) {
		IProperty property = new Property();
		property.setNom(name);
		property.setValeur(value);
		return property;
	}

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
		if(AgetacppApplication.getIntervention() != null && !AgetacppApplication.getIntervention().getMoyens().contains(this)){
			AgetacppApplication.getIntervention().addMoyen(this);
			AgetacppApplication.getIntervention().save();
		}
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
	public IRepresentation getRepresentationOK() {
		return super.getRepresentationOK();
	}

	@Override
	public void setRepresentationOK(IRepresentation representation) {
		super.setRepresentationOK(representation);
	}

	@Override
	public IRepresentation getRepresentationKO() {
		return super.getRepresentationKO();
	}

	@Override
	public void setRepresentationKO(IRepresentation representation) {
		super.setRepresentationKO(representation);
	}

	@Override
	public IRepresentation getRepresentation() {
		if (isOk()) {
			return this.getRepresentationOK();
		} else {
			return this.getRepresentationKO();
		}
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
	public boolean isOnMap() {
		return super.isOnMap();
	}

	@Override
	public void setOnMap(boolean onMap) {
		super.setOnMap(onMap);
	}

	@Override
	public boolean isOk() {
		return super.isOk();
	}

	@Override
	public void setOk(boolean ok) {
		super.setOk(ok);
	}

	@Override
	public void setFrozen(boolean frozen) {
		super.setFrozen(frozen);
	}

	@Override
	public boolean isGroup() {
		return false;
	}

}
