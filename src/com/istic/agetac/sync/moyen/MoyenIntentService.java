package com.istic.agetac.sync.moyen;

import java.util.ArrayList;
import java.util.List;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Line;
import com.istic.agetac.model.Moyen;
import com.istic.sit.framework.couch.AObjectRecuperator;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.model.ALine;
import com.istic.sit.framework.model.Entity;
import com.istic.sit.framework.sync.ASynchornisationService;
import com.istic.sit.framework.view.MainActivity;

public class MoyenIntentService extends IntentService{

	private Intervention intervention;
	public static final String CHANNEL = "com.istic.agetac.moyen";
	public static final String CHANNEL_LIST = "com.istic.agetac.moyen_list";
	
	public MoyenIntentService() {
		super(CHANNEL);
	}

	// Attribut de type IBinder
	private final IBinder mBinder = new SyncBinder(); 
	private Service mService;
	
	// Le Binder est représenté par une classe interne 
	private class SyncBinder extends Binder {
		MoyenIntentService getService() {
			return MoyenIntentService.this;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	public int getIntervalToRefresh(){
		return 2;
	}
	
	private class InterventionReceiver extends AObjectRecuperator<Intervention>{

		public InterventionReceiver(){
			super(Intervention.class, intervention.getId() );
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.e("MESSAGE", error.getMessage() == null 
					? error.toString() : error.getMessage() );
		}
		
		@Override
		public void onResponse(Intervention objet) {
			objet.updateDepandencies();
			intervention.setRev( objet.getRev() );
			List<Entity> entities = new ArrayList<Entity>();
			for( IMoyen moyen : objet.getMoyens() ){
				entities.add((Moyen) moyen);
			}
			List<ALine> lines = new ArrayList<ALine>();
			for( Line l : objet.getLines() ){
				lines.add(l);
			}
			notifyResponseSuccess( entities, lines );
		}
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		intervention = AgetacppApplication.getIntervention();
		if( intervention != null ){
			DataBaseCommunication.sendGet( new InterventionReceiver() );
		}
	}
	
	protected void notifyResponseSuccess( List<Entity> entities, List<ALine> lines ){
		Intent intentReceiver = new Intent( CHANNEL );
		intentReceiver.putParcelableArrayListExtra( CHANNEL , 
				(ArrayList<? extends Parcelable>) entities);
		intentReceiver.putParcelableArrayListExtra( CHANNEL_LIST, 
				(ArrayList<? extends Parcelable>) lines);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intentReceiver);
	}
	
}
