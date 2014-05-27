package com.istic.agetac.api.model;

import java.util.List;

import com.istic.agetac.model.Moyen;
import com.istic.sit.framework.couch.IPersistant;

public interface IGroupe extends IPersistant {

	public String getNom();
	public void setNom(String nom);
	public List<Moyen> getMoyens();
	public void setMoyens(List<Moyen> moyens);
	public void addMoyen(Moyen moyen);
	public void deleteMoyen(Moyen moyen);
	public void addMoyens(List<Moyen> moyens);
	public void deleteAllMoyen();
}
