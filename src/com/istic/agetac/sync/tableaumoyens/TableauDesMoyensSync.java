package com.istic.agetac.sync.tableaumoyens;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.VolleyError;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.controllers.dao.MoyensDao;
import com.istic.agetac.model.Moyen;
import com.istic.sit.framework.sync.ASynchornisationService;

public class TableauDesMoyensSync extends ASynchornisationService 
	implements IViewReceiver<Moyen> {

	public static final String FILTER_MESSAGE_RECEIVER = "com.istic.agetac++.sync.moyen";
	
	public TableauDesMoyensSync(){
		super("Sync tableau des moyens");
	}
	
	public TableauDesMoyensSync(String name) {
		super(name);
	}

	@Override
	public void notifyResponseSuccess(List<Moyen> objects) {
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
		MoyensDao mdao = new MoyensDao(this);
		mdao.findAll();
	}

}
