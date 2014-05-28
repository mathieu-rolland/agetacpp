package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.api.model.ISecteur;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.AMoyenExpListAdapter;
import com.istic.agetac.controler.adapter.MoyenListExpCodisAdapter;
import com.istic.agetac.controler.adapter.MoyenListExpIntervenantAdapter;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;
import com.istic.agetac.sync.moyen.MoyenBroadcast;
import com.istic.agetac.sync.moyen.MoyenIntentService;
import com.istic.agetac.sync.tableaumoyens.TableauDesMoyensReceiver;
import com.istic.agetac.sync.tableaumoyens.TableauDesMoyensSync;
import com.istic.sit.framework.sync.PoolSynchronisation;

public class TableauMoyenFragment extends Fragment {

	public static TableauMoyenFragment newInstance(boolean isCreating) {
		TableauMoyenFragment fragment = new TableauMoyenFragment();
		fragment.setmIsCreating(isCreating);
		return fragment;
	}

	private List<IMoyen> mListMoyen;

	/* Instances des modï¿½les ï¿½ utiliser */

	/* ï¿½lï¿½ments graphiques */
	private ExpandableListView mListViewMoyen; // ListView des moyens

	private AMoyenExpListAdapter mAdapterMoyens; // Adapter des moyens

	private TableauDesMoyensReceiver receiver;

	private boolean mIsCreating;
		
	private Intervention intervention;
	BroadcastReceiver broadcastReceiver;
	
	private PendingIntent pendingIntent;
	
//	ReceiverTdm rtdm ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_tableau_moyen,
				container, false);

		intervention = AgetacppApplication.getIntervention();
		mListViewMoyen = (ExpandableListView) view.findViewById(R.id.fragment_tableau_moyen_expandable_list);

		/* R�cup�rations des donn�es via les mod�les */
		
		if (AgetacppApplication.getUser().getRole() == Role.codis) {
			mAdapterMoyens = new MoyenListExpCodisAdapter(getActivity());
			startSynchroForCodis();
		} else {
			mAdapterMoyens = new MoyenListExpIntervenantAdapter(getActivity());
		}

		List<IMoyen> liste = AgetacppApplication.getIntervention().getMoyens();
		mAdapterMoyens.addAll(liste);
				
		mListViewMoyen.setAdapter(mAdapterMoyens);
		
		if(mListMoyen==null)
		{
			if(intervention == null) {
				mListMoyen = new ArrayList<IMoyen>();
			}
			else {
				mListMoyen = new ArrayList<IMoyen>(intervention.getMoyens());
			}
		}
		else
		{
			mAdapterMoyens.addAll(mListMoyen);
		}

        mListViewMoyen.setAdapter( mAdapterMoyens );

        if(intervention != null){
        	mAdapterMoyens.setSectorAvailable(intervention.getSecteurs());
        }
		mAdapterMoyens.notifyDataSetChanged();
        
		if( AgetacppApplication.getUser().getRole() != Role.codis ){
			LocalBroadcastManager bManager = LocalBroadcastManager.getInstance( getActivity().getApplicationContext() );
			
			IntentFilter filter = new IntentFilter( MoyenIntentService.CHANNEL );
			filter.addAction( MoyenIntentService.CHANNEL );
			
			broadcastReceiver = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent) {
	//				if( AgetacppApplication.getUser().getRole() == Role.codis ){
						updateTableauDesMoyen( (List)intent.getExtras()
						.getParcelableArrayList( MoyenIntentService.CHANNEL ) );
						Log.d("SYNCHRO", "Notify CODIS");
	//				}
					mAdapterMoyens.notifyDataSetChanged();
					
					Log.d("SYNCHRO", "Notify data set change broadcast anonyme");
					}
			};
		
//		rtdm = new ReceiverTdm(this);
		
			bManager.registerReceiver( broadcastReceiver , filter);
		}
//		bManager.registerReceiver( rtdm , filter);
		
        return view;
    }

	private void startSynchroForCodis() {
		if( AgetacppApplication.ACTIVE_ALL_SYNCHRO
				&& AgetacppApplication.ACTIVE_TDM_CODIS_SYNCHRO ){
			
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


	public void updateTableauDesMoyen(List<Moyen> moyens) {
		// // TODO implï¿½menter la rï¿½ception de la synchro.
		 Log.d( "Synchro", " Recieve sync for tableau des moyens : " + moyens ==
				 null ? "Moyen is null" : "Size : " + moyens.size() );
		 
		 List<Moyen> moyensWaiting = new ArrayList<Moyen>();
		 for ( Moyen moyenServer : moyens )
		 {
			 boolean found = false;
			 for ( IMoyen localMoyen : mListMoyen )
			 {
				 if ( localMoyen.getId().equals( moyenServer.getId() ) )
				 {
					 found = true;
					 if(moyenServer.getHArrival() != null && !moyenServer.getHArrival().equals("")) localMoyen.setHArrival( moyenServer.getHArrival() );
					 localMoyen.setHDemande(moyenServer.getHDemande());
					 if(moyenServer.getHEngagement() != null && !moyenServer.getHEngagement().equals("")) localMoyen.setHEngagement( moyenServer.getHEngagement() );
					 if(moyenServer.getHFree() != null && !moyenServer.getHFree().equals("")) localMoyen.setHFree( moyenServer.getHFree() );
					 localMoyen.setIsInGroup( moyenServer.isInGroup() );
					 localMoyen.setLibelle( moyenServer.getLibelle() );
					 localMoyen.setRepresentationOK( moyenServer.getRepresentationOK() );
					 Secteur secteur = moyenServer.getSecteur(intervention);
					 if( secteur != null)
						 localMoyen.setSecteur( secteur );					 
				 }
			 }
			 if ( !found )
			 {
				 moyensWaiting.add( moyenServer );
			 }
		 }
		 for ( Moyen moyen : moyensWaiting )
		 {
			 moyen.setIntervention(intervention);
			 mListMoyen.add(moyen);
		 }
		 this.mAdapterMoyens.notifyDataSetChanged();
	}

	private void stopSynchronisation() {
		if( AgetacppApplication.ACTIVE_ALL_SYNCHRO && AgetacppApplication.ACTIVE_TDM_SYNCHRO ){
			AlarmManager alarm = (AlarmManager) getActivity().getSystemService(
					Context.ALARM_SERVICE);
			PendingIntent pi = receiver.getPendingIntent();
			alarm.cancel(pi);
		}
	}

	@Override
	public void onStop() {
		stopSynchronisation();
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
		if( pendingIntent != null ){
			AlarmManager alarm = (AlarmManager) getActivity().getSystemService(
					Context.ALARM_SERVICE);
			alarm.cancel(pendingIntent);
		}
		super.onStop();
	}

	@Override
	public void onResume() {
		
		PoolSynchronisation pool = AgetacppApplication.getPoolSynchronisation();
		if( AgetacppApplication.ACTIVE_ALL_SYNCHRO
				&& AgetacppApplication.ACTIVE_TDM_SYNCHRO ){
			receiver = new TableauDesMoyensReceiver(this);
			TableauDesMoyensSync sync = new TableauDesMoyensSync();
	
			pool.registerServiceSync(TableauDesMoyensSync.FILTER_MESSAGE_RECEIVER,
					sync, receiver);
		}
		super.onResume();
	}

	public boolean ismIsCreating() {
		return mIsCreating;
	}

	public void setmIsCreating(boolean mIsCreating) {
		this.mIsCreating = mIsCreating;
	}


	public void updateView() {
		mAdapterMoyens.notifyDataSetChanged();
		Log.d("SYNCHRO", "notify data set change update view");
	}

}
