package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.controllers.dao.MoyensDao;
import com.istic.agetac.model.Moyen;
import com.istic.sit.framework.api.model.IEntity;
import com.istic.sit.framework.model.Entity;
import com.istic.sit.framework.model.Representation;
import com.istic.sit.framework.view.MainFragment;

public class SitacFragment extends MainFragment {

	public static SitacFragment newInstance() {
		SitacFragment fragment = new SitacFragment();
		return fragment;
	}

	List<Moyen> listMoyens;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//DataBaseCommunication.BASE_URL = "http://148.60.11.236:5984/sitac/";
		initializeBackground(TypeBackgroundEnum.Map, savedInstanceState);
		
		listMoyens = new ArrayList<Moyen>();
		
		return super.onCreateView(inflater,container, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("TOTO","onResume");
		loadEntities();
		startServiceSynchronisation();
	}

	@Override
	public void onItemMenuClicked(int position, View view) {

	}

	@Override
	public void onCreateSlideMenu() {
		IEntity moyen = new Entity();
		moyen.setLibelle("Moyens");
		moyen.setId("#moyen");
		moyen.setRepresentationOK(new Representation(R.drawable.camion));
		
		IEntity environment = new Entity();
		environment.setLibelle("Environnement");
		environment.setId("#environment");
		environment.setRepresentationOK(new Representation(R.drawable.environment_water));

		Log.d("TOTO","onCreateSlideMenu");
		IEntity entityDynamic = new Entity();
		entityDynamic.setLibelle("Camion");
		entityDynamic
				.setRepresentationOK(new Representation(R.drawable.ic_launcher));

		IEntity entityVirtuel = new Entity();
		entityVirtuel.setLibelle("Bouche incendie");
		entityVirtuel.setRepresentationOK(new Representation(
				R.drawable.ic_launcher));

		IEntity entityStatic = new Entity();
		entityStatic.setLibelle("Agetac power");
		entityStatic.setRepresentationOK(new Representation(
				R.drawable.ic_launcher));

		this.addItemMenu(moyen);
		this.addItemMenu(environment);
		this.addItemMenu(entityDynamic);
		this.addItemMenu(entityVirtuel);
		this.addItemMenu(entityStatic);
		
		
		new MoyensDao(new IViewReceiver<Moyen>() {
			
			@Override
			public void notifyResponseSuccess(List<Moyen> objects) {
				listMoyens = objects;
			}
			
			@Override
			public void notifyResponseFail(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		}).findAll();
		
		if (listMoyens != null){
			for (IEntity moyenToadd : listMoyens){
				this.addItemMenu(moyenToadd);
			}
		}
	}

	@Override
	public void onItemMenuLongClicked(int position, View view) {
		IEntity entity = (IEntity) this.entityAdapter.getItem(position);

		DragShadowBuilder entityShadow = new DragShadowBuilder(view);

		view.startDrag(null, // ClipData
				entityShadow, // View.DragShadowBuilder
				entity, // Object myLocalState
				0);
//		this.mDrawerLayout.closeDrawer(listMenu);
	}

//	@Override
//	public void onCreateMapMenu(GridView menu) {
//		IEntity environment_water = new Entity();
//		environment_water.setLibelle("Point d'eau");
//		environment_water.setRepresentation(new Representation(R.drawable.environment_water));
//		
//		this.addItemEntityGridMenu(environment_water);
//		this.addItemEntityGridMenu(environment_water);
//		
//		EnvironmentButton environment_water = new EnvironmentButton("Point d'eau", view.getContext(), R.drawable.environment_water);
//		
//		
//	}

//	@Override
//	public void onInitializeMapMenu(GridView menu) {
//		// TODO Auto-generated method stub
//		
//	}

}
