package com.istic.agetac.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.sit.framework.api.model.IEntity;
import com.istic.sit.framework.api.model.IPosition.AXIS;
import com.istic.sit.framework.view.AbstractEntityInformationFragment;

public class EntityDockFragment extends AbstractEntityInformationFragment {

	public static final EntityDockFragment newInstance( IEntity entity ){
		EntityDockFragment edf = new EntityDockFragment();
		edf.setEntity(entity);
		return edf;
	}
	
	public EntityDockFragment() {
		super( R.layout.fragment_entity_information, null );
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
		if( entity != null ){
			
			ImageView img = (ImageView) view.findViewById(R.id.fragment_entity_image_display);
			img.setImageResource( entity.getRepresentationOK().getDrawable() );
		
			TextView label = (TextView) view.findViewById(R.id.fragment_entity_label);
			label.setText( entity.getLibelle() );
			
			entity.getPosition().get(AXIS.LAT);
			
		}
		
		return view;
	}
	
}
