package com.istic.agetac.controllers.mapsDock;


import com.istic.agetac.fragments.EntityDockFragment;
import com.istic.agetac.pattern.observer.Observer;
import com.istic.agetac.pattern.observer.Subject;
import com.istic.sit.framework.view.MapFragment;

public class MapObserver implements Observer {
	
	
	
	@Override
	public void update(Subject subject) {
		MapFragment map = (MapFragment) subject;
		EntityDockFragment edf = EntityDockFragment.newInstance( map );
		map.displayFragement(edf);
	}


}
