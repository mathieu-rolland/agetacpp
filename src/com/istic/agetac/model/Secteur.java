package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.istic.agetac.api.model.ISecteur;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.sit.framework.model.Entity;

/**
 * Classe Secteur : Mod�le qui repr�sente un secteur (i.e. SAP/INC/ALIM/...)
 * 
 * @author Anthony LE MEE - 10003134
 */
public class Secteur extends Entity implements ISecteur { 

	private boolean lock;
	private List<Moyen> moyens;

	private transient Intervention intervention;
	
	public Secteur( Intervention intervention )
	{
		super();
		lock = false;
		moyens = new ArrayList<Moyen>();
		this.intervention = intervention;
	}

	@Override
	public void lock() {
		lock = true;
	}

	@Override
	public void unlock() {
		lock = false;
	}

	@Override
	public void save() {
		if( !intervention.getSecteurs().contains(this) ) intervention.getSecteurs().add(this);
		intervention.addHistorique(new Action(AgetacppApplication.getUser().getName(),new Date(),"Création du secteur "+this.getName()));
		intervention.save();
	}

	@Override
	public void update() {
//		DataBaseCommunication.sendPut(this);
		intervention.addHistorique(new Action(AgetacppApplication.getUser().getName(),new Date(),"MAJ du secteur "+this.getName()));
		AgetacppApplication.getIntervention().update();
	}


	@Override
	public void delete() {
		AgetacppApplication.getIntervention().delete(this);
		intervention.addHistorique(new Action(AgetacppApplication.getUser().getName(),new Date(),"Suppression du secteur "+this.getName()));
	}


	@Override
	public int describeContents() {
		return 0;
	}


	@Override
	public boolean isLock() {
		return lock;
	}

	@Override
	public void setName(String libelle) {
		setLibelle(libelle);
	}

	@Override
	public String getName() {
		return getLibelle();
	}
	
	public String toString() {
		return getLibelle();
	}

	@Override
	public void addMoyen(Moyen moyen) {
		this.moyens.add(moyen);
	}

	@Override
	public List<Moyen> getMoyens() {
		return moyens;
	}
	
	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

}
