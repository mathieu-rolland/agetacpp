package com.istic.agetac.model;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.api.model.IMessage;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.IPersistant;
import com.istic.sit.framework.couch.JsonSerializer;

public class Message implements IMessage, IPersistant, Parcelable {

	private String _id;
	private String _rev;
	private boolean lock;
	private String uid;
	private boolean validate;
	private HashMap<Message_part, String> messages;
	
	public Message()
	{
		messages = new HashMap<IMessage.Message_part, String>();
		validate = false;
		lock = false;
		_id = "";
		_rev = "";
	}
	
	public Message(Parcel source) {
		String serializedJson = source.readString();
		try {
			Message message = (Message) JsonSerializer.deserialize(Message.class, new JSONObject(serializedJson));
			this.setId( message.getId() );
			this.setRev(message.getRev());
			this.lock = message.lock;
			this.messages = message.messages;
			this.validate = message.validate;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getUid() {
		return uid;
	}

	@Override
	public void setUid(String uid) {
		this.uid = uid;
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
		DataBaseCommunication.sendPost(this);
	}

	@Override
	public void update() {
		DataBaseCommunication.sendPut(this);
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
		return  !(messages.get(Message_part.JE_SUIS) == null ? true : messages.get(Message_part.JE_SUIS).isEmpty())
				&& !(messages.get(Message_part.JE_VOIS) == null ? true : messages.get(Message_part.JE_VOIS).isEmpty())
				&& !(messages.get(Message_part.JE_PREVOIS) == null ? true : messages.get(Message_part.JE_PREVOIS).isEmpty() )
				&& !(messages.get(Message_part.JE_FAIS) == null ? true : messages.get(Message_part.JE_FAIS).isEmpty())
				&& !(messages.get(Message_part.JE_DEMANDE) == null ? true : messages.get(Message_part.JE_DEMANDE).isEmpty());
	}

	@Override
	public void onResponse(JSONObject arg0) {
		Message msg = (Message) JsonSerializer.deserialize(getClass(), arg0);
		this.messages = msg.messages;
		this.lock = msg.lock;
		this.uid = msg.uid;
		this.validate = msg.validate;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Log.e("Agetac++","[Failed : "+ 
				(error.networkResponse == null ? "Unknown error" : 
				"Error HTTP("+error.networkResponse.statusCode +")" ) 
		+" ]");
	}

	@Override
	public String getUrl(int method) {
		return DataBaseCommunication.BASE_URL + _id;
	}

	@Override
	public JSONObject getData() {
		try {
			return JsonSerializer.serialize(this);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete() {
		DataBaseCommunication.sendDelete(this);
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
	public String getRev() {
		return _rev;
	}

	@Override
	public void setRev(String rev) {
		this._rev = rev;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		try {
			String message = JsonSerializer.serialize(this).toString();
			dest.writeString(message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
}
