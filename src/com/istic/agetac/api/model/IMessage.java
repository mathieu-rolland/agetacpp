package com.istic.agetac.api.model;

import java.util.Date;

import com.istic.agetac.pattern.observer.Subject;
import com.istic.sit.framework.couch.IRecordable;

public interface IMessage extends IRecordable, Subject {

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
	public boolean isComplet();
	public void setDateEmission( Date date );
	public Date getDateEmission();
	public IIntervention getIntervention();
	public void setIntervention(IIntervention intervention);
	
}
