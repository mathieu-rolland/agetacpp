package com.istic.agetac.controllers.dao;

import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.model.Intervention;

public class InterventionDao extends ADao<Intervention> {

	public InterventionDao(IViewReceiver<Intervention> iViewReceiver) {
		super(iViewReceiver);
	}

	public void findAll() {
		super.executeFindAll(Intervention.class);
	}
}
