package com.istic.agetac.controllers.messages;

import com.istic.agetac.fragments.MessageFragment;

import android.view.View;
import android.view.View.OnClickListener;

public class OnNextMessagePart implements OnClickListener {

	private MessageFragment activity;
	
	public OnNextMessagePart( MessageFragment activity ){
		this.activity = activity;
	}
	
	@Override
	public void onClick(View v) {
		activity.message_next(v);
	}

}
