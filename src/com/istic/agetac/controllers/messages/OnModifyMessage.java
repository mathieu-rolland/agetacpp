package com.istic.agetac.controllers.messages;

import com.istic.agetac.activities.MessageActivity;
import com.istic.agetac.api.model.IMessage;

import android.view.View;
import android.view.View.OnClickListener;

public class OnModifyMessage implements OnClickListener {

	private IMessage message;
	private MessageActivity activity;
	
	public OnModifyMessage( IMessage message , MessageActivity activity ){
		this.message = message;
	}
	
	@Override
	public void onClick(View arg0) {
		activity.setCurrentMessage(message);
	}

}
