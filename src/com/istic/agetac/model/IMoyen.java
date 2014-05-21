package com.istic.agetac.model;

import java.util.Date;

import com.istic.sit.framework.api.model.IRepresentation;

public interface IMoyen
{    
    boolean isGroup();
    
    IRepresentation getRepresentationOK();
    String getLibelle();
    Date getHDemande();
    Date getHEngagement();
    Date getHArrival();
    Date getHFree();
    Date getSecteur();
    
    void setRepresentationOK(IRepresentation representation);
    void setLibelle(String libelle);
    void setHDemande(Date hourDemande);
    void setHEngagement(Date hourEngagement);
    void setHArrival(Date HourArrived);
    void setHFree(Date HourFree);
    void setSecteur(Date secteur); 
   
}
