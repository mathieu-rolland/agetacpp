package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.Color;
import android.util.Log;

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

		
		try{
		    IMoyen moyen1 = new Moyen( TypeMoyen.VSAV, inter1 );
	        moyen1.setHDemande( new Date(2014,27,05,14,22) );
	        
	        IMoyen moyen2 = new Moyen( TypeMoyen.VSAV, inter1 );
	        moyen2.setHDemande( new Date(2014,27,05,14,22) );
            moyen2.setHEngagement( new Date(2014,27,05,15,00) ) ;
            moyen2.setLibelle( "moyen2" );
            
            IMoyen moyen3 = new Moyen( TypeMoyen.VSAV, inter1 );
            moyen3.setHDemande( new Date(2014,27,05,14,22) );
            moyen3.setLibelle( "moyen3" );
            moyen3.setHEngagement(  new Date(2014,27,05,15,00) ) ;
            moyen3.setHArrival( new Date(2014,27,05,15,30) );
            moyen3.setSecteur( s2 );
            
            IMoyen moyen4 = new Moyen( TypeMoyen.VSAV, inter1 );
            moyen4.setHDemande( new Date(2014,27,05,14,22) );
            moyen4.setLibelle( "moyen4" );
            moyen4.setHEngagement(  new Date(2014,27,05,15,00) ) ;
            moyen4.setHArrival( new Date(2014,27,05,15,30) );
            moyen4.setSecteur( s2 );
            moyen4.setHFree( new Date(2014,27,05,17,30) );
            
            IMoyen moyen5 = new Moyen( TypeMoyen.VSAV, inter1 );
            moyen5.setHDemande( new Date(2014,27,05,14,22) );
            moyen5.setLibelle( "moyen5" );
            moyen5.setHEngagement(  new Date(2014,27,05,15,00) ) ;
            
            IMoyen moyen6 = new Moyen( TypeMoyen.VSAV, inter1 );
            moyen6.setHDemande( new Date(2014,27,05,14,22) );
            moyen6.setLibelle( "moyen6" );
            moyen6.setHEngagement(  new Date(2014,27,05,15,43) ) ;

            List<IMoyen> listeMoyenDuGroupe1 = new ArrayList<IMoyen>();
            listeMoyenDuGroupe1.add( moyen1 );
            listeMoyenDuGroupe1.add( moyen2 );
            
            List<IMoyen> listeMoyenDuGroupe2 = new ArrayList<IMoyen>();
            listeMoyenDuGroupe2.add( moyen3 );
            listeMoyenDuGroupe2.add( moyen4 );
            
            
            IMoyen groupe1 = new Moyen(listeMoyenDuGroupe1,inter1);
            groupe1.setLibelle( "Groupe 1" );
            IMoyen groupe2 = new Moyen(listeMoyenDuGroupe2,inter1);
            groupe2.setLibelle( "Groupe 2" );
            
            List<IMoyen> moyensAAjouterALintervention = new ArrayList<IMoyen>();
            moyensAAjouterALintervention.add( moyen5 );
            moyensAAjouterALintervention.add( moyen6 );
            moyensAAjouterALintervention.add(groupe1);
            moyensAAjouterALintervention.add(groupe2);
            
            inter1.setMoyens( moyensAAjouterALintervention );
	        
		}catch(Exception e)
		{
		    Log.e("CREATION BASE","Erreur dans les dates des moyens");
		}
		
		
		
		
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
