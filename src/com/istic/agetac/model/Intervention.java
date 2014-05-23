package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.api.model.IIntervention;
import com.istic.agetac.api.model.IMessage;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.pattern.observer.Observer;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.JsonSerializer;

public class Intervention implements IIntervention {

	private String _id;
	private String _rev;
	private String adresse;
	private String nom;
	private String codeSinistre;
	private List<IMoyen> moyens;
	private transient List<Observer> observers;
	private List<Action> historique;
	private OCT oct;
	private List<Environnement> environnements;
	private List<Intervenant> intervenants;
	private Codis codis;
	private List<IMessage> messages;
	private List<Groupe> groupes;
	private List<Secteur> secteurs;
	
	public Intervention(){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.adresse = "";
		this.nom = "";
		this.codeSinistre = "";
		this.moyens = new ArrayList<IMoyen>();
		this.environnements = new ArrayList<Environnement>();
		this.intervenants = new ArrayList<Intervenant>();
		this.messages = new ArrayList<IMessage>();
		this.secteurs = new ArrayList<Secteur>();
		this.historique = new ArrayList<Action>();
		this.oct = new OCT();
		this.groupes = new ArrayList<Groupe>();
	}
	
	public OCT getOct() {
		return oct;
	}

	public void setOct(OCT oct) {
		this.oct = oct;
	}

	public Intervention(String adresse, String nom, String codeSinistre){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.adresse = adresse;
		this.nom = nom;
		this.codeSinistre = codeSinistre;
		this.moyens = new ArrayList<IMoyen>();
		this.environnements = new ArrayList<Environnement>();
		this.intervenants = new ArrayList<Intervenant>();
		this.messages = new ArrayList<IMessage>();
		this.historique = new ArrayList<Action>();
		this.groupes = new ArrayList<Groupe>();
		this.secteurs = new ArrayList<Secteur>();
	}
	
	public Intervention(String adresse, String nom, String codeSinistre, List<IMoyen> moyens){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.adresse = adresse;
		this.nom = nom;
		this.codeSinistre = codeSinistre;
		this.moyens = moyens;
		this.environnements = new ArrayList<Environnement>();
		this.intervenants = new ArrayList<Intervenant>();
		this.messages = new ArrayList<IMessage>();
		this.secteurs = new ArrayList<Secteur>();
		this.historique = new ArrayList<Action>();
		this.groupes = new ArrayList<Groupe>();
	}
	
	public Intervention(String adresse, String nom, String codeSinistre, List<IMoyen> moyens, List<Intervenant> users){
		this._id = UUID.randomUUID().toString();
		this._rev = "";
		this.adresse = adresse;
		this.nom = nom;
		this.codeSinistre = codeSinistre;
		this.moyens = moyens;
		this.environnements = new ArrayList<Environnement>();
		this.intervenants = users;
		this.messages = new ArrayList<IMessage>();
		this.secteurs = new ArrayList<Secteur>();
		this.historique = new ArrayList<Action>();
	}

	public List<Action> getHistorique() {
		return historique;
	}

	public void setHistorique(List<Action> historique) {
		this.historique = historique;
		this.groupes = new ArrayList<Groupe>();
		this.secteurs = new ArrayList<Secteur>();
	}

	/**
	 * @return the adresse
	 */
	@Override
	public String getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */
	@Override
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	/**
	 * @return the nom
	 */
	@Override
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	@Override
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the codeSinistre
	 */
	@Override
	public String getCodeSinistre() {
		return codeSinistre;
	}

	/**
	 * @param codeSinistre the codeSinistre to set
	 */
	@Override
	public void setCodeSinistre(String codeSinistre) {
		this.codeSinistre = codeSinistre;
	}

	/**
	 * @return the moyens
	 */
	@Override
	public List<IMoyen> getMoyens() {
		return moyens;
	}

	/**
	 * @param moyens the moyens to set
	 */
	@Override
	public void setMoyens(List<IMoyen> moyens) {
		this.moyens = moyens;
	}
	
	public void addMoyen(Moyen moyen) {
		this.moyens.add(moyen);
	}
	
	public void addMoyens(List<Moyen> listMoyen) {
		for (Moyen moyen : listMoyen) {
			this.moyens.add(moyen);
		}
	}
	
	public List<Intervenant> getIntervenants(){
		return intervenants;
	}

	/**
	 * @param users the users to set
	 */
	@Override
	public void setIntervenants(List<Intervenant> intervenants) {
		this.intervenants = intervenants;
	}
	
	@Override
	public void addIntervenants(List<Intervenant> intervenants) {
		this.intervenants.addAll(intervenants);
	}
	
	@Override
	public void addIntervenant(Intervenant intervenant) {
		this.intervenants.add(intervenant);
	}
	
	/**
	 * @return the messages
	 */
	@Override
	public List<IMessage> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	@Override
	public void setMessages(List<IMessage> messages) {
		this.messages = messages;
	}
	
	@Override
	public void addMessage(IMessage message){
		this.messages.add(message);
	}

	@Override
	public void addMessages(List<IMessage> messages) {
		this.messages.addAll(messages);
	}
	
	/**
	 * @return the codis
	 */
	public Codis getCodis() {
		return codis;
	}

	/**
	 * @param codis the codis to set
	 */
	public void setCodis(Codis codis) {
		this.codis = codis;
	}

	@Override
	public void onResponse(JSONObject json) {
		Log.i("Intervention - RESPONSE", json.toString());
		try {
			if(_id.equals("")){
				// the Intervention has just been created in database 
				_id = (String) json.get("id");
			}
			_rev = (String) json.get("rev");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		if(error.getMessage() != null){
			Log.v("agetacpp - Intervention", error.getMessage());
			if(error.getCause() != null){
				Log.v("agetacpp - Intervention", error.getCause().toString());
			}
		}
		else{
			Log.v("agetacpp - Intervention", error.toString());
		}
	}

	@Override
	public String getUrl(int method) {
		return DataBaseCommunication.BASE_URL + _id;
	}

	@Override
	public JSONObject getData() {
		try {
			return JsonSerializer.serialize(this);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save() {
		DataBaseCommunication.sendPut(this);
	}

	@Override
	public void update() {
		DataBaseCommunication.sendPut(this);
	}

	@Override
	public void delete() {
		DataBaseCommunication.sendDelete(this);
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public void setId(String id) {
		this._id = id;
	}

	@Override
	public String getRev() {
		return _rev;
	}

	@Override
	public void setRev(String rev) {
		this._rev = rev;
	}

	@Override
	public List<Environnement> getEnvironnements() {
		return environnements;
	}

	@Override
	public void setEnvironnements(List<Environnement> environnements) {
		this.environnements = environnements;
	}

	@Override
	public void addEnvironnement(Environnement environnement) {
		this.environnements.add(environnement);
	}

	@Override
	public void addEnvironnements(List<Environnement> environnements) {
		this.environnements.addAll(environnements);
	}
	
	public void updateDepandencies(){
		
		for(IMoyen moyen : moyens){
			moyen.setIntervention(this);
		}
		
		for(Environnement environnement : environnements){
			environnement.setIntervention(this);
		}
		
		for(Intervenant intervenant : intervenants){
			intervenant.setIntervention(this);
		}
		
		if( codis != null ) codis.setIntervention(this);
		
		for(IMessage message : messages ){
			message.setIntervention(this);
		}
		
		for(Groupe gr : groupes){
			gr.setIntervention(this);
		}
		
	}
	
	public String toString(){
		String returned = "";
		returned += "{\n \t_id : "+ this._id + ",\n";
		returned += " \t_rev : "+ this._rev + ",\n";
		returned += " \taddress : "+ this.adresse + ",\n";
		returned += " \tcode sinistre : "+ this.codeSinistre + ",\n";
		returned += " \tnom : "+ this.nom + ",\n";
		returned += " \tCODIS : "+ this.codis == null ? "null" : codis.toString() + ",\n";
		returned += " \tIntervents : [\n";
			for( Intervenant intervenant : intervenants ){
				returned += intervenant.toString() + ",\n";
			}
		returned += " \t],";
		
		return returned;
	}
	
	public void delete( Environnement environnement ){
		environnements.remove(environnement);
		save();
	}
	
	public void delete( Groupe groupe ){
		groupes.remove(groupe);
		save();
	}
	
	public void delete( Message message ){
		messages.remove(message);
		save();
	}
	
	public void delete( Moyen moyen ){
		moyens.remove(moyen);
		save();
	}
	
	public void delete( Secteur secteur ){
		this.secteurs.remove(secteur);
		save();
	}
	
	public void delete( TypeMoyen typeMoyen ){
	}
	
	public void delete( UserAvailable user ){
	}
	

	public List<Groupe> getGroupes() {
		return groupes;
	}

	public void setGroupes(List<Groupe> groupes) {
		this.groupes = groupes;
	}

	public List<Secteur> getSecteurs() {
		return secteurs;
	}

	public void setSecteurs(List<Secteur> secteurs) {
		this.secteurs = secteurs;
	}
	
}
