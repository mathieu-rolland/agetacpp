package com.istic.agetac.model.serializer;

import java.lang.reflect.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.istic.agetac.api.model.IIntervention;
import com.istic.agetac.api.model.IUser;
import com.istic.agetac.model.Codis;
import com.istic.agetac.model.Intervenant;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.User;
import com.istic.sit.framework.couch.JsonSerializer;



public class AgetacSerializer {
	
	public static void init(){
		JsonSerializer.registerDeserializer(User.class, new UserDeserializer());
	}

	private static class InterventionDeserializer implements JsonDeserializer<IIntervention> {

		@Override
		public Intervention deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			Intervention intervention = gson.fromJson(json, Intervention.class);
			JsonArray jsonUsers = json.getAsJsonObject().get("users").getAsJsonArray();
			for(JsonElement elmt : jsonUsers){
//				intervention.addUser(gson.fromJson(elmt, User.class));
			}
			return intervention;
		}
	}
	
	private static class UserDeserializer implements JsonDeserializer<User> {

		@Override
		public User deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			if(json.getAsJsonObject().has("intervention")){
				return gson.fromJson(json, Intervenant.class);
			}
			else{
				return gson.fromJson(json, Codis.class);
			}
		}
	}
	
	public static JSONObject serializeIntervention(IIntervention intervention, boolean goInDeep) throws JSONException {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		JSONObject json = new JSONObject(gson.toJson(intervention).toString());
		// add users list to the JSON if goInDeep
		if(goInDeep){
			JSONArray users = new JSONArray();
//			for(User user : intervention.getUsers()){
//				users.put(serializeUser(user, false));
//			}
			json.accumulate("users", users);
		}
		// remove _rev in case of creation
		if ( intervention.getRev() != null && intervention.getRev().isEmpty()) {
			json.remove("_rev");
		}
		json.accumulate("type", JsonSerializer.getTypeHierarchy(intervention));
		return json;
	}
	
	public static JSONObject serializeUser(IUser user, boolean goInDeep) throws JSONException {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		JSONObject json = new JSONObject(gson.toJson(user).toString());
		// add users list to the JSON if goInDeep
		if(goInDeep){
			JSONArray interventions = new JSONArray();
//			for(Intervention intervention : user.getInterventions()){
//				interventions.put(serializeIntervention(intervention, false));
//			}
			json.accumulate("interventions", interventions);
		}
		// remove _rev in case of creation
		if ( user.getRev() != null && user.getRev().isEmpty()) {
			json.remove("_rev");
		}
		json.accumulate("type", JsonSerializer.getTypeHierarchy(user));
		return json;
	}
}
