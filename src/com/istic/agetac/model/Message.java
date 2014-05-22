package com.istic.agetac.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.istic.agetac.api.model.IIntervention;
import com.istic.agetac.api.model.IMessage;
import com.istic.agetac.pattern.observer.Observer;
import com.istic.sit.framework.couch.JsonSerializer;

public class Message implements IMessage, Parcelable {

	private String _id;
	private transient boolean lock;
	private boolean validate;
	private Date dateEmission;
	private HashMap<Message_part, String> messages;
	private transient Intervention intervention;
	private transient List<Observer> observers;
	
	public Message()
	{
		messages = new HashMap<IMessage.Message_part, String>();
		observers = new ArrayList<Observer>();
		validate = false;
		lock = false;
		_id = "";
	}
	
	public Message( Intervention intervention )
	{
		messages = new HashMap<IMessage.Message_part, String>();
		observers = new ArrayList<Observer>();
		this.intervention = intervention;
		validate = false;
		lock = false;
		_id = "";
	}
	
	public Message(Parcel source) {
		String serializedJson = source.readString();
		observers = new ArrayList<Observer>();
		try {
			Message message = (Message) JsonSerializer.deserialize(Message.class, new JSONObject(serializedJson));
			_id = "";
			this.setId( message.getId() );
			this.lock = message.lock;
			this.messages = message.messages;
			this.validate = message.validate;
			this.intervention = message.intervention;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setText(Message_part part, String text) {
		if( isEditable(part) ){
			this.messages.put(part, text);
		}
	}

	@Override
	public String getText(Message_part part) {
		return messages.get(part);
	}

	@Override
	public boolean isEditable(Message_part part) {
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
		intervention.save();
	}
	
	@Override
	public void delete() {
		intervention.getMessages().remove(this);
		this.save();
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

	@Override
	public boolean isComplet() {
		return  (messages.get(Message_part.JE_SUIS) != null && !messages.get(Message_part.JE_SUIS).isEmpty())
				&& (messages.get(Message_part.JE_VOIS) != null && !messages.get(Message_part.JE_VOIS).isEmpty())
				&& (messages.get(Message_part.JE_PREVOIS) != null&& !messages.get(Message_part.JE_PREVOIS).isEmpty() )
				&& (messages.get(Message_part.JE_FAIS) != null && !messages.get(Message_part.JE_FAIS).isEmpty())
				&& (messages.get(Message_part.JE_DEMANDE) != null && !messages.get(Message_part.JE_DEMANDE).isEmpty());
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public void setId(String id) {
		this._id = id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String message = gson.toJson(this).toString();
		dest.writeString(message);
	}

	public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>()
	{
		@Override
		public Message createFromParcel(Parcel source)
		{
			return new Message(source);
		}

		@Override
		public Message[] newArray(int size)
		{
			return new Message[size];
		}
	};

	@Override
	public boolean isLock() {
		return lock;
	}

	@Override
	public void registerObserver(Observer observer) {
		this.observers.add(observer);
	}

	@Override
	public void notifyObserver(Observer observer) {
		observer.update(this);
	}

	@Override
	public void notifyObservers() {
		for( Observer obs : observers ){
			obs.update(this);
		}
	}

	@Override
	public void unregisterObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void setDateEmission(Date date) {
		this.dateEmission = date;
	}

	@Override
	public Date getDateEmission() {
		return dateEmission;
	}

	@Override
	public IIntervention getIntervention() {
		return intervention;
	}

	@Override
	public void setIntervention(IIntervention intervention) {
		this.intervention = (Intervention)intervention;
	}

	
}
