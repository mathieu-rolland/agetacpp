package com.istic.agetac.api.model;

public interface IMessage {

	public String getUid();
	public void setUid(String uid);
	public void setText(String text);
	public String getText();
	public boolean isEditable();
	public boolean isValidate();
	public void validate();
	public void unvalidate();
	public void lock();
	public void unlock();
	public void save();
	public void update();
	
}
