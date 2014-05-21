package com.istic.agetac.model;

import com.istic.sit.framework.api.model.IRepresentation;

public interface IMoyen
{    
    boolean isGroup();
    
    IRepresentation getRepresentationOK();
    String getLibelle();
    String getHDemande();
    String getHEngagement();
    String getHArrival();
    String getHFree();
    String getSecteur();
    
    void setRepresentationOK(IRepresentation representation);
    void setLibelle(String libelle);
    void setHDemande(String hourDemande);
    void setHEngagement(String hourEngagement);
    void setHArrival(String HourArrived);
    void setHFree(String HourFree);
    void setSecteur(String secteur); 
   
}
