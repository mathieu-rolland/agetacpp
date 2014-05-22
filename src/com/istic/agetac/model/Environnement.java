package com.istic.agetac.model;

import com.istic.sit.framework.model.Entity;

public class Environnement extends Entity {
	
	private transient Intervention intervention;
	
	public Environnement( Intervention intervention ){
		this.intervention = intervention;
	}
	
	@Override
	public void delete() {
		intervention.delete(this);
		this.save();
	}

	@Override
	public void save() {
		if( !intervention.getEnvironnements().contains(this)){
			intervention.getEnvironnements().add(this);
		}
		intervention.save();
	}

	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

}
