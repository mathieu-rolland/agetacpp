package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.List;

public class Codis extends User {
	
	private transient List<Intervention> interventions;

	public Codis(String name, String username) {
		super(name, username, Role.codis);
		setInterventions(new ArrayList<Intervention>());
	}

	/**
	 * @return the interventions
	 */
	public List<Intervention> getInterventions() {
		return interventions;
	}

	/**
	 * @param interventions the interventions to set
	 */
	public void setInterventions(List<Intervention> interventions) {
		this.interventions = interventions;
	}
	
	public void addIntervention(Intervention intervention){
		this.interventions.add(intervention);
	}

	@Override
	public void save() {
		for(Intervention i : interventions){
			i.save();
		}
	}

	@Override
	public void delete() {
		for(Intervention i : interventions){
			i.setCodis(null);
		}
		this.save();
	}

}
