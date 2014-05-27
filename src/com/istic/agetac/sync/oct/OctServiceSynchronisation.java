package com.istic.agetac.sync.oct;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.OCT;
import com.istic.agetac.pattern.observer.Observer;
import com.istic.agetac.pattern.observer.Subject;
import com.istic.sit.framework.couch.AObjectRecuperator;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.JsonSerializer;
import com.istic.sit.framework.sync.ASynchornisationService;

public class OctServiceSynchronisation extends ASynchornisationService implements IViewReceiver<OCT>, Observer
{
	
	private String name;
	public static final String FILTER_OCT_RECEIVER = "com.istic.agetac++.sync.oct";
	private Intervention intervention;
	
	public OctServiceSynchronisation(String name) {
		super(name);
		this.name = name;
		intervention = AgetacppApplication.getIntervention();
	}
	
	public OctServiceSynchronisation(){
		super(FILTER_OCT_RECEIVER);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getIntervalToRefresh() {
		return 5;
	}

	@Override
	public void update(Subject subject) {
		//QUESTION: POUR L'OCT ON NE TRANSMET PAS UNE LISTE MAIS UN OBJET UNIQUE.
		ArrayList<OCT> list = new ArrayList<OCT>();
		list.add(((Intervention) subject).getOct());
		notifyResponseSuccess( list );
		
	}

	@Override
	public void notifyResponseFail(VolleyError error) {
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		//TODO Récupérer l'intervention synchronisée :
		intervention = AgetacppApplication.getIntervention();
		Log.d("OCT" ,"handle" );
		if( intervention == null ) return; 
		DataBaseCommunication.sendGet( new InterventionReceiver() );
	}

	@Override
	public void notifyResponseSuccess(List<OCT> objects) {
		Log.d("OCT", "On response success ");
		Intent intentReceiver = new Intent( FILTER_OCT_RECEIVER );
		intentReceiver.putParcelableArrayListExtra( ASynchornisationService.SYNC_SERVICE_EXTRA , 
				(ArrayList<? extends Parcelable>) objects);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intentReceiver);
		
	}

	private class InterventionReceiver extends AObjectRecuperator<Intervention>{

		public InterventionReceiver(){
			super(Intervention.class, intervention.getId() );
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.e("OCT", error.getMessage() == null 
					? error.toString() : error.getMessage() );
		}
		
		@Override
		public void onResponse(Intervention objet) {
			Log.d("OCT", "On response : ");
			try {
				Log.d("OCT", JsonSerializer.serialize(objet).toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			objet.updateDepandencies();
			List<OCT> OCT = new ArrayList<OCT>();
			OCT.add(intervention.getOct());
			notifyResponseSuccess(OCT);
		}
	}
	
}
