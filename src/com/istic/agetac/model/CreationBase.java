package com.istic.agetac.model;

import java.util.Date;
import java.util.List;

import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.api.model.IMessage.Message_part;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controllers.dao.MoyensDao;
import com.istic.agetac.exceptions.AddInterventionException;
import com.istic.agetac.pattern.observer.Observer;
import com.istic.agetac.pattern.observer.Subject;
import com.istic.sit.framework.api.model.IPosition.AXIS;
import com.istic.sit.framework.couch.AObjectRecuperator;
import com.istic.sit.framework.couch.CouchDBUtils;
import com.istic.sit.framework.model.CoordonateGPS;
import com.istic.sit.framework.model.Representation;

public class CreationBase {

	public static void createMoyen(){
		// Moyen m1
		Moyen m1 = new Moyen("VSAV");
		CoordonateGPS pos1 = new CoordonateGPS();
		pos1.set(AXIS.LAT, 48.11943311944856);
		pos1.set(AXIS.LNG, -1.64665337651968);
		m1.setPosition(pos1);
		m1.setLibelle("VSAV1");
		m1.setRepresentationOK(new Representation(R.drawable.vsav));
		m1.setRepresentationKO(new Representation(R.drawable.ic_launcher));
		m1.setSecteur("S.L.L");
		
		m1.save();
	}
	
	public static void createUser() throws AddInterventionException{
		// User tata
		Intervenant tata = new Intervenant("tata", "tata");
		tata.setPassword("tata");
		
		// User codis2
		Codis codis2 = new Codis("codis2", "codis2");
		codis2.setPassword("codis2");
		
		// Intervention i1
		Intervention i1 = new Intervention("263, avenue du G�n�ral Leclerc, 35042, Rennes", "FEU DANS ERP");
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
	
	public static void testRecupUser(){
		CouchDBUtils.getObjectById(new AObjectRecuperator<Codis>(Codis.class, "3fcd9849-a748-4a56-88ba-59c3b291e0b4") {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				if(error.getMessage() != null){
					Log.e("CreationBase", error.getMessage());
				}
				else{
					Log.e("CreationBase", error.toString());
				}
			}

			@Override
			public void onResponse(Codis objet) {
				// TODO Auto-generated method stub
//				objet.getInterventions().get(0).getIntervenants(new Observer() {
//					
//					@Override
//					public void update(Subject subject) {
//						// TODO Auto-generated method stub
//						((Intervention)subject).getIntervenants();
//					}
//				});
				Intervention inter = new Intervention();
				inter.setAdresse("5 rue de la piqueti�re");
				inter.setCodeSinistre("feu de cigarette");
				inter.setCodis(objet);
			
				objet.addIntervention(inter);
				objet.save();
				inter.save();
			}
		});
	}
	
	
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
				Message m1 = new Message();
				m1.setDateEmission(new Date());
				m1.setText(Message_part.JE_DEMANDE, "ta m�re");
				m1.setText(Message_part.JE_FAIS, "de la merde");
				m1.setText(Message_part.JE_PREVOIS, "de coder");
				m1.setText(Message_part.JE_SUIS, "bourr�");
				m1.setText(Message_part.JE_VOIS, "une belle blonde");
				m1.setIntervention(objet);
				objet.save();
				m1.save();
			}
		});
	}
}
