package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.List;

public class Codis extends User {
	
	private List<Intervention> interventions;

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

}
