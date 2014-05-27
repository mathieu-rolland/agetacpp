package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.Color;

import com.istic.agetac.api.model.IMessage.Message_part;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.sit.framework.model.Property;

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
		mo1.setLibelle("VSAV 1");
		
		Property property1 = new Property();
		property1.setNom(Moyen.NAME_PROPERTY_HOUR_ENGAGEMENT);
		property1.setValeur(Moyen.FORMATER.format(new Date()));
		mo1.addPropriete(property1);
		mo1.setSecteur(s5);
		
		inter1.addMoyen(mo1);
		
		Moyen mo2 = new Moyen(TypeMoyen.VSAV, inter1);
		mo2.setLibelle("VSAV 2");
		
		Property property2 = new Property();
		property2.setNom(Moyen.NAME_PROPERTY_HOUR_ENGAGEMENT);
		property2.setValeur(Moyen.FORMATER.format(new Date()));
		mo2.addPropriete(property2);
		mo2.setSecteur(s5);
		
		inter1.addMoyen(mo2);
		
		Moyen mo1bis = new Moyen(TypeMoyen.VSAV, inter1);
		mo1bis.setLibelle("VSAV 3");
		
		Property property1Bis = new Property();
		property1Bis.setNom(Moyen.NAME_PROPERTY_HOUR_ENGAGEMENT);
		property1Bis.setValeur(Moyen.FORMATER.format(new Date()));
		mo1bis.addPropriete(property1Bis);
		mo1bis.setSecteur(s5);
		
		inter1.addMoyen(mo1bis);
		
		Moyen mo3 = new Moyen(TypeMoyen.FPT, inter1);
		mo3.setLibelle("FPT 2");
		
		Property property3 = new Property();
		property3.setNom(Moyen.NAME_PROPERTY_HOUR_ENGAGEMENT);
		property3.setValeur(Moyen.FORMATER.format(new Date()));
		mo3.addPropriete(property3);
		mo3.setSecteur(s5);		
		
		inter1.addMoyen(mo3);
			
		Moyen mo4 = new Moyen(TypeMoyen.CCGC, inter1);
		mo4.setLibelle("CCGC 1");
		
		Property property4 = new Property();
		property4.setNom(Moyen.NAME_PROPERTY_HOUR_ENGAGEMENT);
		property4.setValeur(Moyen.FORMATER.format(new Date()));
		mo4.addPropriete(property4);
		mo4.setSecteur(s5);		
		
		inter1.addMoyen(mo4);
		
		Moyen mo5 = new Moyen(TypeMoyen.VLCC, inter1);
		mo5.setLibelle("VLCC 1");
		
		Property property5 = new Property();
		property5.setNom(Moyen.NAME_PROPERTY_HOUR_ENGAGEMENT);
		property5.setValeur(Moyen.FORMATER.format(new Date()));
		mo5.addPropriete(property5);
		mo5.setSecteur(s5);		
		
		inter1.addMoyen(mo5);
		
		List<IMoyen> listMoyen = new ArrayList<IMoyen>();
		listMoyen.add(mo1);
		listMoyen.add(mo2);
		
		List<IMoyen> listMoyen2 = new ArrayList<IMoyen>();
		listMoyen2.add(mo3);

		Moyen g1 = new Moyen(listMoyen, inter1);
		g1.setLibelle("Groupe 1 - Rennes");
		Moyen g2 = new Moyen(listMoyen2, inter1);
		g2.setLibelle("Groupe 2 - Rennes ");
		
		inter1.addGroupe(g1);
		inter1.addGroupe(g2);
		
	      OCT oct = new OCT( s1, s2 , s3, s4, "70", "70", "30", "30", "11", "30", "12", "30", "13", "30", "14" );
	        inter1.setOct( oct );
		
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
