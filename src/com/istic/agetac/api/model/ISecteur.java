package com.istic.agetac.api.model;


public interface ISecteur {

	public String getId();
	public void setId(String id);
	public void setName( String libelle );
	public String getName();
	public void setColor( String color );
	public String getColor();
	public void lock();
	public void unlock();
	public boolean isLock();
	public void save();
	public void update();
	
}
