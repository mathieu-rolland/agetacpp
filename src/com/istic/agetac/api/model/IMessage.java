package com.istic.agetac.api.model;

public interface IMessage {

	enum Message_part{
		JE_SUIS, JE_VOIS, JE_PREVOIS, JE_FAIS, JE_DEMANDE
	}
	
	public String getUid();
	public void setUid(String uid);
	public void setText( Message_part part, String text );
	public String getText(Message_part part);
	public boolean isEditable(Message_part part);
	public boolean isValidate();
	public void validate();
	public void unvalidate();
	public void lock();
	public void unlock();
	public void save();
	public void update();
	public boolean isComplet();
	
}
