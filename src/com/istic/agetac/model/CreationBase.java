package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.Color;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IMessage.Message_part;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.sit.framework.model.Property;
import com.istic.sit.framework.model.Representation;

public class CreationBase {
	
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
		Intervention inter1 = new Intervention("263, avenue du Général Leclerc, 35042, Rennes", "Intervention 1", "FEU DANS ERP");
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
		Moyen mo1 = new Moyen(TypeMoyen.VSAV, inter1);
		mo1.setLibelle("VSAV1");
		mo1.setRepresentationKO(new Representation(R.drawable.vsav_ko));
		mo1.setRepresentationOK(new Representation(R.drawable.vsav_ko));
		
		Property property1 = new Property();
		property1.setNom(Moyen.NAME_PROPERTY_HOUR_ENGAGEMENT);
		property1.setValeur(Moyen.FORMATER.format(new Date()));
		mo1.addPropriete(property1);
		mo1.setSecteur(s5);
		
		inter1.addMoyen(mo1);
		
		Moyen mo1bis = new Moyen(TypeMoyen.VSAV, inter1);
		mo1bis.setLibelle("VSAV 3");
		mo1bis.setRepresentationKO(new Representation(R.drawable.vsav_ko));
		mo1bis.setRepresentationOK(new Representation(R.drawable.vsav_ko));
		
		Property property1Bis = new Property();
		property1Bis.setNom(Moyen.NAME_PROPERTY_HOUR_ENGAGEMENT);
		property1Bis.setValeur(Moyen.FORMATER.format(new Date()));
		mo1bis.addPropriete(property1Bis);
		mo1bis.setSecteur(s5);
		
		inter1.addMoyen(mo1bis);
		
		Moyen mo2 = new Moyen(TypeMoyen.VSAV, inter1);
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
		Moyen g1 = new Moyen(inter1);
		g1.setLibelle("Groupe 1 - Rennes");
		g1.setGroup(true);
		Moyen g2 = new Moyen(inter1);
		g2.setLibelle("Groupe 2 - Rennes ");
		g2.setGroup(true);
		Moyen g3 = new Moyen(inter1);
		g3.setLibelle("Groupe 3 - Rennes");
		g3.setGroup(true);
		
		inter1.addGroupe(g1);
		inter1.addGroupe(g2);
		inter1.addGroupe(g3);
		
		UserAvailable userAvailable = new UserAvailable();
		userAvailable.addUser(christophe);
		userAvailable.addUser(antho);
		userAvailable.addUser(marion);
		userAvailable.addUser(maxime);
		userAvailable.addUser(mathieu);
		
		
		Environnement ev1 = new Environnement( AgetacppApplication.getIntervention() );
		ev1.setLibelle("Bouche incendie");

		inter1.addEnvironnement(ev1);
		
		EnvironnementsStatic env = new EnvironnementsStatic();
		
		// sauvegarde en base
		inter1.save();
		env.save();
		userAvailable.save();
	}
}
