package com.istic.agetac.controllers.messages;

import android.view.View;
import android.view.View.OnClickListener;

import com.istic.agetac.fragments.MessageFragment;

public class OnPreviousMessagePart implements OnClickListener {

	private MessageFragment activity;
	
	public OnPreviousMessagePart( MessageFragment activity )
	{
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		activity.message_previous(v);
	}
	
}
