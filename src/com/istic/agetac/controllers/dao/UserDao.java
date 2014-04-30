package com.istic.agetac.controllers.dao;

import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.model.User;


public class UserDao extends ADao<User> {

	public UserDao(IViewReceiver<User> iViewReceiver) {
		super(iViewReceiver);
	}

	public void findAll() {
		super.executeFindAll(User.class);
	}
}
