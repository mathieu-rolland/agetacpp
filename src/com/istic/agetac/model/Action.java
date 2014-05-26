package com.istic.agetac.model;

import java.util.Calendar;
import java.util.Date;

public class Action {

	private String user;
	private Date dateAction;
	private String natureAction;
	private transient Intervention intervention;
	
	public Action(String user,Date dateAction,String nature){
		this.user = user;
		this.dateAction = dateAction;
		this.natureAction=nature;
	}
	
	public Action(){
		this.user = "Jacques";
		Calendar calendar = Calendar.getInstance();
		this.dateAction = calendar.getTime();
		this.natureAction = "Message envoyé à Jean";
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getDateAction() {
		return dateAction;
	}

	public void setDateAction(Date dateAction) {
		this.dateAction = dateAction;
	}

	public String getNatureAction() {
		return natureAction;
	}

	public void setNatureAction(String natureAction) {
		this.natureAction = natureAction;
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
