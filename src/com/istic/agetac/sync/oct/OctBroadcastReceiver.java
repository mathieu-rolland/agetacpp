package com.istic.agetac.sync.oct;

import java.util.List;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.istic.agetac.fragments.OctFragment;
import com.istic.sit.framework.sync.ASyncReceiver;
import com.istic.sit.framework.sync.ASynchornisationService;

public class OctBroadcastReceiver extends ASyncReceiver{

	private OctFragment octActivity;
	
	public OctBroadcastReceiver(OctFragment octActivity){
		this.octActivity = octActivity;
	}
	@Override
	public void setPendingIntent(PendingIntent pending) {
		super.pendingIntent = pending;
		
	}

	@Override
	public void onReceive(Context arg0, Intent intent) {
		octActivity.update((List)intent.getExtras()
				.getParcelableArrayList(ASynchornisationService.SYNC_SERVICE_EXTRA));
	}

}
