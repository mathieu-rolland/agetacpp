package com.istic.agetac.fragments;

import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.model.Moyen;
import com.istic.sit.framework.api.model.IPosition.AXIS;
import com.istic.sit.framework.api.model.IProperty;
import com.istic.sit.framework.model.Property;
import com.istic.sit.framework.view.AbstractEntityInformationFragment;
import com.istic.sit.framework.view.MapFragment;

public class EntityDockFragment extends AbstractEntityInformationFragment {

	private Button arrived;
	private Button modify;
	
	public static final EntityDockFragment newInstance( MapFragment map ){
		EntityDockFragment edf = new EntityDockFragment();
		edf.setEntity( map.getSelectedEntity() );
		edf.setFragment(map);
		return edf;
	}
	
	private MapFragment fragment;
	
	public EntityDockFragment() {
		super( R.layout.fragment_entity_information, null );
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
		if( entity != null ){
			ImageView img = (ImageView) view.findViewById(R.id.fragment_entity_image_display);
			img.setImageResource( entity.getRepresentation().getDrawable() );
			
			TextView lat = (TextView) view.findViewById(R.id.fragment_activity_position_lat_value);
			lat.setText( String.valueOf(entity.getPosition().get(AXIS.LAT)) );
			
			TextView lng = (TextView) view.findViewById(R.id.fragment_activity_position_lng_value);
			lng.setText( String.valueOf(entity.getPosition().get(AXIS.LNG)) );
			
			modify = (Button) view.findViewById(R.id.fragment_entity_button_supprimer);
			modify.setOnClickListener(new OnDeleteButton(fragment));
			
			arrived = (Button) view.findViewById(R.id.fragment_entity_button_arrived);
			
			List<IProperty> properties = entity.getProprietes();
			boolean dateArrivedIsSet = false;
			boolean dateEngagementIsSet = false;
			for (IProperty iProperty : properties) {
				Log.d("Log",  iProperty.getNom() + " has attribute " + iProperty.getValeur() );
				if( iProperty.getNom().equals( Moyen.NAME_PROPERTY_HOUR_ARRIVAL )
						&& iProperty.getValeur() != null 
						&& !iProperty.getValeur().isEmpty() ){
					dateArrivedIsSet = true;
					break;
				}
				if( iProperty.getNom().equals( Moyen.NAME_PROPERTY_HOUR_ENGAGEMENT) 
						&& iProperty.getValeur() != null 
						&& !iProperty.getValeur().isEmpty()){
					dateEngagementIsSet = true;
				}
			}
			//Masquer le bouton "Arriver" si le moyen est deja arrivé.
			if( dateArrivedIsSet ){
				arrived.setVisibility(View.INVISIBLE);
			}else{
				modify.setVisibility( View.INVISIBLE );
				arrived.setVisibility(View.VISIBLE);
				arrived.setOnClickListener(new OnArrivedButton());
			}
			
			if( !dateEngagementIsSet ){
				arrived.setVisibility(View.INVISIBLE);
				modify.setVisibility(View.INVISIBLE);
			}
			
		}
		return view;
	}
	
	public MapFragment getFragment() {
		return fragment;
	}

	public void setFragment(MapFragment fragment) {
		this.fragment = fragment;
	}

	private class OnDeleteButton implements OnClickListener{

		private MapFragment map;
		
		public OnDeleteButton( MapFragment  map ){
			this.map = map;
		}
		
		@Override
		public void onClick(View arg0) {
			String alert = getResources().getString(R.string.fragment_entity_alert_libererRessource);
			alert = alert.replace("%s", entity.getLibelle());
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage(alert)
	               .setPositiveButton(R.string.fragment_messages_list_alert_modification_yes,
	            		   new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   Property property = new Property();
	                	   property.setNom(Moyen.NAME_PROPERTY_HOUR_FREE);
	                	   property.setValeur( Moyen.FORMATER.format(new Date()) );
	                	   entity.addPropriete( property );
	                	   map.removeEntity(entity);
	                	   entity.save();
	                   }
	               })
	               .setNegativeButton(R.string.fragment_messages_list_alert_modification_no, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                   }
	               });
	        builder.create();
	        builder.show();
		}
		
	}
	
	private class OnArrivedButton implements OnClickListener{
		
		@Override
		public void onClick(View arg0) {
        	   Property property = new Property();
        	   property.setNom(Moyen.NAME_PROPERTY_HOUR_ARRIVAL);
        	   property.setValeur( Moyen.FORMATER.format(new Date()) );
        	   entity.addPropriete( property );
        	   Property propertySector = new Property();
        	   propertySector.setNom(Moyen.NAME_PROPERTY_SECTEUR);
        	   propertySector.setValeur("SLL");
        	   entity.addPropriete( propertySector );
        	   entity.setOk(true);
        	   entity.save();
        	   arrived.setVisibility(View.INVISIBLE);
        	   modify.setVisibility(View.VISIBLE);
		}
		
	}
	
	
}
