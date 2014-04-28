package com.istic.agetac.controllers.messages;

import android.view.View;
import android.view.View.OnClickListener;

import com.istic.agetac.api.model.IMessage;

public class OnValidateMessage implements OnClickListener {

	private IMessage message;
	
	public OnValidateMessage( IMessage message){
		this.message = message;
	}
	
	@Override
	public void onClick(View arg0) {
		this.message.validate();
		this.message.save();
	}

}
