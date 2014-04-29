package com.istic.agetac.controllers.messages;

import com.istic.agetac.fragments.MessageFragment;

import android.view.View;
import android.view.View.OnClickListener;

public class OnCancelMessage implements OnClickListener {

	private MessageFragment activity;
	
	public OnCancelMessage( MessageFragment activity )
	{
		this.activity = activity;
	}
	
	@Override
	public void onClick(View v) {
		activity.message_cancel(v);
	}
	
}
