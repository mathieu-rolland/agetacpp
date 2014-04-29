package com.istic.agetac.model;

public class Intervenant extends User {
	
	private Intervention intervention;

	public Intervenant(String name, String username) {
		super(name, username, Role.intervenant);
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
