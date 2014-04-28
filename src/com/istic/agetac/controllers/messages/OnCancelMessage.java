package com.istic.agetac.controllers.messages;

import com.istic.agetac.activities.MessageActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class OnCancelMessage implements OnClickListener {

	private MessageActivity activity;
	
	public OnCancelMessage( MessageActivity activity )
	{
		this.activity = activity;
	}
	
	@Override
	public void onClick(View v) {
		activity.message_cancel(v);
	}
	
}
