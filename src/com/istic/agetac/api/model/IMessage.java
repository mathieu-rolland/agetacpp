package com.istic.agetac.api.model;

import com.istic.agetac.pattern.observer.Subject;

public interface IMessage extends Subject{

	enum Message_part{
		JE_SUIS, JE_VOIS, JE_PREVOIS, JE_FAIS, JE_DEMANDE
	}
	
	public String getId();
	public void setId(String id);
	public void setText( Message_part part, String text );
	public String getText(Message_part part);
	public boolean isEditable(Message_part part);
	public boolean isValidate();
	public void validate();
	public void unvalidate();
	public void lock();
	public void unlock();
	public boolean isLock();
	public void save();
	public void update();
	public boolean isComplet();
	
}
