package com.istic.agetac.sync.oct;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.VolleyError;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.api.model.IMessage;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.OCT;
import com.istic.agetac.pattern.observer.Observer;
import com.istic.agetac.pattern.observer.Subject;
import com.istic.sit.framework.sync.ASynchornisationService;

public class OctServiceSynchronisation extends ASynchornisationService implements IViewReceiver<OCT>, Observer
{
	
	private String name;
	public static final String FILTER_OCT_RECEIVER = "com.istic.agetac++.sync.oct";

	public OctServiceSynchronisation(String name) {
		super(name);
		this.name = name;
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
		return 10;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
//		new MessageDAO(this).executeFindAll( Message.class );
		Intervention intervention = AgetacppApplication.getIntervention();
		if( intervention == null ) return; 
		intervention.getOct();
		
	}

	@Override
	public void notifyResponseSuccess(List<OCT> objects) {
		Intent intentReceiver = new Intent( FILTER_OCT_RECEIVER );
		intentReceiver.putParcelableArrayListExtra( ASynchornisationService.SYNC_SERVICE_EXTRA , 
				(ArrayList<? extends Parcelable>) objects);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intentReceiver);
		
	}

}
