package com.istic.agetac.api.model;

import java.util.List;

import com.istic.agetac.model.Moyen;

public interface IIntervention {

	public String getId();
	public void setId(String id);
	public String getRev();
	public void setRev(String rev);
	public String getAdresse();
	public void setAdresse(String Adresse);
	public String getCodeSinistre();
	public void setCodeSinistre(String CodeSinistre);
	public List<Moyen> getMoyens();
	public void setMoyens(List<Moyen> moyens);
}
