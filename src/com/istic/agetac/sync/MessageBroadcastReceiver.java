package com.istic.agetac.sync;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.istic.agetac.activities.MessageActivity;
import com.istic.sit.framework.sync.ASyncReceiver;

public class MessageBroadcastReceiver extends ASyncReceiver{

	private MessageActivity messageActivity;
	
	public MessageBroadcastReceiver(MessageActivity msgActivity){
		this.messageActivity = msgActivity;
	}
	
	@Override
	public void setPendingIntent(PendingIntent pending) {
		super.pendingIntent = pending;
	}

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		messageActivity.update(null);
	}

}
