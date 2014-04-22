package com.istic.agetac.api.model;

public interface IMessage {

	public void setText(String text);
	public String getText();
	public boolean isEditable();
	public void lock();
	public void unlock();
	public void save();
	public void update();
	
}
