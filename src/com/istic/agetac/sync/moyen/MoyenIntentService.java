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

import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Line;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.model.Entity;

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

	@Override
	protected void onHandleIntent(Intent intent) {
		intervention = AgetacppApplication.getIntervention();
		if( intervention != null ){
			MapQuerySynchro mqs = new MapQuerySynchro(Intervention.class, intervention, this);
			DataBaseCommunication.sendGet( mqs );
		}
	}
	
	protected void notifyResponseSuccess( List<Entity> entities, List<Line> lines ){
		Intent intentReceiver = new Intent( CHANNEL );
		intentReceiver.setAction( CHANNEL );
		intentReceiver.putParcelableArrayListExtra( CHANNEL , 
				(ArrayList<? extends Parcelable>) entities);
		intentReceiver.putParcelableArrayListExtra( CHANNEL_LIST, 
				(ArrayList<? extends Parcelable>) lines);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intentReceiver);
	}
	
}
