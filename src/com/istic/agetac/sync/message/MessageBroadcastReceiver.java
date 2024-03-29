package com.istic.agetac.sync.message;

import java.util.List;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.istic.agetac.fragments.MessageFragment;
import com.istic.sit.framework.sync.ASyncReceiver;
import com.istic.sit.framework.sync.ASynchornisationService;

public class MessageBroadcastReceiver extends ASyncReceiver{

	private MessageFragment messageActivity;
	
	public MessageBroadcastReceiver(MessageFragment msgActivity){
		this.messageActivity = msgActivity;
	}
	
	@Override
	public void setPendingIntent(PendingIntent pending) {
		super.pendingIntent = pending;
	}

	@Override
	public void onReceive(Context arg0, Intent intent) {
		messageActivity.update((List)intent.getExtras()
				.getParcelableArrayList(ASynchornisationService.SYNC_SERVICE_EXTRA));
	}

}
