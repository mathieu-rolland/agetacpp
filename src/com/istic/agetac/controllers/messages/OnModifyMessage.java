package com.istic.agetac.controllers.messages;

import com.istic.agetac.api.model.IMessage;
import com.istic.agetac.fragments.MessageFragment;

import android.view.View;
import android.view.View.OnClickListener;

public class OnModifyMessage implements OnClickListener {

	private IMessage message;
	private MessageFragment activity;
	
	public OnModifyMessage( IMessage message , MessageFragment activity ){
		this.message = message;
		this.activity = activity;
	}
	
	@Override
	public void onClick(View arg0) {
		activity.setCurrentMessage(message);
	}

}
