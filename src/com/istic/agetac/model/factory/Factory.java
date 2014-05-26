package com.istic.agetac.model.factory;

import com.istic.agetac.model.Line;
import com.istic.sit.framework.factory.AFactory;
import com.istic.sit.framework.model.ALine;

public class Factory extends AFactory{

	@Override
	public ALine createLine() {
		return new Line();
	}

}
