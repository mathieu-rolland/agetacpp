package com.istic.agetac.api.model;

import java.util.Date;

import com.istic.agetac.model.Intervention;
import com.istic.sit.framework.api.model.IEntity;
import com.istic.sit.framework.api.model.IRepresentation;
import com.istic.sit.framework.couch.IPersistant;
import com.istic.sit.framework.couch.ITaMere;

/**
 * Interface qui regroupe les moyen et les groupes de moyens sous un même type.
 * @author Anthony LE MÉE - 1003134
 *
 */
public interface IMoyen extends ITaMere {

	public boolean isGroup();
	
    IRepresentation getRepresentationOK();
    IRepresentation getRepresentationKO();
    String getLibelle();
    Date getHDemande();
    Date getHEngagement();
    Date getHArrival();
    Date getHFree();
    
    void setRepresentationOK(IRepresentation representation);
    void setLibelle(String libelle);
    void setHDemande(Date hourDemande);
    void setHEngagement(Date hourEngagement);
    void setHArrival(Date HourArrived);
    void setHFree(Date HourFree);

	public void setIntervention(Intervention intervention);
   
} // interface
