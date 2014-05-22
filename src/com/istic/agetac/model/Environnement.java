package com.istic.agetac.model;

import com.istic.agetac.app.AgetacppApplication;
import com.istic.sit.framework.model.Entity;

public class Environnement extends Entity {
	
	private transient Intervention intervention;
	
	@Override
	public void delete() {
		AgetacppApplication.getIntervention().getEnvironnements().remove(this);
		this.save();
	}

	@Override
	public void save() {
		AgetacppApplication.getIntervention().save();
	}

	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

}
