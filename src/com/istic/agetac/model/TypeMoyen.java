package com.istic.agetac.model;

import com.istic.agetac.R;
import com.istic.sit.framework.api.model.IRepresentation;
import com.istic.sit.framework.model.Representation;

/**
 * Concept métier représentant le type d'un véhicule dans son secteur attribué
 * @author Anthony LE MÉE - 10003134
 */
public enum TypeMoyen {
	
	VSAV_INC("VSAV_INC", 
			new Representation(R.drawable.vsav_ok),
			new Representation(R.drawable.vsav_ko)), 
	VSAV_ALIM("VSAV_ALIM",
			new Representation(R.drawable.vsav_ok), 
			new Representation(R.drawable.vsav_ko)), 
	VSAV_SAP("VSAV_SAP",
			new Representation(R.drawable.vsav_ok), 
			new Representation(R.drawable.vsav_ko)), 
	FPT_SAP("FPT_SAP",
			new Representation(R.drawable.fpt_sap), 
			new Representation(R.drawable.fpt_sap)), 
	FPT_INC("FPT_INC",
			new Representation(R.drawable.fpt_inc), 
			new Representation(R.drawable.fpt_inc)), 
	FPT_ALIM("FPT_ALIM",
			new Representation(R.drawable.fpt_alim), 
			new Representation(R.drawable.fpt_alim));

	private String mType;
	private IRepresentation representationOK;
	private IRepresentation representationKO;

	TypeMoyen(String type, IRepresentation representationOK,
			IRepresentation representationKO) {
		this.mType = type;
		this.representationOK = representationOK;
		this.representationKO = representationKO;
	}

	public String getType() {
		return this.mType;
	}

	@Override
	public String toString() {
		return mType;
	}

	public IRepresentation getRepresentationOK() {
		return this.representationOK;
	}

	public IRepresentation getRepresentationKO() {
		return this.representationKO;
	}
}
