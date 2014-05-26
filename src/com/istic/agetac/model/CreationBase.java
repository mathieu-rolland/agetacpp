package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.Color;
import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.api.model.IMessage.Message_part;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controllers.dao.InterventionDao;
import com.istic.agetac.controllers.dao.MoyensDao;
import com.istic.agetac.controllers.dao.UserDao;
import com.istic.agetac.exceptions.AddInterventionException;
import com.istic.sit.framework.api.model.IPosition.AXIS;
import com.istic.sit.framework.couch.AObjectRecuperator;
import com.istic.sit.framework.couch.CouchDBUtils;
import com.istic.sit.framework.model.CoordonateGPS;
import com.istic.sit.framework.model.Property;
import com.istic.sit.framework.model.Representation;

public class CreationBase {

	public static void createMoyen(){
		// Moyen m1
		Moyen m1 = new Moyen(TypeMoyen.VSAV, AgetacppApplication.getIntervention());
		CoordonateGPS pos1 = new CoordonateGPS();
		pos1.set(AXIS.LAT, 48.11943311944856);
		pos1.set(AXIS.LNG, -1.64665337651968);
		m1.setPosition(pos1);
		m1.setLibelle("VSAV1");
		m1.setRepresentationOK(new Representation(R.drawable.vsav_ko));
		m1.setRepresentationKO(new Representation(R.drawable.ic_launcher));
		Secteur secteur = new Secteur();
		secteur.setName("INC");
		secteur.setColor(Color.parseColor("#ff0000"));
		m1.setSecteur(secteur);
		
		m1.save();
		
		m1 = new Moyen(TypeMoyen.VSAV, AgetacppApplication.getIntervention());
		pos1 = new CoordonateGPS();
		pos1.set(AXIS.LAT, 49.11943311944856);
		pos1.set(AXIS.LNG, -1.64665337651968);
		m1.setPosition(pos1);
		m1.setLibelle("VSAV2");
		m1.setRepresentationOK(new Representation(R.drawable.vsav_ko));
		m1.setRepresentationKO(new Representation(R.drawable.ic_launcher));
		Secteur secteur1 = new Secteur();
		secteur1.setName("INC");
		secteur1.setColor(Color.parseColor("#ff0000"));
		m1.setSecteur(secteur1);
		
		m1.save();
		
		m1 = new Moyen(TypeMoyen.VSAV, AgetacppApplication.getIntervention());
		pos1 = new CoordonateGPS();
		pos1.set(AXIS.LAT, 50.11943311944856);
		pos1.set(AXIS.LNG, -1.64665337651968);
		m1.setPosition(pos1);
		m1.setLibelle("VSAV3");
		m1.setRepresentationOK(new Representation(R.drawable.vsav_ko));
		m1.setRepresentationKO(new Representation(R.drawable.ic_launcher));
		secteur1 = new Secteur();
		secteur1.setName("INC");
		secteur1.setColor(Color.parseColor("#ff0000"));
		m1.setSecteur(secteur1);
		
		m1.save();
	}
	
	public static void createUser() throws AddInterventionException{
		// User tata
		Intervenant tata = new Intervenant("TUYAUX", "toto");
		tata.setPassword("toto");
		
		// User codis2
		Codis codis2 = new Codis("CODIS", "codis");
		codis2.setPassword("codis");
		
		// Intervention i1
		Intervention i1 = new Intervention("263, avenue du G�n�ral Leclerc, 35042, Rennes", "Intervention 1", "FEU DANS ERP");
		i1.addIntervenant(tata);
		i1.setCodis(codis2);
		tata.setIntervention(i1);
		codis2.addIntervention(i1);
		
		tata.save();
		codis2.save();
		i1.save();
	}
	
	public static void createCodis()
	{
		Codis c = new Codis("codis2", "codis2");
		c.setPassword("codis2");
		c.save();
	}
	
//	public static void testRecupUser(){
//		CouchDBUtils.getObjectById(new AObjectRecuperator<Codis>(Codis.class, "3fcd9849-a748-4a56-88ba-59c3b291e0b4") {
//
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				// TODO Auto-generated method stub
//				if(error.getMessage() != null){
//					Log.e("CreationBase", error.getMessage());
//				}
//				else{
//					Log.e("CreationBase", error.toString());
//				}
//			}
//
//			@Override
//			public void onResponse(Codis objet) {
//				// TODO Auto-generated method stub
////				objet.getInterventions().get(0).getIntervenants(new Observer() {
////					
////					@Override
////					public void update(Subject subject) {
////						// TODO Auto-generated method stub
////						((Intervention)subject).getIntervenants();
////					}
////				});
//				Intervention inter = new Intervention();
//				inter.setAdresse("5 rue de la piqueti�re");
//				inter.setCodeSinistre("feu de cigarette");
//				inter.setCodis(objet);
//			
//				objet.addIntervention(inter);
//				objet.save();
//				inter.save();
//			}
//		});
//	}
	
	
	public static void testRecupMoyen(){
		new MoyensDao(new IViewReceiver<Moyen>() {
			
			@Override
			public void notifyResponseSuccess(List<Moyen> objects) {
				// TODO Auto-generated method stub
				objects.isEmpty();
			}
			
			@Override
			public void notifyResponseFail(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		}).findAll();
	}
	
	public static void createMessage(){
		CouchDBUtils.getObjectById(new AObjectRecuperator<Intervention>(Intervention.class, "42e5cfbd-3d32-4e96-abbf-ef5de3034b4e") {

			@Override
			public void onErrorResponse(VolleyError error) {
				if(error.getMessage() != null){
					Log.e("CreationBase", error.getMessage());
				}
				else{
					Log.e("CreationBase", error.toString());
				}
			}

			@Override
			public void onResponse(Intervention objet) {
				// TODO Auto-generated method stub
				Message m1 = new Message( AgetacppApplication.getIntervention() );
				m1.setDateEmission(new Date());
				m1.setText(Message_part.JE_DEMANDE, "1 FPT et 2 VSAV");
				m1.setText(Message_part.JE_FAIS, "Une coupure de l'alimentation du b�timent et la mise en s�curit� du personnel.");
				m1.setText(Message_part.JE_PREVOIS, "De stopper la propagation du feu aux �tages sup�rieurs");
				m1.setText(Message_part.JE_SUIS, "CDG TUYAUX");
				m1.setText(Message_part.JE_VOIS, "Un feu violent dans le b�timent 12D");
				m1.setIntervention(objet);
				objet.save();
				m1.save();
			}
		});
	}
	
	public static void createCleanBase(){
		// Users
		Codis vincent = new Codis("Vincent", "vincent");
		vincent.setPassword("vincent");
		Codis marion = new Codis("Marion", "marion");
		marion.setPassword("marion");
		Intervenant antho = new Intervenant("Anthony", "antho");
		antho.setPassword("antho");	
		Intervenant maxime = new Intervenant("Maxime", "maxime");
		maxime.setPassword("maxime");
		Intervenant mathieu = new Intervenant("Mathieu", "mathieu");
		mathieu.setPassword("mathieu");
		Intervenant christophe = new Intervenant("Christophe", "christophe");
		christophe.setPassword("christophe");
		Intervenant thomas = new Intervenant("Thomas", "thomas");
		thomas.setPassword("thomas");
		
		// Interventions
		Intervention inter1 = new Intervention("263, avenue du G�n�ral Leclerc, 35042, Rennes", "Intervention 1", "FEU DANS ERP");
		inter1.setCodis(vincent);
		vincent.addIntervention(inter1);
		inter1.addIntervenant(thomas);
		thomas.setIntervention(inter1);
		
		// Messages
		Message msg1 = new Message(inter1);
		msg1.setDateEmission(new Date());
		msg1.setText(Message_part.JE_DEMANDE, "un VSAV, 2 FPT");
		msg1.setText(Message_part.JE_FAIS, "ma prise de COS");
		msg1.setText(Message_part.JE_PREVOIS, "une intervention longue et difficile");
		msg1.setText(Message_part.JE_SUIS, "Mathieu, COS");
		msg1.setText(Message_part.JE_VOIS, "un blessé à la cuisse dans l'aile droite du batiment");
		
		Message msg2 = new Message(inter1);
		msg2.setDateEmission(new Date());
		msg2.setText(Message_part.JE_DEMANDE, "Rien");
		msg2.setText(Message_part.JE_FAIS, "Mise en sécurité");
		msg2.setText(Message_part.JE_PREVOIS, "une propagation généralisé");
		msg2.setText(Message_part.JE_SUIS, "Mathieu, COS");
		msg2.setText(Message_part.JE_VOIS, "un batiment en feu sur sa partie droite");
		
		inter1.addMessage(msg1);
		inter1.addMessage(msg2);
		
		//Secteurs
		Secteur s1 = new Secteur();
		s1.setColor(Color.parseColor("#f8f8f8"));
		s1.setName("SLL");
		
		Secteur s2 = new Secteur();
		s2.setColor(Color.parseColor("#ce8bec"));
		s2.setName("SAP");
		
		Secteur s3 = new Secteur();
		s3.setColor(Color.parseColor("#85ba8e"));
		s3.setName("ALIM");
		
		Secteur s4 = new Secteur();
		s4.setColor(Color.parseColor("#992f2f"));
		s4.setName("INC");
		
		Secteur s5 = new Secteur();
		s5.setColor(Color.parseColor("#ce8bec"));
		s5.setName("CRM");
		
		inter1.addSecteur(s1);
		inter1.addSecteur(s2);
		inter1.addSecteur(s3);
		inter1.addSecteur(s4);
		inter1.addSecteur(s5);
		
		//moyens
		Moyen mo1 = new Moyen(TypeMoyen.VSAV, AgetacppApplication.getIntervention());
		mo1.setLibelle("VSAV1");
		mo1.setRepresentationKO(new Representation(R.drawable.vsav_ko));
		mo1.setRepresentationOK(new Representation(R.drawable.vsav_ko));
		
		Property property1 = new Property();
		property1.setNom(Moyen.NAME_PROPERTY_HOUR_ENGAGEMENT);
		property1.setValeur(Moyen.FORMATER.format(new Date()));
		mo1.addPropriete(property1);
		mo1.setSecteur(s5);
		
		inter1.addMoyen(mo1);
		
		Moyen mo1bis = new Moyen(TypeMoyen.VSAV, AgetacppApplication.getIntervention());
		mo1bis.setLibelle("VSAV 3");
		mo1bis.setRepresentationKO(new Representation(R.drawable.vsav_ko));
		mo1bis.setRepresentationOK(new Representation(R.drawable.vsav_ko));
		
		Property property1Bis = new Property();
		property1Bis.setNom(Moyen.NAME_PROPERTY_HOUR_ENGAGEMENT);
		property1Bis.setValeur(Moyen.FORMATER.format(new Date()));
		mo1bis.addPropriete(property1Bis);
		mo1bis.setSecteur(s5);
		
		inter1.addMoyen(mo1bis);
		
		Moyen mo2 = new Moyen(TypeMoyen.VSAV, AgetacppApplication.getIntervention());
		mo2.setLibelle("VSAV2");
		mo2.setRepresentationKO(new Representation(R.drawable.vsav_ko));
		mo2.setRepresentationOK(new Representation(R.drawable.vsav_ko));
		
		Property property2 = new Property();
		property2.setNom(Moyen.NAME_PROPERTY_HOUR_ENGAGEMENT);
		property2.setValeur(Moyen.FORMATER.format(new Date()));
		mo2.addPropriete(property2);
		mo2.setSecteur(s5);		
		
		inter1.addMoyen(mo2);
		
		List<Moyen> listMoyen = new ArrayList<Moyen>();
		listMoyen.add(mo1);
		listMoyen.add(mo2);
		Groupe g1 = new Groupe("Groupe 1 - Rennes", listMoyen);
		Groupe g2 = new Groupe("Groupe 2 - Rennes", new ArrayList<Moyen>());
		Groupe g3 = new Groupe("Groupe 3 - Rennes", new ArrayList<Moyen>());
		
		inter1.addGroupe(g1);
		inter1.addGroupe(g2);
		inter1.addGroupe(g3);
		
		UserAvailable userPoubelle = new UserAvailable();
		
		Environnement ev1 = new Environnement( AgetacppApplication.getIntervention() );
		ev1.setLibelle("Bouche incendie");

		inter1.addEnvironnement(ev1);
		
		CreationBase.creationEnvStatic();
		
		// sauvegarde en base
		inter1.save();
		userPoubelle.save();
	}
	
	public static void recupIntervention(String id){
		CouchDBUtils.getObjectById(new AObjectRecuperator<Intervention>(Intervention.class, id) {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onResponse(Intervention objet) {
				// TODO Auto-generated method stub
				objet.getCodeSinistre();
			}
		});
	}
	
	public static void viderBase(){
		new UserDao(new IViewReceiver<User>() {
			
			@Override
			public void notifyResponseSuccess(List<User> objects) {
				for (User user : objects) {
					user.delete();
				}
			}
			
			@Override
			public void notifyResponseFail(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		}).findAll();
		new InterventionDao(new IViewReceiver<Intervention>() {
			
			@Override
			public void notifyResponseSuccess(List<Intervention> objects) {
				for (Intervention intervention : objects) {
					intervention.delete();
				}
			}
			
			@Override
			public void notifyResponseFail(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		}).findAll();
		
		new MoyensDao(new IViewReceiver<Moyen>() {
			
			@Override
			public void notifyResponseSuccess(List<Moyen> objects) {
				for (Moyen moyen : objects) {
					moyen.delete();
				}
			}
			
			@Override
			public void notifyResponseFail(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		}).findAll();
//		new ADao<Message>(new IViewReceiver<Message>() {
//
//			@Override
//			public void notifyResponseSuccess(List<Message> objects) {
//				// TODO Auto-generated method stub
//				for (Message message : objects) {
//					message.delete();
//				}
//			}
//
//			@Override
//			public void notifyResponseFail(VolleyError error) {
//				// TODO Auto-generated method stub
//				
//			}
//		}).executeFindAll(Message.class);
	}
	
	public static void createSecteur(){
		Secteur s1 = new Secteur();
		s1.setColor(Color.parseColor("#f8f8f8"));
		s1.setName("SLL");
		s1.save();
		
		Secteur s2 = new Secteur();
		s2.setColor(Color.parseColor("#ce8bec"));
		s2.setName("SAP");
		s2.save();
		
		Secteur s3 = new Secteur();
		s3.setColor(Color.parseColor("#85ba8e"));
		s3.setName("ALIM");
		s3.save();
		
		Secteur s4 = new Secteur();
		s4.setColor(Color.parseColor("#992f2f"));
		s4.setName("INC");
		s4.save();
		
		Secteur s5 = new Secteur();
		s5.setColor(Color.parseColor("#ce8bec"));
		s5.setName("CRM");
		s5.save();
	}
	
	
	public static void CreateTest(){
		
		Codis codis = new Codis("Codis", "codis");
		codis.setPassword("codis");
		
		Intervenant toto = new Intervenant("Toto", "toto");
		toto.setPassword("toto");
		
		Intervenant tata = new Intervenant("Tata", "tata");
		tata.setPassword("tata");
		
		Intervenant titi = new Intervenant("Titi", "titi");
		titi.setPassword("titi");
		
		Intervenant user1 = new Intervenant("User1", "user1");
		user1.setPassword("user1");
		
		Codis codis2 = new Codis("Codis 2", "codis2");
		codis2.setPassword("codis2");
		
		UserAvailable userPoubelle = new UserAvailable();
		
		Intervention inter1 = new Intervention("5 rue de la Piquetière", "Inter 1" , "CONNARD");
		
		Intervention inter2 = new Intervention("7 rue de la Merde", "Inter 2" , "DFDGSDGD");
		
		// Messages
		Message msg1 = new Message(inter1);
		msg1.setDateEmission(new Date());
		msg1.setText(Message_part.JE_DEMANDE, "un VSAV, 2 FPT");
		msg1.setText(Message_part.JE_FAIS, "ma prise de COS");
		msg1.setText(Message_part.JE_PREVOIS, "une intervention longue et difficile");
		msg1.setText(Message_part.JE_SUIS, "Mathieu, COS");
		msg1.setText(Message_part.JE_VOIS, "un blessé à la cuisse dans l'aile droite du batiment");
		
		Message msg2 = new Message(inter1);
		msg2.setDateEmission(new Date());
		msg2.setText(Message_part.JE_DEMANDE, "Rien");
		msg2.setText(Message_part.JE_FAIS, "Mise en sécurité");
		msg2.setText(Message_part.JE_PREVOIS, "une propagation généralisé");
		msg2.setText(Message_part.JE_SUIS, "Mathieu, COS");
		msg2.setText(Message_part.JE_VOIS, "un batiment en feu sur sa partie droite");
		
		//moyens
		Moyen mo1 = new Moyen(TypeMoyen.VSAV, AgetacppApplication.getIntervention());
		mo1.setLibelle("VSAV1");
		mo1.setRepresentationKO(new Representation(R.drawable.vsav_ko));
		mo1.setRepresentationOK(new Representation(R.drawable.vsav_ko));
		
		Property property1 = new Property();
		property1.setNom(Moyen.NAME_PROPERTY_HOUR_ENGAGEMENT);
		property1.setValeur(Moyen.FORMATER.format(new Date()));
		mo1.addPropriete(property1);
		
		Moyen mo2 = new Moyen(TypeMoyen.VSAV, AgetacppApplication.getIntervention());
		mo2.setLibelle("VSAV2");
		mo2.setRepresentationKO(new Representation(R.drawable.vsav_ko));
		mo2.setRepresentationOK(new Representation(R.drawable.vsav_ko));
		
		Property property2 = new Property();
		property2.setNom(Moyen.NAME_PROPERTY_HOUR_ENGAGEMENT);
		property2.setValeur(Moyen.FORMATER.format(new Date()));
		mo2.addPropriete(property2);
		
		Environnement ev1 = new Environnement( AgetacppApplication.getIntervention() );
		ev1.setLibelle("Bouche incendie");
		
		Environnement ev2 = new Environnement( AgetacppApplication.getIntervention() );
		ev2.setLibelle("Bouche à ta mère");
		
		inter1.addIntervenant(toto);
		inter1.setCodis(codis);
		codis.addIntervention(inter1);
		toto.setIntervention(inter1);
		inter1.addMessage(msg1);
		inter1.addMessage(msg2);
		inter1.addMoyen(mo1);
		inter1.addMoyen(mo2);
		inter1.addEnvironnement(ev1);
		inter1.addEnvironnement(ev2);
		
		inter2.addIntervenant(tata);
		inter2.setCodis(codis);
		codis.addIntervention(inter2);
		tata.setIntervention(inter2);
		
		userPoubelle.addUser(titi);
		userPoubelle.addUser(user1);
		userPoubelle.addUser(codis2);
		
		inter1.save();
		inter2.save();
		userPoubelle.save();
	}
	
	public static void creationEnvStatic(){
		EnvironnementsStatic env = new EnvironnementsStatic();
		env.save();
	}
}
