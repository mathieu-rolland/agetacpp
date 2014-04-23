package com.istic.agetac.model;

import com.istic.agetac.api.model.IMessage;

public class Message implements IMessage {

	private boolean lock;
	private String uid;
	private String message;
	private boolean validate;
	
	@Override
	public String getUid() {
		return uid;
	}

	@Override
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Override
	public void setText(String text) {
		if( isEditable() ){
			this.message = text;
		}
	}

	@Override
	public String getText() {
		return message;
	}

	@Override
	public boolean isEditable() {
		return !lock;
	}

	@Override
	public void lock() {
		lock = true;
	}

	@Override
	public void unlock() {
		lock = false;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	public boolean isValidate() {
		return validate;
	}

	public void validate() {
		this.validate = true;
	}
	
	public void unvalidate(){
		this.validate = false;
	}

}
