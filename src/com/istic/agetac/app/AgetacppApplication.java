package com.istic.agetac.app;

import com.istic.agetac.api.model.IUser;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.model.Intervenant;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.serializer.AgetacSerializer;
import com.istic.sit.framework.application.FrameworkApplication;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.JsonSerializer;

public class AgetacppApplication extends FrameworkApplication {

	private static IUser user;
	private static Intervention currentInterventionCodis;
	
	@Override
	public void onCreate() {
		super.onCreate();
		JsonSerializer.initSerializer();
		JsonSerializer.initDeserializer();
		AgetacSerializer.init();
		DataBaseCommunication.BASE_URL = "http://148.60.11.236:5984/marion/";
		//CreationBase.createCleanBase();
	}

	/**
	 * @return the user
	 */
	public static IUser getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public static void setUser(IUser user) {
		AgetacppApplication.user = user;
	}

	public static Intervention getIntervention() {
		if( user == null ) return null;
		if( user.getRole() == Role.intervenant ){
			return ((Intervenant)user).getIntervention();
		}else{
			return currentInterventionCodis;
		}
	}

	public static void setCurrentInterventionCodis(
			Intervention currentInterventionCodis) {
		AgetacppApplication.currentInterventionCodis = currentInterventionCodis;
	}
	
}
