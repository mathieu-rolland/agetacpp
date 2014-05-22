package com.istic.agetac.model;

import java.util.Calendar;
import java.util.Date;

import com.istic.agetac.api.model.IUser.Role;

public class Action {

	private User user;
	private Date dateAction;
	private String natureAction;
	
	public Action(User user,Date dateAction,String nature){
		this.user = user;
		this.dateAction = dateAction;
		this.natureAction=nature;
	}
	
	public Action(){
		this.user = new Codis("Codis", "Jacques");
		Calendar calendar = Calendar.getInstance();
		this.dateAction = calendar.getTime();
		this.natureAction = "Message envoyé à Jean";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
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
}
