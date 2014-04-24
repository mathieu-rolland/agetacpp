package com.istic.agetac.sync;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.VolleyError;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.model.ADao;
import com.istic.agetac.model.Message;
import com.istic.sit.framework.sync.ASynchornisationService;
import com.istic.sit.framework.sync.EntitySyncService;

public class MessageServiceSynchronisation 
	extends ASynchornisationService implements IViewReceiver<Message> {

	private String name;
	public static final String FILTER_MESSAGE_RECEIVER = "com.istic.agetac++.sync.messages";
	
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
		new MessageDAO(this).executeFindAll( Message.class );
	}

	private class MessageDAO extends ADao<Message>{

		public MessageDAO(IViewReceiver<Message> iViewReceiver) {
			super(iViewReceiver);
		}
		
	}

	@Override
	public void notifyResponseSuccess(List<Message> objects) {
		Intent intentReceiver = new Intent( FILTER_MESSAGE_RECEIVER );
		intentReceiver.putParcelableArrayListExtra( FILTER_MESSAGE_RECEIVER , 
				(ArrayList<? extends Parcelable>) objects);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intentReceiver);
	}

	@Override
	public void notifyResponseFail(VolleyError error) {
		
	}
	
}
