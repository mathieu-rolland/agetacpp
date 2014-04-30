package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.controllers.dao.MoyensDao;
import com.istic.agetac.controllers.mapsDock.MapObserver;
import com.istic.agetac.model.Moyen;
import com.istic.sit.framework.api.model.IEntity;
import com.istic.sit.framework.model.Entity;
import com.istic.sit.framework.model.Representation;
import com.istic.sit.framework.view.MainFragment;
import com.istic.sit.framework.view.MapFragment;

public class SitacFragment extends MainFragment {

	public static SitacFragment newInstance() {
		SitacFragment fragment = new SitacFragment();
		return fragment;
	}

	List<Moyen> listMoyens;
	MoyensDao menuMoyenUpdate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// DataBaseCommunication.BASE_URL = "http://148.60.11.236:5984/sitac/";
		initializeBackground(TypeBackgroundEnum.Map, savedInstanceState);

		listMoyens = new ArrayList<Moyen>();
		MapFragment mapFragment = (MapFragment) super.getFragment();
		mapFragment.registerObserver(new MapObserver());

		menuMoyenUpdate = new MoyensDao(new IViewReceiver<Moyen>() {

			@Override
			public void notifyResponseSuccess(List<Moyen> objects) {
				listMoyens = objects;
				menuUpdate(objects);
			}

			@Override
			public void notifyResponseFail(VolleyError error) {
				// TODO Auto-generated method stub

			}
		});

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		loadEntities();
		startServiceSynchronisation();
	}

	@Override
	public void onItemMenuClicked(int position, View view) {
		IEntity entity = (IEntity) this.entityAdapter.getItem(position);

		Toast.makeText(getActivity().getBaseContext(), "Click on Item",
				Toast.LENGTH_SHORT).show();

		//centrer sur entity
		((MapFragment) getFragment()).gotoMyLocation(entity.getPosition());
		// TO DO : affichage infos
	}

	@Override
	public void onCreateSlideMenu() {
		IEntity moyen = new Entity();
		moyen.setLibelle("[+] Moyens");
		moyen.setId("#moyen");
		moyen.setRepresentationOK(new Representation(R.drawable.ic_camion));
		moyen.setRepresentationKO(new Representation(R.drawable.ic_camion));
		IEntity environment = new Entity();
		environment.setLibelle("[+] Environnement");
		environment.setId("#environment");
		environment.setRepresentationOK(new Representation(R.drawable.ic_water));
		environment.setRepresentationKO(new Representation(R.drawable.ic_water));

		Log.d("TOTO", "onCreateSlideMenu");

		this.addItemMenuDefault(moyen);
		this.addItemMenuDefault(environment);

		new MoyensDao(new IViewReceiver<Moyen>() {

			@Override
			public void notifyResponseSuccess(List<Moyen> objects) {
				listMoyens = objects;
				for (IEntity moyenToadd : objects) {
					addItemMenu(moyenToadd);
				}
			}

			@Override
			public void notifyResponseFail(VolleyError error) {
				// TODO Auto-generated method stub

			}
		}).findAll();
	}

	public void menuUpdate(List<Moyen> moyens) {
		boolean exist;
		for (Moyen newMoyen : moyens) {
			exist = false;
			for (int i = 0; i < itemsMenu.size(); i++) {
				Entity oldentity = (Entity) itemsMenu.get(i);
				if (newMoyen.getId().equals(oldentity.getId())) {
					exist = true;
					// Mise a jour boolean
					oldentity.setOk(newMoyen.isOk());
					oldentity.setOnMap(newMoyen.isOnMap());

					entityAdapter.notifyDataSetChanged();
				}
			}
			if (!exist) {
				addItemMenu(newMoyen);
			}
		}
	}

	@Override
	public void onItemMenuLongClicked(int position, View view) {
		IEntity entity = (IEntity) this.entityAdapter.getItem(position);

		if (!entity.isOnMap()) {
			DragShadowBuilder entityShadow = new DragShadowBuilder(view);

			view.startDrag(null, // ClipData
					entityShadow, // View.DragShadowBuilder
					entity, // Object myLocalState
					0);
		}
	}

	@Override
	public void updateEntities(List<Entity> synchronizedEntities) {
		if (synchronizedEntities != null)
			((MapFragment) getFragment()).updateEntities(synchronizedEntities);

		menuMoyenUpdate.findAll();
	}

	private void stopSynchronisation() {
		AlarmManager alarm = (AlarmManager) getActivity().getSystemService(
				Context.ALARM_SERVICE);
		PendingIntent pi = ser.getPendingIntent();
		alarm.cancel(pi);
	}

	@Override
	public void onStop() {
		stopSynchronisation();
		super.onStop();
	}

	// @Override
	// public void onCreateMapMenu(GridView menu) {
	// IEntity environment_water = new Entity();
	// environment_water.setLibelle("Point d'eau");
	// environment_water.setRepresentation(new
	// Representation(R.drawable.environment_water));
	//
	// this.addItemEntityGridMenu(environment_water);
	// this.addItemEntityGridMenu(environment_water);
	//
	// EnvironmentButton environment_water = new
	// EnvironmentButton("Point d'eau", view.getContext(),
	// R.drawable.environment_water);
	//
	//
	// }

	// @Override
	// public void onInitializeMapMenu(GridView menu) {
	// // TODO Auto-generated method stub
	//
	// }

}
