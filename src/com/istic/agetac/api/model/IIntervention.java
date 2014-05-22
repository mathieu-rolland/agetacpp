package com.istic.agetac.api.model;

import java.util.List;
import com.istic.agetac.model.Codis;
import com.istic.agetac.model.Environnement;
import com.istic.agetac.model.Intervenant;
import com.istic.agetac.model.Moyen;
import com.istic.sit.framework.couch.IPersistant;

public interface IIntervention extends IPersistant{

	public String getId();
	public void setId(String id);
	public String getRev();
	public void setRev(String rev);
	public String getNom();
	public void setNom(String nom);
	public String getAdresse();
	public void setAdresse(String Adresse);
	public String getCodeSinistre();
	public void setCodeSinistre(String CodeSinistre);
	public List<Moyen> getMoyens();
	public void setMoyens(List<Moyen> moyens);
	public void addMoyen(Moyen moyen);
	public void addMoyens(List<Moyen> moyens);
	public List<Environnement> getEnvironnements();
	public void setEnvironnements(List<Environnement> environnements);
	public void addEnvironnement(Environnement environnement);
	public void addEnvironnements(List<Environnement> environnements);
	public List<Intervenant> getIntervenants();
	public void setIntervenants(List<Intervenant> intervenants);
	public void addIntervenant(Intervenant intervenant);
	public void addIntervenants(List<Intervenant> intervenants);
	public Codis getCodis();
	public void setCodis(Codis codis);
	public List<IMessage> getMessages();
	public void setMessages(List<IMessage> messages);
	public void addMessage(IMessage message);
	public void addMessages(List<IMessage> messages);
}
