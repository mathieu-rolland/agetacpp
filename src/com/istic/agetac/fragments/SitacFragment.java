package com.istic.agetac.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;
import com.istic.agetac.R;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controllers.dao.MoyensDao;
import com.istic.agetac.controllers.mapsDock.MapObserver;
import com.istic.agetac.model.Environnement;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.TypeMoyen;
import com.istic.sit.framework.adapter.EntityAdapter;
import com.istic.sit.framework.api.model.IEntity;
import com.istic.sit.framework.api.view.IBackground;
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

		Geocoder coder = new Geocoder(getActivity());
		Log.d("ADDRESSES", AgetacppApplication.getIntervention()
							.getAdresse());
		try {
			ArrayList<Address> adresses = (ArrayList<Address>) coder
					.getFromLocationName(AgetacppApplication.getIntervention()
							.getAdresse(), 1);
			mapFragment.INITPOSITION = new LatLng(adresses.get(0).getLatitude(), adresses.get(0)
					.getLongitude());
		} catch (IOException e) {
			e.printStackTrace();
		}

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

		onClickGridMenuListener = new onClickGridMenuListener();

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

		// centrer sur entity
		if (entity.getPosition() != null && entity.isOnMap())
			((MapFragment) getFragment()).gotoMyLocation(entity.getPosition());
		// TO DO : affichage infos
	}

	@Override
	public void onCreateSlideMenu() {
		IEntity moyen = new Environnement();
		moyen.setLibelle("[+] Moyens");
		moyen.setId("#moyen");
		moyen.setRepresentationOK(new Representation(R.drawable.ic_camion));
		moyen.setRepresentationKO(new Representation(R.drawable.ic_camion));
		IEntity environment = new Environnement();
		environment.setLibelle("[+] Environnement");
		environment.setId("#environment");
		environment
				.setRepresentationOK(new Representation(R.drawable.ic_water));
		environment
				.setRepresentationKO(new Representation(R.drawable.ic_water));

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
			if (!exist
					&& (newMoyen.getHFree() == null || newMoyen.getHFree()
							.isEmpty())) {
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
		Log.d("SYNCHRO MAP", "SitacFragment - updateEntities");
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

	@Override
	protected boolean onActionDropFromMenu(Entity typeEntity, DragEvent event) {
		if (typeEntity.getId().equals("#environment")
				|| typeEntity.getId().equals("#moyen")) {
			// Affichage du gridview de choix de moyens
			// MapFragment mapFragment = (MapFragment) fragment;
			showEntityGridMenu(typeEntity, event.getX(), event.getY());

			clearItemEntityGridMenu();
			if (typeEntity.getId().equals("#environment")) {
				// Danger
				IEntity danger_black = new Environnement();
				danger_black.setLibelle("Cheminements");
				danger_black.setRepresentationOK(new Representation(
						R.drawable.ic_danger_black));
				danger_black.setRepresentationKO(new Representation(
						R.drawable.ic_danger_black));
				IEntity danger_blue = new Environnement();
				danger_blue.setLibelle("Eau");
				danger_blue.setRepresentationOK(new Representation(
						R.drawable.ic_danger_blue));
				danger_blue.setRepresentationKO(new Representation(
						R.drawable.ic_danger_blue));
				IEntity danger_green = new Environnement();
				danger_green.setLibelle("Personnes");
				danger_green.setRepresentationOK(new Representation(
						R.drawable.ic_danger_green));
				danger_green.setRepresentationKO(new Representation(
						R.drawable.ic_danger_green));
				IEntity danger_orange = new Environnement();
				danger_orange.setLibelle("Particuliers");
				danger_orange.setRepresentationOK(new Representation(
						R.drawable.ic_danger_orange));
				danger_orange.setRepresentationKO(new Representation(
						R.drawable.ic_danger_orange));
				IEntity danger_purple = new Environnement();
				danger_purple.setLibelle("Commandements");
				danger_purple.setRepresentationOK(new Representation(
						R.drawable.ic_danger_purple));
				danger_purple.setRepresentationKO(new Representation(
						R.drawable.ic_danger_purple));
				IEntity danger_red = new Environnement();
				danger_red.setLibelle("Eau");
				danger_red.setRepresentationOK(new Representation(
						R.drawable.ic_danger_red));
				danger_red.setRepresentationKO(new Representation(
						R.drawable.ic_danger_red));

				addItemEntityGridMenu(danger_black);
				addItemEntityGridMenu(danger_blue);
				addItemEntityGridMenu(danger_green);
				addItemEntityGridMenu(danger_orange);
				addItemEntityGridMenu(danger_purple);
				addItemEntityGridMenu(danger_red);

				// Risk
				IEntity risk_black = new Environnement();
				risk_black.setLibelle("Cheminements");
				risk_black.setRepresentationOK(new Representation(
						R.drawable.ic_risk_black));
				risk_black.setRepresentationKO(new Representation(
						R.drawable.ic_risk_black));
				IEntity risk_blue = new Environnement();
				risk_blue.setLibelle("Eau");
				risk_blue.setRepresentationOK(new Representation(
						R.drawable.ic_risk_blue));
				risk_blue.setRepresentationKO(new Representation(
						R.drawable.ic_risk_blue));
				IEntity risk_green = new Environnement();
				risk_green.setLibelle("Personnes");
				risk_green.setRepresentationOK(new Representation(
						R.drawable.ic_risk_green));
				risk_green.setRepresentationKO(new Representation(
						R.drawable.ic_risk_green));
				IEntity risk_orange = new Environnement();
				risk_orange.setLibelle("Particuliers");
				risk_orange.setRepresentationOK(new Representation(
						R.drawable.ic_risk_orange));
				risk_orange.setRepresentationKO(new Representation(
						R.drawable.ic_risk_orange));
				IEntity risk_purple = new Environnement();
				risk_purple.setLibelle("Commandements");
				risk_purple.setRepresentationOK(new Representation(
						R.drawable.ic_risk_purple));
				risk_purple.setRepresentationKO(new Representation(
						R.drawable.ic_risk_purple));
				IEntity risk_red = new Environnement();
				risk_red.setLibelle("Eau");
				risk_red.setRepresentationOK(new Representation(
						R.drawable.ic_risk_red));
				risk_red.setRepresentationKO(new Representation(
						R.drawable.ic_risk_red));

				addItemEntityGridMenu(risk_black);
				addItemEntityGridMenu(risk_blue);
				addItemEntityGridMenu(risk_green);
				addItemEntityGridMenu(risk_orange);
				addItemEntityGridMenu(risk_purple);

				// Water
				IEntity water = new Environnement();
				water.setLibelle("Point d'eau");
				water.setRepresentationOK(new Representation(
						R.drawable.ic_water));
				water.setRepresentationKO(new Representation(
						R.drawable.ic_water));
				addItemEntityGridMenu(water);

				gridMenuTitle.setText("Elements d'environnement a placer");
			} else if (typeEntity.getId().equals("#moyen")) {
				Moyen fpt_alim = new Moyen(TypeMoyen.FPT_ALIM);
				fpt_alim.setLibelle("FPT ALIM");

				Moyen fpt_inc = new Moyen(TypeMoyen.FPT_INC);
				fpt_inc.setLibelle("FPT INC");

				Moyen fpt_sap = new Moyen(TypeMoyen.FPT_SAP);
				fpt_sap.setLibelle("FPT SAP");

				Moyen vsav_alim = new Moyen(TypeMoyen.VSAV_ALIM);
				vsav_alim.setLibelle("VSAV ALIM");

				Moyen vsav_inc = new Moyen(TypeMoyen.VSAV_INC);
				vsav_inc.setLibelle("VSAV INC");

				Moyen vsav_sap = new Moyen(TypeMoyen.VSAV_SAP);
				vsav_sap.setLibelle("VSAV SAP");

				addItemEntityGridMenu(fpt_alim);
				addItemEntityGridMenu(fpt_inc);
				addItemEntityGridMenu(fpt_sap);
				addItemEntityGridMenu(vsav_alim);
				addItemEntityGridMenu(vsav_inc);
				addItemEntityGridMenu((Entity) vsav_sap);

				gridMenuTitle.setText("Moyen a engager");
			}
			currentX = event.getX();
			currentY = event.getY();
		} else {
			// Create entity to set on map
			((IBackground) fragment).addEntity(typeEntity, event.getX(),
					event.getY());
		}

		return true;
	}

	public class onClickGridMenuListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// Get entity where user click on
			EntityAdapter entityAdapter = (EntityAdapter) parent.getAdapter();
			IEntity gridEntity = (IEntity) entityAdapter.getItem(position);
			// Create entity to set on map
			/*
			 * Moyen moyen = new Moyen(TypeMoyen.VSAV);
			 * moyen.setRepresentationOK(gridEntity.getRepresentation());
			 * moyen.setRepresentationKO(gridEntity.getRepresentation());
			 * moyen.setLibelle(gridEntity.getLibelle());
			 */

			((IBackground) fragment).addEntity((Entity) gridEntity, currentX,
					currentY);

			hideEntityGridMenu();
		}
	}
}
