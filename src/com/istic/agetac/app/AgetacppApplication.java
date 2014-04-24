package com.istic.agetac.app;

import com.istic.agetac.api.model.IUser;
import com.istic.sit.framework.application.FrameworkApplication;

public class AgetacppApplication extends FrameworkApplication {

	private static IUser user;
	
	@Override
	public void onCreate() {
		super.onCreate();
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
	
}
