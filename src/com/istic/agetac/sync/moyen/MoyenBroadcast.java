package com.istic.agetac.sync.moyen;

import java.util.List;

import com.istic.agetac.fragments.SitacFragment;
import com.istic.sit.framework.sync.ASynchornisationService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MoyenBroadcast extends BroadcastReceiver{

	private SitacFragment fragment;
	
	public MoyenBroadcast( SitacFragment fragment ){
		super();
		this.fragment = fragment;
	}
	
	@Override
	public void onReceive(Context arg0, Intent intent) {
		Log.d("Moyen","Receive");
		fragment.updateEntities((List)intent.getExtras()
				.getParcelableArrayList( MoyenIntentService.CHANNEL ));
		fragment.updateGraphics((List) intent.getExtras().getParcelableArrayList(MoyenIntentService.CHANNEL_LIST));
	}

}
