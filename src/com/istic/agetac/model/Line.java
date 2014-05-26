package com.istic.agetac.model;

import com.istic.agetac.app.AgetacppApplication;
import com.istic.sit.framework.model.ALine;

public class Line extends ALine {
	
	private transient Intervention intervention;

	@Override
	public void save() {
		AgetacppApplication.getIntervention().save();
	}

	@Override
	public void delete() {
		AgetacppApplication.getIntervention().getLines().remove(this);
		save();
	}

	/**
	 * @return the intervention
	 */
	public Intervention getIntervention() {
		return intervention;
	}

	/**
	 * @param intervention the intervention to set
	 */
	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

}
