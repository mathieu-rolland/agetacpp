package com.istic.agetac.controllers.dao;

import com.istic.agetac.model.Intervention;
import com.istic.sit.framework.couch.APersitantRecuperator;

public abstract class ConnexionRecuperator extends APersitantRecuperator<Intervention> {

	public ConnexionRecuperator(String username, String password) {
		super(Intervention.class, "agetacpp", "connexion", username+"|"+password);
	}

}
