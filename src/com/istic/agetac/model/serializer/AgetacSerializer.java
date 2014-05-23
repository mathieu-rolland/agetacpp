package com.istic.agetac.model.serializer;

import java.lang.reflect.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.istic.agetac.api.model.IIntervention;
import com.istic.agetac.api.model.IMessage;
import com.istic.agetac.model.Codis;
import com.istic.agetac.model.Intervenant;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Message;
import com.istic.agetac.model.User;
import com.istic.sit.framework.couch.JsonSerializer;



public class AgetacSerializer {
	
	public static void init(){
		JsonSerializer.registerDeserializer(User.class, new UserDeserializer());
		JsonSerializer.registerDeserializer(IMessage.class, new IMessageDeserializer());
		JsonSerializer.registerDeserializer(IIntervention.class, new IInterventionDeserializer());
	}
	
	private static class IInterventionDeserializer implements JsonDeserializer<IIntervention> {

		@Override
		public IIntervention deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
			Gson gson = JsonSerializer.getBuilder().create();
			return gson.fromJson(json, Intervention.class);
		}
		
	}
	
	private static class IMessageDeserializer implements JsonDeserializer<IMessage> {

		@Override
		public IMessage deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
			Gson gson = JsonSerializer.getBuilder().create();
			return gson.fromJson(json, Message.class);
		}
		
	}
	
	private static class UserDeserializer implements JsonDeserializer<User> {

		@Override
		public User deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
			Gson gson = JsonSerializer.getBuilder().create();
			if(json.getAsJsonObject().get("role").getAsString().equals("codis")){
				return gson.fromJson(json, Codis.class);
			}
			else{
				return gson.fromJson(json, Intervenant.class);
			}
		}
	}
	
	public static JSONObject serializeIntervention(IIntervention intervention, boolean goInDeep) throws JSONException {
		Gson gson = JsonSerializer.getBuilder().create();
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
	
}
