package com.istic.agetac.sync.message;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.api.model.IMessage;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.pattern.observer.Observer;
import com.istic.agetac.pattern.observer.Subject;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.sync.ASynchornisationService;

public class MessageServiceSynchronisation 
	extends ASynchornisationService implements IViewReceiver<IMessage>, Observer {

	private String name;
	public static final String FILTER_MESSAGE_RECEIVER = "com.istic.agetac++.sync.messages";
	private Intervention intervention;
	
	public MessageServiceSynchronisation(String name) {
		super(name);
		this.name = name;
	}

	public MessageServiceSynchronisation(){
		super(FILTER_MESSAGE_RECEIVER);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getIntervalToRefresh() {
		return 10;
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		intervention = AgetacppApplication.getIntervention();
		if( intervention != null ){
			MessageQuerySynchro mqs = new MessageQuerySynchro(Intervention.class, intervention, this);
			DataBaseCommunication.sendGet( mqs );
		}
	}

	@Override
	public void notifyResponseSuccess(List<IMessage> objects) {
		Log.d("Antho", "NB MESSAGE DE SYNC : " + objects.size());
		Intent intentReceiver = new Intent( FILTER_MESSAGE_RECEIVER );
		intentReceiver.putParcelableArrayListExtra( ASynchornisationService.SYNC_SERVICE_EXTRA , 
				(ArrayList<? extends Parcelable>) objects);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intentReceiver);
	}

	@Override
	public void notifyResponseFail(VolleyError error) {
		
	}

	@Override
	public void update(Subject subject) {
		notifyResponseSuccess( ((Intervention) subject).getMessages() );
	}

}
