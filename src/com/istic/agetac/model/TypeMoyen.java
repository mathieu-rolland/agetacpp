package com.istic.agetac.model;

import com.istic.agetac.R;
import com.istic.sit.framework.api.model.IRepresentation;
import com.istic.sit.framework.model.Representation;

/**
 * Concept métier représentant le type d'un véhicule dans son secteur attribué
 * 
 * @author Anthony LE MÉE - 10003134
 */
public enum TypeMoyen {

	VSAV("VSAV", new Representation(R.drawable.vsav_ok), 
			new Representation(R.drawable.vsav_ko)), 
	FPT("FPT", new Representation(R.drawable.fpt_ok), 
			new Representation(R.drawable.fpt_ko)), 
	CCFM("CCFM", new Representation(R.drawable.ccfm_ok), 
			new Representation(R.drawable.ccfm_ko)), 
	CCGC("CCGC", new Representation(R.drawable.ccgc_ok), 
			new Representation(R.drawable.ccgc_ko)), 
	VAR("VAR", new Representation(R.drawable.var_ok), 
			new Representation(R.drawable.var_ko)), 
	VLCC("VLCC", new Representation(R.drawable.vlcc_ok), 
			new Representation(R.drawable.vlcc_ko)), 
	VLCG("VLCG", new Representation(R.drawable.vlcg_ok), 
			new Representation(R.drawable.vlcg_ko)), 
	VLS("VLS", new Representation(R.drawable.vls_ok), 
			new Representation(R.drawable.vls_ko)), 
	VSR("VSR", new Representation(R.drawable.vsr_ok), 
			new Representation(R.drawable.vsr_ok));

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
