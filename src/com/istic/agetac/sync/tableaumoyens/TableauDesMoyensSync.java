package com.istic.agetac.sync.tableaumoyens;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.model.Intervention;
import com.istic.sit.framework.couch.AObjectRecuperator;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.sync.ASynchornisationService;

public class TableauDesMoyensSync extends ASynchornisationService 
	implements IViewReceiver<IMoyen> {

	public static final String FILTER_MESSAGE_RECEIVER = "com.istic.agetac++.sync.moyen";
	private Intervention intervention;
	
	public TableauDesMoyensSync(){
		super("Sync tableau des moyens");
	}
	
	public TableauDesMoyensSync(String name) {
		super(name);
	}

	@Override
	public void notifyResponseSuccess(List<IMoyen> objects) {
		Intent intentReceiver = new Intent( FILTER_MESSAGE_RECEIVER );
		intentReceiver.putParcelableArrayListExtra( ASynchornisationService.SYNC_SERVICE_EXTRA , 
				(ArrayList<? extends Parcelable>) objects);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intentReceiver);
	}

	@Override
	public void notifyResponseFail(VolleyError error) {
		
	}

	@Override
	public String getName() {
		return "Sync tableau des moyens";
	}

	@Override
	public int getIntervalToRefresh() {
		return 4;
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		intervention = AgetacppApplication.getIntervention();
		if( intervention != null ){
			DataBaseCommunication.sendGet(new InterventionReceiver());
		}
	}

	private class InterventionReceiver extends AObjectRecuperator<Intervention>{

		public InterventionReceiver(){
			super(Intervention.class, intervention.getId() );
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.e("LoginActivity", error.getMessage() == null 
					? error.toString() : error.getMessage() );
		}
		
		@Override
		public void onResponse(Intervention objet) {
			notifyResponseSuccess(objet.getMoyens());
		}
	}
	
}
