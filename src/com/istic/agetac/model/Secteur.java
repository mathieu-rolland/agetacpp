package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.istic.agetac.api.model.IMoyen;
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
	private transient List<IMoyen> moyens;

	private transient Intervention intervention;
	
	public Secteur()
	{
		super();
		lock = false;
		moyens = new ArrayList<IMoyen>();
		intervention = AgetacppApplication.getIntervention();
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
//		DataBaseCommunication.sendPost(this);
		Intervention inter= AgetacppApplication.getIntervention();
		if( !inter.getSecteurs().contains(this) ) inter.getSecteurs().add(this);
		inter.save();
		if( !intervention.getSecteurs().contains(this) ) intervention.getSecteurs().add(this);
		intervention.addHistorique(new Action(AgetacppApplication.getUser().getName(),new Date(),"Création du secteur "+this.getName()));
		intervention.save();
	}

	@Override
	public void update() {
//		DataBaseCommunication.sendPut(this);
		AgetacppApplication.getIntervention().update();
		intervention.addHistorique(new Action(AgetacppApplication.getUser().getName(),new Date(),"Modification du secteur "+this.getName()));
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
	public void addMoyen(IMoyen moyen) {
		this.moyens.add(moyen);
	}

	@Override
	public List<IMoyen> getMoyens() {
		return moyens;
	}
	
	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

}
