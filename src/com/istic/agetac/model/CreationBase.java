package com.istic.agetac.model;

import com.istic.agetac.R;
import com.istic.sit.framework.api.model.IPosition.AXIS;
import com.istic.sit.framework.model.CoordonateGPS;
import com.istic.sit.framework.model.Representation;

public class CreationBase {

	public static void createMoyen(){
		// Moyen m1
		Moyen m1 = new Moyen("VSAV");
		CoordonateGPS pos1 = new CoordonateGPS();
		pos1.set(AXIS.LAT, 48.11943311944856);
		pos1.set(AXIS.LNG, -1.64665337651968);
		m1.setPosition(pos1);
		m1.setLibelle("VSAV1");
		m1.setRepresentationOK(new Representation(R.drawable.vsav));
		m1.setSecteur("S.L.L");
		
		m1.save();
	}
}
