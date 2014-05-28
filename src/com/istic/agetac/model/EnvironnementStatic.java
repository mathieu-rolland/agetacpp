package com.istic.agetac.model;

import com.istic.agetac.app.AgetacppApplication;
import com.istic.sit.framework.model.Entity;

public class EnvironnementStatic extends Entity {

	public EnvironnementStatic() {
	}

	@Override
	public void delete() {
		AgetacppApplication.getEnvironnementsStatic().getListEnvironnement()
				.remove(this);
		AgetacppApplication.getEnvironnementsStatic().save();
	}

	@Override
	public void save() {
		if (!AgetacppApplication.getEnvironnementsStatic()
				.getListEnvironnement().contains(this))
			AgetacppApplication.getEnvironnementsStatic()
					.getListEnvironnement().add(this);
		AgetacppApplication.getEnvironnementsStatic().save();
	}

}
