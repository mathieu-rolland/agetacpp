package com.istic.agetac.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
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
import com.istic.agetac.controllers.mapsDock.MapObserver;
import com.istic.agetac.model.Environnement;
import com.istic.agetac.model.EnvironnementStatic;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Line;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;
import com.istic.agetac.model.TypeMoyen;
import com.istic.agetac.pattern.observer.Observer;
import com.istic.agetac.pattern.observer.Subject;
import com.istic.agetac.sync.moyen.MoyenBroadcast;
import com.istic.agetac.sync.moyen.MoyenIntentService;
import com.istic.sit.framework.adapter.ExpandableListAdapter;
import com.istic.sit.framework.api.model.IEntity;
import com.istic.sit.framework.api.view.IBackground;
import com.istic.sit.framework.model.ALine;
import com.istic.sit.framework.model.Entity;
import com.istic.sit.framework.model.Representation;
import com.istic.sit.framework.view.MainFragment;
import com.istic.sit.framework.view.MapFragment;

public class SitacFragment extends MainFragment implements Observer {

	public static SitacFragment newInstance() {
		SitacFragment fragment = new SitacFragment();
		return fragment;
	}

	List<IMoyen> listMoyens;

	private Intervention intervention;
	
	private PendingIntent pendingIntent;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);

		this.intervention = AgetacppApplication.getIntervention();
		initializeBackground(TypeBackgroundEnum.Map, savedInstanceState);

		listMoyens = intervention.getMoyens();

		MapFragment mapFragment = (MapFragment) super.getFragment();

		// register observer for map event :
		mapFragment.setMapReadyObserver(this);
		mapFragment.registerObserver(new MapObserver());

		Geocoder coder = new Geocoder(getActivity());
		Log.d("ADDRESSES", intervention.getAdresse());
		try {
			ArrayList<Address> adresses = (ArrayList<Address>) coder
					.getFromLocationName(intervention.getAdresse(), 1);
			MapFragment.INITPOSITION = new LatLng(
					adresses.get(0).getLatitude(), adresses.get(0)
							.getLongitude());
		} catch (IOException e) {
			e.printStackTrace();
		}

		menuUpdate(listMoyens);

		onClickExpandableListItemListener = new onClickExpandableListItemListener();
		expListView.setOnChildClickListener(onClickExpandableListItemListener);

		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		startServiceSynchronisation();
	}

	@Override
	public void startServiceSynchronisation()
	{
//		PoolSynchronisation ps = FrameworkApplication.getPoolSynchronisation();
//		ms = new MoyenService("Moyen sync");
//		mb = new MoyenBroadcast_custtom( this );
//		ps.registerServiceSync(MainFragment.RECEIVE_SYNC, ms, mb);
//		Log.d("Moyen", "Synchronisation started");
		if( AgetacppApplication.ACTIVE_ALL_SYNCHRO
				&& AgetacppApplication.ACTIVE_MAP_SYNCHRO ){
			
			LocalBroadcastManager bManager = LocalBroadcastManager.getInstance( getActivity().getApplicationContext() );
			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction( MoyenIntentService.CHANNEL );
			
			MoyenIntentService service = new MoyenIntentService();
			MoyenBroadcast broadcast = new MoyenBroadcast(this);
			
			bManager.registerReceiver( broadcast , intentFilter);
	
			Intent intent = new Intent(getActivity(), service.getClass());
			pendingIntent = PendingIntent.getService(getActivity(), 0, intent, 0);
			
			Calendar cal = Calendar.getInstance();
			AlarmManager alarm = (AlarmManager)getActivity().getSystemService( Context.ALARM_SERVICE );
			alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 
					service.getIntervalToRefresh() * 1000 , pendingIntent); 
			Log.d("Pool synchronisation service", "Service moyen now started.");
		}
	}
	
	@Override
	public void onItemMenuClicked(int position, View view) {
		IEntity entity = (IEntity) this.entityAdapter.getItem(position);

		Toast.makeText(getActivity().getBaseContext(), "Click on Item",Toast.LENGTH_SHORT).show();

		// centrer sur entity
		if (entity.getPosition() != null && entity.isOnMap())
			((MapFragment) getFragment()).gotoMyLocation(entity.getPosition());
		// TODO : affichage infos
	}

	@Override
	public void onCreateSlideMenu() {
		IEntity moyen = new Environnement(intervention);
		moyen.setLibelle("[+] Moyens");
		moyen.setId("#moyen");
		moyen.setRepresentationOK(new Representation(R.drawable.ic_camion));
		moyen.setRepresentationKO(new Representation(R.drawable.ic_camion));

		IEntity environmentDynamique = new Environnement(intervention);
		environmentDynamique.setLibelle("[+] Env. dynamique");
		environmentDynamique.setId("#environmentDynamique");
		environmentDynamique.setRepresentationOK(new Representation(
				R.drawable.ic_water));
		environmentDynamique.setRepresentationKO(new Representation(
				R.drawable.ic_water));

		IEntity environmentStatique = new Environnement(intervention);
		environmentStatique.setLibelle("[+] Env. statique");
		environmentStatique.setId("#environmentStatique");
		environmentStatique.setRepresentationOK(new Representation(
				R.drawable.ic_water3));
		environmentStatique.setRepresentationKO(new Representation(
				R.drawable.ic_water3));

		this.addItemMenuDefault(moyen);
		this.addItemMenuDefault(environmentDynamique);
		this.addItemMenuDefault(environmentStatique);

		Intervention intervention = AgetacppApplication.getIntervention();
		listMoyens = intervention.getMoyens();

		for (IMoyen moyenToadd : listMoyens) {
			addItemMenu(((Moyen) moyenToadd));
		}

		List<Secteur> listSecteur = intervention.getSecteurs();
		int i = 0;
		boolean find = false;
		while (i < listSecteur.size() && !find) {
			Log.e("VINCENT", "Secteur trouvé : "
					+ listSecteur.get(i).getLibelle());
			find = (listSecteur.get(i).getLibelle().equals("CRM"));
			i++;
		}

		if (find) {
			Log.e("VINCENT", "ON LA TROUVé CE PUTIN DE CRM");
			Secteur CRM = listSecteur.get(i - 1);

			CRM.setRepresentationOK(new Representation(R.drawable.crm));
			CRM.setRepresentationKO(new Representation(R.drawable.crm));

			addItemMenu(CRM);
		} else {
			Log.e("VINCENT", "ON LA PAS TROUVé CE PUTIN DE CRM");
		}

		// new MoyensDao(new IViewReceiver<Moyen>() {
		//
		// @Override
		// public void notifyResponseSuccess(List<Moyen> objects) {
		// listMoyens = objects;
		// for (IEntity moyenToadd : objects) {
		// addItemMenu(moyenToadd);
		// }
		// }
		//
		// @Override
		// public void notifyResponseFail(VolleyError error) {
		// // TODO Auto-generated method stub
		//
		// }
		// }).findAll();
	}

	public void menuUpdate(List<IMoyen> moyens) {
		boolean exist;
		for (IMoyen newMoyen : moyens) {
			exist = false;
			for (int i = 0; i < itemsMenu.size(); i++) {
				Entity oldentity = (Entity) itemsMenu.get(i);
				if (((Moyen) newMoyen).getId().equals(oldentity.getId())) {
					exist = true;
					
					if (newMoyen.getHFree() != null){
						removeItem((Moyen) oldentity);
					}else{
						// Mise a jour boolean
						oldentity.setOk(((Moyen) newMoyen).isOk());
						oldentity.setOnMap(((Moyen) newMoyen).isOnMap());
					}
					entityAdapter.notifyDataSetChanged();
				}
			}
			if (!exist && (newMoyen.getHFree() == null)) {
				addItemMenu((Moyen) newMoyen);
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
		if (synchronizedEntities != null && isAdded()){
			((MapFragment) getFragment()).updateEntities(synchronizedEntities);
			
		}
		List<IMoyen> moyens = new ArrayList<IMoyen>();
		for( Entity entity : synchronizedEntities ){
			moyens.add( (IMoyen) entity );
		}
		menuUpdate(moyens);
	}

	private void stopSynchronisation() {
		AlarmManager alarm = (AlarmManager) getActivity().getSystemService(
				Context.ALARM_SERVICE);
		PendingIntent pi = ser.getPendingIntent();
		alarm.cancel(pi);
	}

	@Override
	public void onStop() {
		if( AgetacppApplication.ACTIVE_ALL_SYNCHRO
				&& AgetacppApplication.ACTIVE_MAP_SYNCHRO ){
			AlarmManager alarm = (AlarmManager)
					getActivity().getSystemService(Context.ALARM_SERVICE);
			alarm.cancel(pendingIntent);
		}
		super.onStop();
		
	}

	@Override
	protected boolean onActionDropFromMenu(Entity typeEntity, DragEvent event) {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<IEntity>>();

		if (typeEntity.getId().equals("#environmentDynamique")
				|| typeEntity.getId().equals("#environmentStatique")
				|| typeEntity.getId().equals("#moyen")) {
			// Affichage du gridview de choix de moyens
			// MapFragment mapFragment = (MapFragment) fragment;
			showEntityGridMenu(typeEntity, event.getX(), event.getY());

			clearExpMenu();
			if (typeEntity.getId().equals("#environmentDynamique")) {
				// Danger
				listDataHeader.add("Sources de danger");
				List<IEntity> danger = new ArrayList<IEntity>();

				IEntity danger_blue = new Environnement(intervention);
				danger_blue.setLibelle("Ayant trait à l'eau");
				danger_blue.setRepresentationOK(new Representation(
						R.drawable.ic_danger_blue));
				danger_blue.setRepresentationKO(new Representation(
						R.drawable.ic_danger_blue));
				IEntity danger_green = new Environnement(intervention);
				danger_green.setLibelle("Personnes");
				danger_green.setRepresentationOK(new Representation(
						R.drawable.ic_danger_green));
				danger_green.setRepresentationKO(new Representation(
						R.drawable.ic_danger_green));
				IEntity danger_orange = new Environnement(intervention);
				danger_orange.setLibelle("Particuliers");
				danger_orange.setRepresentationOK(new Representation(
						R.drawable.ic_danger_orange));
				danger_orange.setRepresentationKO(new Representation(
						R.drawable.ic_danger_orange));
				IEntity danger_red = new Environnement(intervention);
				danger_red.setLibelle("Incendie");
				danger_red.setRepresentationOK(new Representation(
						R.drawable.ic_danger_red));
				danger_red.setRepresentationKO(new Representation(
						R.drawable.ic_danger_red));

				danger.add(danger_blue);
				danger.add(danger_green);
				danger.add(danger_orange);
				danger.add(danger_red);
				listDataChild.put(listDataHeader.get(0), danger);

				// Risk
				listDataHeader.add("Points sensibles");
				List<IEntity> risk = new ArrayList<IEntity>();

				IEntity risk_blue = new Environnement(intervention);
				risk_blue.setLibelle("Ayant trait à l'eau");
				risk_blue.setRepresentationOK(new Representation(
						R.drawable.ic_risk_blue));
				risk_blue.setRepresentationKO(new Representation(
						R.drawable.ic_risk_blue));
				IEntity risk_green = new Environnement(intervention);
				risk_green.setLibelle("Personnes");
				risk_green.setRepresentationOK(new Representation(
						R.drawable.ic_risk_green));
				risk_green.setRepresentationKO(new Representation(
						R.drawable.ic_risk_green));
				IEntity risk_orange = new Environnement(intervention);
				risk_orange.setLibelle("Particuliers");
				risk_orange.setRepresentationOK(new Representation(
						R.drawable.ic_risk_orange));
				risk_orange.setRepresentationKO(new Representation(
						R.drawable.ic_risk_orange));
				IEntity risk_red = new Environnement(intervention);
				risk_red.setLibelle("Incendie");
				risk_red.setRepresentationOK(new Representation(
						R.drawable.ic_risk_red));
				risk_red.setRepresentationKO(new Representation(
						R.drawable.ic_risk_red));

				risk.add(risk_blue);
				risk.add(risk_green);
				risk.add(risk_orange);
				risk.add(risk_red);
				listDataChild.put(listDataHeader.get(1), risk);

				// Water
				listDataHeader.add("Prise d'eau");
				List<IEntity> eau = new ArrayList<IEntity>();
				IEntity water = new Environnement(intervention);
				water.setLibelle("Point d'eau pèrenne");
				water.setRepresentationOK(new Representation(
						R.drawable.ic_water));
				water.setRepresentationKO(new Representation(
						R.drawable.ic_water));
				IEntity water2 = new Environnement(intervention);
				water2.setLibelle("Point d'eau non pèrenne");
				water2.setRepresentationOK(new Representation(
						R.drawable.ic_water2));
				water2.setRepresentationKO(new Representation(
						R.drawable.ic_water2));
				IEntity water3 = new Environnement(intervention);
				water3.setLibelle("Point de ravitaillement");
				water3.setRepresentationOK(new Representation(
						R.drawable.ic_water3));
				water3.setRepresentationKO(new Representation(
						R.drawable.ic_water3));
				eau.add(water);
				eau.add(water2);
				eau.add(water3);
				listDataChild.put(listDataHeader.get(2), eau);

				expMenuTitle.setText("Eléments d'environnement à placer");
			} else if (typeEntity.getId().equals("#environmentStatique")) {
				// Risk
				listDataHeader.add("Points sensibles");
				List<IEntity> risk = new ArrayList<IEntity>();

				IEntity risk_blue = new EnvironnementStatic();
				risk_blue.setLibelle("Ayant trait à l'eau");
				risk_blue.setRepresentationOK(new Representation(
						R.drawable.ic_risk_blue));
				risk_blue.setRepresentationKO(new Representation(
						R.drawable.ic_risk_blue));
				IEntity risk_green = new EnvironnementStatic();
				risk_green.setLibelle("Personnes");
				risk_green.setRepresentationOK(new Representation(
						R.drawable.ic_risk_green));
				risk_green.setRepresentationKO(new Representation(
						R.drawable.ic_risk_green));
				IEntity risk_orange = new EnvironnementStatic();
				risk_orange.setLibelle("Particuliers");
				risk_orange.setRepresentationOK(new Representation(
						R.drawable.ic_risk_orange));
				risk_orange.setRepresentationKO(new Representation(
						R.drawable.ic_risk_orange));
				IEntity risk_red = new EnvironnementStatic();
				risk_red.setLibelle("Incendie");
				risk_red.setRepresentationOK(new Representation(
						R.drawable.ic_risk_red));
				risk_red.setRepresentationKO(new Representation(
						R.drawable.ic_risk_red));

				risk.add(risk_blue);
				risk.add(risk_green);
				risk.add(risk_orange);
				risk.add(risk_red);
				listDataChild.put(listDataHeader.get(0), risk);

				// Water
				listDataHeader.add("Prise d'eau");
				List<IEntity> eau = new ArrayList<IEntity>();
				IEntity water = new EnvironnementStatic();
				water.setLibelle("Point d'eau pèrenne");
				water.setRepresentationOK(new Representation(
						R.drawable.ic_water));
				water.setRepresentationKO(new Representation(
						R.drawable.ic_water));
				IEntity water2 = new EnvironnementStatic();
				water2.setLibelle("Point d'eau non pèrenne");
				water2.setRepresentationOK(new Representation(
						R.drawable.ic_water2));
				water2.setRepresentationKO(new Representation(
						R.drawable.ic_water2));
				IEntity water3 = new EnvironnementStatic();
				water3.setLibelle("Point de ravitaillement");
				water3.setRepresentationOK(new Representation(
						R.drawable.ic_water3));
				water3.setRepresentationKO(new Representation(
						R.drawable.ic_water3));
				eau.add(water);
				eau.add(water2);
				eau.add(water3);
				listDataChild.put(listDataHeader.get(1), eau);

				expMenuTitle.setText("Eléments d'environnement à placer");
			} else if (typeEntity.getId().equals("#moyen")) {
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
				vlcc.setLibelle("VLCC");

				Moyen vlcg = new Moyen(TypeMoyen.VLCG, intervention);
				vlcg.setLibelle("VLCG");

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
		} else {
			// Create entity to set on map
			((IBackground) fragment).addEntity(typeEntity, event.getX(),
					event.getY());
		}
		expandableAdapter = new ExpandableListAdapter(this.getActivity(),
				listDataHeader, listDataChild);
		// setting list adapter
		expListView.setAdapter(expandableAdapter);
		return true;
	}

	public class onClickExpandableListItemListener implements
			OnChildClickListener {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			Log.d("CLICK", "Click on child");
			IEntity i = listDataChild.get(listDataHeader.get(groupPosition))
					.get(childPosition);
			((IBackground) fragment).addEntity((Entity) i, currentX, currentY);
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

		// // Adding child data
		// listDataHeader.add("Top 250");
		//
		// // Adding child data
		// List<IEntity> top250 = new ArrayList<IEntity>();
		// IEntity danger_black = new Entity();
		// danger_black.setLibelle("Cheminements");
		// danger_black.setRepresentationOK(new
		// Representation(R.drawable.ic_danger_black));
		// danger_black.setRepresentationKO(new
		// Representation(R.drawable.ic_danger_black));
		// top250.add(danger_black);
		//
		// listDataChild.put(listDataHeader.get(0), top250); // Header, Child
		// data
	}

	@Override
	public void update(Subject subject) {
		List<Entity> entities = new ArrayList<Entity>();
		for (IMoyen m : intervention.getMoyens()) {
			entities.add((Entity) m);
		}
		loadEntities(entities);
		
		List<Entity> entitiesDynamique = new ArrayList<Entity>();
		for (Environnement e : intervention.getEnvironnements()) {
			entities.add((Entity) e);
		}
		loadEntities(entitiesDynamique);
		
		List<Entity> entitiesStatic = new ArrayList<Entity>();
		for (EnvironnementStatic m : AgetacppApplication
				.getEnvironnementsStatic().getListEnvironnement()) {
			entitiesStatic.add((Entity) m);
		}
		loadEntities(entitiesStatic);

		List<Entity> secteurToDisplay = new ArrayList<Entity>();
		int i = 0;
		boolean find = false;

		List<Secteur> secteurs = AgetacppApplication.getIntervention().getSecteurs();
		while (i < secteurs.size() && !find) {
			if (secteurs.get(i).getLibelle().equals("CRM")) {
				secteurToDisplay.add(secteurs.get(i));
				find = true;
			}
			i++;
		}
		
		loadEntities(secteurToDisplay);
		
		List<ALine> lines = new ArrayList<ALine>();
		for (Line l : AgetacppApplication.getIntervention().getLines()) {
			lines.add((ALine) l);
		}
		
		loadGraphics(lines);
	}
}
