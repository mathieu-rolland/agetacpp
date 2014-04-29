package com.istic.agetac.controllers.messages;

import android.view.View;
import android.view.View.OnClickListener;

import com.istic.agetac.fragments.MessageFragment;

public class OnSendMessage implements OnClickListener {

	private MessageFragment activity;
	
	public OnSendMessage( MessageFragment activity )
	{
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		activity.message_validate(v);
	}
	
}
