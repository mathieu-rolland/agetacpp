package com.istic.agetac.sync.message;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.api.model.IMessage;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Message;
import com.istic.sit.framework.couch.APersitantRecuperator;
import com.istic.sit.framework.couch.JsonSerializer;

public class MessageQuerySynchro extends APersitantRecuperator<Intervention>{

	private List<IMessage> messages;
	private MessageServiceSynchronisation service;
	private Intervention intervention;
	
	public MessageQuerySynchro(Class<Intervention> type, Intervention intervention, MessageServiceSynchronisation service) {
		super(type, "agetacpp", "get_synchro_messages", intervention.getId() );
		messages = new ArrayList<IMessage>();
		this.service = service;
		this.intervention = intervention;
	}

	@Override
	public void onResponse(JSONObject json) {
		
		try {
			JSONArray rows = json.getJSONArray("rows");
			for( int i = 0 ; i < rows.length() ; i++ ){
				JSONObject row = rows.getJSONObject(i);
				JSONObject value = row.getJSONObject("value");
				if( value.has("messages") ){
					parseJsonMessage( value.getJSONArray("messages") );
				}else if( value.has("rev") ){
					intervention.setRev( value.getString("rev") );
				}
			}
			service.notifyResponseSuccess( this.messages);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void parseJsonMessage( JSONArray messages ){
		this.messages = (List<IMessage>) JsonSerializer.deserialize(Message.class, messages);
		Log.d("SYNCHRO","Size : " + this.messages.size());
	}
	
	@Override
	public void onErrorResponse(VolleyError arg0) {}

	@Override
	public void onResponse(List<Intervention> objets) {
	}

}
