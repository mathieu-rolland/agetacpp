package com.istic.agetac.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.istic.agetac.R;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controllers.dao.MoyensDao;
import com.istic.agetac.controllers.mapsDock.MapObserver;
import com.istic.agetac.model.Environnement;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.TypeMoyen;
import com.istic.sit.framework.adapter.ExpandableListAdapter;
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

	List<IMoyen> listMoyens;
	MoyensDao menuMoyenUpdate;

	private Intervention intervention;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		this.intervention = AgetacppApplication.getIntervention();
		// DataBaseCommunication.BASE_URL = "http://148.60.11.236:5984/sitac/";
		initializeBackground(TypeBackgroundEnum.Map, savedInstanceState);

		listMoyens = intervention.getMoyens();
		MapFragment mapFragment = (MapFragment) super.getFragment();
		mapFragment.registerObserver(new MapObserver());

		Geocoder coder = new Geocoder(getActivity());
		Log.d("ADDRESSES", intervention.getAdresse());
		try {
			ArrayList<Address> adresses = (ArrayList<Address>) coder
					.getFromLocationName(intervention.getAdresse(), 1);
			MapFragment.INITPOSITION = new LatLng(adresses.get(0).getLatitude(), adresses.get(0)
					.getLongitude());
		} catch (IOException e) {
			e.printStackTrace();
		}

		menuUpdate(listMoyens);

		onClickExpandableListItemListener = new onClickExpandableListItemListener();

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
		IEntity moyen = new Environnement( intervention );
		moyen.setLibelle("[+] Moyens");
		moyen.setId("#moyen");
		moyen.setRepresentationOK(new Representation(R.drawable.ic_camion));
		moyen.setRepresentationKO(new Representation(R.drawable.ic_camion));

		IEntity environmentDynamique = new Environnement( intervention );
		environmentDynamique.setLibelle("[+] Env. dynamique");
		environmentDynamique.setId("#environmentDynamique");
		environmentDynamique.setRepresentationOK(new Representation(R.drawable.ic_water));
		environmentDynamique.setRepresentationKO(new Representation(R.drawable.ic_water));
		
		IEntity environmentStatique = new Environnement( intervention );
		environmentStatique.setLibelle("[+] Env. statique");
		environmentStatique.setId("#environmentStatique");
		environmentStatique.setRepresentationOK(new Representation(R.drawable.ic_water3));
		environmentStatique.setRepresentationKO(new Representation(R.drawable.ic_water3));

		this.addItemMenuDefault(moyen);
		this.addItemMenuDefault(environmentDynamique);
		this.addItemMenuDefault(environmentStatique);

		Intervention intervention = AgetacppApplication.getIntervention();
		listMoyens = intervention.getMoyens();
		
		for (IMoyen moyenToadd : listMoyens) {
			addItemMenu(((Moyen)moyenToadd));
		}
		
//		new MoyensDao(new IViewReceiver<Moyen>() {
//
//			@Override
//			public void notifyResponseSuccess(List<Moyen> objects) {
//				listMoyens = objects;
//				for (IEntity moyenToadd : objects) {
//					addItemMenu(moyenToadd);
//				}
//			}
//
//			@Override
//			public void notifyResponseFail(VolleyError error) {
//				// TODO Auto-generated method stub
//
//			}
//		}).findAll();
	}

	public void menuUpdate(List<IMoyen> moyens) {
		boolean exist;
		for (IMoyen newMoyen : moyens) {
			exist = false;
			for (int i = 0; i < itemsMenu.size(); i++) {
				Entity oldentity = (Entity) itemsMenu.get(i);
				if (((Moyen) newMoyen).getId().equals(oldentity.getId())) {
					exist = true;
					// Mise a jour boolean
					oldentity.setOk(((Moyen)newMoyen).isOk());
					oldentity.setOnMap(((Moyen)newMoyen).isOnMap());

					entityAdapter.notifyDataSetChanged();
				}
			}
			if (!exist
					&& (newMoyen.getHFree() == null)) {
				addItemMenu((Moyen)newMoyen);
			}
		}
	}

	@Override
	public void onItemMenuLongClicked(int position, View view) {
		IEntity entity = (IEntity) this.entityAdapter.getItem(position);

		if (!entity.isOnMap()) {
			DragShadowBuilder entityShadow = new DragShadowBuilder(view);

			view.startDrag(null,  // ClipData
					entityShadow, // View.DragShadowBuilder
					entity,       // Object myLocalState
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
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<IEntity>>();

		if(typeEntity.getId().equals("#environmentDynamique") || typeEntity.getId().equals("#environmentStatique") || typeEntity.getId().equals("#moyen")){
			// Affichage du gridview de choix de moyens
			//			MapFragment mapFragment = (MapFragment) fragment;
			showEntityGridMenu(typeEntity, event.getX(), event.getY());

			clearExpMenu();
			if(typeEntity.getId().equals("#environmentDynamique")){
				//Danger
				listDataHeader.add("Sources de danger");
				List<IEntity> danger = new ArrayList<IEntity>();

				IEntity danger_blue = new Environnement(intervention);
				danger_blue.setLibelle("Ayant trait à l'eau");
				danger_blue.setRepresentationOK(new Representation(R.drawable.ic_danger_blue));
				danger_blue.setRepresentationKO(new Representation(R.drawable.ic_danger_blue));
				IEntity danger_green = new Environnement(intervention);
				danger_green.setLibelle("Personnes");
				danger_green.setRepresentationOK(new Representation(R.drawable.ic_danger_green));
				danger_green.setRepresentationKO(new Representation(R.drawable.ic_danger_green));
				IEntity danger_orange = new Environnement(intervention);
				danger_orange.setLibelle("Particuliers");
				danger_orange.setRepresentationOK(new Representation(R.drawable.ic_danger_orange));
				danger_orange.setRepresentationKO(new Representation(R.drawable.ic_danger_orange));
				IEntity danger_red = new Environnement(intervention);
				danger_red.setLibelle("Incendie");
				danger_red.setRepresentationOK(new Representation(R.drawable.ic_danger_red));
				danger_red.setRepresentationKO(new Representation(R.drawable.ic_danger_red));

				danger.add(danger_blue);
				danger.add(danger_green);
				danger.add(danger_orange);
				danger.add(danger_red);
				listDataChild.put(listDataHeader.get(0), danger);

				//Risk
				listDataHeader.add("Points sensibles");
				List<IEntity> risk = new ArrayList<IEntity>();

				IEntity risk_blue = new Environnement(intervention);
				risk_blue.setLibelle("Ayant trait à l'eau");
				risk_blue.setRepresentationOK(new Representation(R.drawable.ic_risk_blue));
				risk_blue.setRepresentationKO(new Representation(R.drawable.ic_risk_blue));
				IEntity risk_green = new Environnement(intervention);
				risk_green.setLibelle("Personnes");
				risk_green.setRepresentationOK(new Representation(R.drawable.ic_risk_green));
				risk_green.setRepresentationKO(new Representation(R.drawable.ic_risk_green));
				IEntity risk_orange = new Environnement(intervention);
				risk_orange.setLibelle("Particuliers");
				risk_orange.setRepresentationOK(new Representation(R.drawable.ic_risk_orange));
				risk_orange.setRepresentationKO(new Representation(R.drawable.ic_risk_orange));
				IEntity risk_red = new Environnement(intervention);
				risk_red.setLibelle("Incendie");
				risk_red.setRepresentationOK(new Representation(R.drawable.ic_risk_red));
				risk_red.setRepresentationKO(new Representation(R.drawable.ic_risk_red));

				risk.add(risk_blue);
				risk.add(risk_green);
				risk.add(risk_orange);
				risk.add(risk_red);
				listDataChild.put(listDataHeader.get(1), risk);

				//Water
				listDataHeader.add("Prise d'eau");
				List<IEntity> eau = new ArrayList<IEntity>();
				IEntity water = new Environnement(intervention);
				water.setLibelle("Point d'eau pèrenne");
				water.setRepresentationOK(new Representation(R.drawable.ic_water));
				water.setRepresentationKO(new Representation(R.drawable.ic_water));
				IEntity water2 = new Environnement(intervention);
				water2.setLibelle("Point d'eau non pèrenne");
				water2.setRepresentationOK(new Representation(R.drawable.ic_water2));
				water2.setRepresentationKO(new Representation(R.drawable.ic_water2));
				IEntity water3 = new Environnement(intervention);
				water3.setLibelle("Point de ravitaillement");
				water3.setRepresentationOK(new Representation(R.drawable.ic_water3));
				water3.setRepresentationKO(new Representation(R.drawable.ic_water3));
				eau.add(water);
				eau.add(water2);
				eau.add(water3);
				listDataChild.put(listDataHeader.get(2), eau);

				expMenuTitle.setText("Eléments d'environnement à placer");
			}
			else if(typeEntity.getId().equals("#environmentStatique")){
				//Risk
				listDataHeader.add("Points sensibles");
				List<IEntity> risk = new ArrayList<IEntity>();

				IEntity risk_blue = new Environnement(intervention);
				risk_blue.setLibelle("Ayant trait à l'eau");
				risk_blue.setRepresentationOK(new Representation(R.drawable.ic_risk_blue));
				risk_blue.setRepresentationKO(new Representation(R.drawable.ic_risk_blue));
				IEntity risk_green = new Environnement(intervention);
				risk_green.setLibelle("Personnes");
				risk_green.setRepresentationOK(new Representation(R.drawable.ic_risk_green));
				risk_green.setRepresentationKO(new Representation(R.drawable.ic_risk_green));
				IEntity risk_orange = new Environnement(intervention);
				risk_orange.setLibelle("Particuliers");
				risk_orange.setRepresentationOK(new Representation(R.drawable.ic_risk_orange));
				risk_orange.setRepresentationKO(new Representation(R.drawable.ic_risk_orange));
				IEntity risk_red = new Environnement(intervention);
				risk_red.setLibelle("Incendie");
				risk_red.setRepresentationOK(new Representation(R.drawable.ic_risk_red));
				risk_red.setRepresentationKO(new Representation(R.drawable.ic_risk_red));

				risk.add(risk_blue);
				risk.add(risk_green);
				risk.add(risk_orange);
				risk.add(risk_red);
				listDataChild.put(listDataHeader.get(0), risk);

				//Water
				listDataHeader.add("Prise d'eau");
				List<IEntity> eau = new ArrayList<IEntity>();
				IEntity water = new Environnement(intervention);
				water.setLibelle("Point d'eau pèrenne");
				water.setRepresentationOK(new Representation(R.drawable.ic_water));
				water.setRepresentationKO(new Representation(R.drawable.ic_water));
				IEntity water2 = new Environnement(intervention);
				water2.setLibelle("Point d'eau non pèrenne");
				water2.setRepresentationOK(new Representation(R.drawable.ic_water2));
				water2.setRepresentationKO(new Representation(R.drawable.ic_water2));
				IEntity water3 = new Environnement(intervention);
				water3.setLibelle("Point de ravitaillement");
				water3.setRepresentationOK(new Representation(R.drawable.ic_water3));
				water3.setRepresentationKO(new Representation(R.drawable.ic_water3));
				eau.add(water);
				eau.add(water2);
				eau.add(water3);
				listDataChild.put(listDataHeader.get(1), eau);

				expMenuTitle.setText("Eléments d'environnement à placer");
			}
			else if(typeEntity.getId().equals("#moyen")){
				listDataHeader.add("Vehicule");
				List<IEntity> vehicule = new ArrayList<IEntity>();
				
				Moyen fpt = new Moyen(TypeMoyen.FPT, intervention);
				fpt.setLibelle("FPT");

				Moyen vsav = new Moyen(TypeMoyen.VSAV, intervention);
				vsav.setLibelle("VSAV");

				Moyen vsr = new Moyen(TypeMoyen.VSR, intervention);
				vsr.setLibelle("VSR");

				Moyen ccfm = new Moyen(TypeMoyen.CCFM, intervention);
				ccfm.setLibelle("CCFM");

				Moyen ccgc = new Moyen(TypeMoyen.CCGC, intervention);
				ccgc.setLibelle("CCGC");

				Moyen var = new Moyen(TypeMoyen.VAR, intervention);
				var.setLibelle("VAR");
				
				Moyen vls = new Moyen(TypeMoyen.VLS, intervention);
				vls.setLibelle("VLS");
				
				Moyen vlcc = new Moyen(TypeMoyen.VLCC, intervention);
				vlcc.setLibelle("VLS");
				
				Moyen vlcg = new Moyen(TypeMoyen.VLCG, intervention);
				vlcg.setLibelle("VLS");
				
				vehicule.add(fpt);
				vehicule.add(vsav);
				vehicule.add(vsr);
				vehicule.add(ccfm);
				vehicule.add(ccgc);
				vehicule.add(var);
				vehicule.add(vls);
				vehicule.add(vlcc);
				vehicule.add(vlcg);
				listDataChild.put(listDataHeader.get(0), vehicule);

				listDataHeader.add("GROUPE");
				List<IEntity> groupe = new ArrayList<IEntity>();
				Moyen temp = new Moyen(TypeMoyen.FPT, intervention);
				temp.setLibelle("Groupe");
				
				groupe.add(temp);
				listDataChild.put(listDataHeader.get(1), groupe);

				expMenuTitle.setText("Moyen a engager");

			}
			currentX = event.getX();
			currentY = event.getY();
		}
		else{
			//Create entity to set on map
			((IBackground) fragment).addEntity(typeEntity, event.getX(), event.getY());
		}
		expandableAdapter = new ExpandableListAdapter(this.getActivity(), listDataHeader, listDataChild);
		// setting list adapter
		expListView.setAdapter(expandableAdapter);
		return true;
	}



	public class onClickExpandableListItemListener implements OnChildClickListener {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			IEntity i = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
			((IBackground) fragment).addEntity((Entity) i, currentX,
					currentY);
			hideExpMenu();
			return false;
		}
	}

	/*
	 * Preparing the list data
	 */
	@Override
	protected void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<IEntity>>();

		//		// Adding child data
		//		listDataHeader.add("Top 250");
		//
		//		// Adding child data
		//		List<IEntity> top250 = new ArrayList<IEntity>();
		//		IEntity danger_black = new Entity();
		//		danger_black.setLibelle("Cheminements");
		//		danger_black.setRepresentationOK(new Representation(R.drawable.ic_danger_black));
		//		danger_black.setRepresentationKO(new Representation(R.drawable.ic_danger_black));
		//		top250.add(danger_black);
		//		
		//		listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
	}
}
