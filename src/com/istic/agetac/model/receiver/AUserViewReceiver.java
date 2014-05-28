package com.istic.agetac.model.receiver;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.VolleyError;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.User;
import com.istic.sit.framework.couch.APersitantRecuperator;
import com.istic.sit.framework.couch.JsonSerializer;

public abstract class AUserViewReceiver extends APersitantRecuperator<Intervention> {

	protected String username;
	protected String password;

	public AUserViewReceiver(String username, String password) {
		super(Intervention.class, "agetacpp", "connexion", username + "|" + password);
		this.username = username;
		this.password = password;
	}

	@Override
	public void onResponse(JSONObject json) {
		AgetacppApplication.setIntervention(null);
		AgetacppApplication.setListIntervention(null);
		AgetacppApplication.setRole(null);
		AgetacppApplication.setUser(null);
		try {
			JSONArray row = (JSONArray) json.get("rows");
			List<Intervention> interventions = new ArrayList<Intervention>();
			for (int i = 0; i < row.length(); i++) {
				Log.d("d", row.toString());
				JSONObject o = row.getJSONObject(i);
				o = o.getJSONObject("value");
				String role = o.getString("role");
				if (role.equals("codis")) {
					AgetacppApplication.setRole(Role.codis);
				} else {
					AgetacppApplication.setRole(Role.intervenant);
				}
				if ((Boolean) o.get("ok")) {
					// User ayant une/des interventions
					JSONObject value = o.getJSONObject("res");
					Intervention interv = (Intervention) JsonSerializer.deserialize(Intervention.class, value);
					interventions.add(interv);
				} else {
					// User sans intervention
					JSONObject value = o.getJSONObject("res");
					User u = (User) JsonSerializer.deserialize(User.class, value);
					AgetacppApplication.setUser(u);
					if (u.getRole() == Role.codis) {
						AgetacppApplication.setListIntervention(new ArrayList<Intervention>());
					}
				}
			}
			onResponse(interventions);
		} catch (JSONException e) {
			Log.e("LoginActivity", e.toString());
		}
	}
}
