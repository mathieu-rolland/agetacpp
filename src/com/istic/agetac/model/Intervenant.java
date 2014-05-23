package com.istic.agetac.model;


public class Intervenant extends User {
	
	private transient Intervention intervention;

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

	@Override
	public void save() {
		intervention.save();
	}

	@Override
	public void delete() {
		intervention.getIntervenants().remove(this);
		this.save();
	}
	
	public String toString(){
		String returned = "{\n";
		
		returned += "nom : "+ this.getName() +",\n";
		returned += "username : "+ this.getUsername() +",\n";
		returned += "password : "+ this.getPassword() +",\n";
		
		return returned+"}";
	}
	
}
