package com.istic.agetac.api.model;

import java.util.Date;
import java.util.List;

import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Secteur;
import com.istic.sit.framework.api.model.IRepresentation;
import com.istic.sit.framework.couch.IRecordable;

/**
 * Interface qui regroupe les moyen et les groupes de moyens sous un même type.
 * @author Anthony LE MÉE - 1003134
 *
 */
public interface IMoyen extends IRecordable {

	boolean isGroup();
	boolean isInGroup();
	void setIsInGroup(boolean value);
	
    IRepresentation getRepresentationOK();
    IRepresentation getRepresentationKO();
    String getLibelle();
    Date getHDemande();
    Date getHEngagement();
    Date getHArrival();
    Date getHFree();
    Secteur getSecteur();
    
    void setRepresentationOK(IRepresentation representation);
    void setLibelle(String libelle);
    void setHDemande(Date hourDemande);
    void setHEngagement(Date hourEngagement);
    void setHArrival(Date HourArrived);
    void setHFree(Date HourFree);
    void setSecteur(Secteur secteur);
    
    

	public void setIntervention(Intervention intervention);
	
	public List<IMoyen> getListMoyen();
	public void setListMoyen(List<IMoyen> liste);
	public void addMoyen(IMoyen moyen);
	public void deleteAllMoyen();
	public void deleteMoyen(IMoyen moyen);
	void addMoyens( List<IMoyen> liste );
   
} // interface
