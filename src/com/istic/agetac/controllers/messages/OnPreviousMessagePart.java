package com.istic.agetac.controllers.messages;

import android.view.View;
import android.view.View.OnClickListener;

import com.istic.agetac.activities.MessageActivity;

public class OnPreviousMessagePart implements OnClickListener {

	private MessageActivity activity;
	
	public OnPreviousMessagePart( MessageActivity activity )
	{
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		activity.message_previous(v);
	}
	
}
