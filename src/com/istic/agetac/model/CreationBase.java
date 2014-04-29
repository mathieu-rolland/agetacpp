package com.istic.agetac.model;

import java.util.List;

import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.controllers.dao.MoyensDao;
import com.istic.agetac.exceptions.AddInterventionException;
import com.istic.agetac.model.serializer.AgetacSerializer;
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
		Intervention i1 = new Intervention("263, avenue du Général Leclerc, 35042, Rennes", "FEU DANS ERP");
		i1.addIntervenant(tata);
		i1.setCodis(codis2);
		tata.setIntervention(i1);
		codis2.addIntervention(i1);
		
		tata.save();
		codis2.save();
		i1.save();
	}
	
	public static void testRecupUser(){
		CouchDBUtils.getObjectById(new AObjectRecuperator<Codis>(Codis.class, "2c536599-9699-44cf-a2ed-0d213d0f4a43") {

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
				objet.getUsername();
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
}
