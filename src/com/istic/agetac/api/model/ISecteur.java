package com.istic.agetac.api.model;

import java.util.List;

import com.istic.agetac.model.Moyen;


public interface ISecteur {
	
	public void setName( String libelle );
	public String getName();
	public void setColor( int color );
	public int getColor();
	public void lock();
	public void unlock();
	public boolean isLock();
	public void save();
	public void update();
	public void addMoyen( Moyen moyen );
	public List<Moyen> getMoyens();
}
