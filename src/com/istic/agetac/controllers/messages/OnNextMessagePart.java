package com.istic.agetac.controllers.messages;

import com.istic.agetac.activities.MessageActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class OnNextMessagePart implements OnClickListener {

	private MessageActivity activity;
	
	public OnNextMessagePart( MessageActivity activity ){
		this.activity = activity;
	}
	
	@Override
	public void onClick(View v) {
		activity.message_next(v);
	}

}
