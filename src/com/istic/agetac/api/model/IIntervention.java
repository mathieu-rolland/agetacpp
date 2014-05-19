package com.istic.agetac.api.model;

import java.util.List;

import com.istic.agetac.model.Codis;
import com.istic.agetac.model.Intervenant;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.pattern.observer.Observer;
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
	void getIntervenants(Observer o);
	List<Intervenant> getIntervenants();
	void setIntervenants(List<Intervenant> intervenants);
	void addIntervenant(Intervenant intervenant);
	Codis getCodis();
	void setCodis(Codis codis);
	List<IMessage> getMessages();
	void getMessages(Observer o);
	void setMessages(List<IMessage> messages);
	void addMessage(IMessage message);
}
