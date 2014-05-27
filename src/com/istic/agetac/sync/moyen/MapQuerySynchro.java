package com.istic.agetac.sync.moyen;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Line;
import com.istic.agetac.model.Moyen;
import com.istic.sit.framework.couch.APersitantRecuperator;
import com.istic.sit.framework.couch.JsonSerializer;
import com.istic.sit.framework.model.Entity;

public class MapQuerySynchro extends APersitantRecuperator<Intervention>{

	private List<Entity> moyens;
	private List<Line> lines;
	private MoyenIntentService service;
	private Intervention intervention;
	
	public MapQuerySynchro(Class<Intervention> type, Intervention intervention, MoyenIntentService service) {
		super(type, "agetacpp", "get_synchro_map", intervention.getId() );
		moyens = new ArrayList<Entity>();
		lines = new ArrayList<Line>();
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
				if( value.has("lines") ){
					parseJsonLines( value.getJSONArray("lines") );
				}else if( value.has("moyens") ){
					parseJsonMoyens( value.getJSONArray("moyens") );
				}else if( value.has("rev") ){
					intervention.setRev( value.getString("rev") );
				}
			}
			service.notifyResponseSuccess( this.moyens, this.lines);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void parseJsonLines( JSONArray lines ){
		this.lines = (List<Line>) JsonSerializer.deserialize(Line.class, lines);
	}
	
	@SuppressWarnings("unchecked")
	private void parseJsonMoyens( JSONArray moyens ){
		this.moyens = (List<Entity>) JsonSerializer.deserialize(Moyen.class, moyens);
	}
	
	@Override
	public void onErrorResponse(VolleyError arg0) {}

	@Override
	public void onResponse(List<Intervention> objets) {
	}

}
