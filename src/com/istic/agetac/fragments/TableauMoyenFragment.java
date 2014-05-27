package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.AMoyenExpListAdapter;
import com.istic.agetac.controler.adapter.MoyenListExpCodisAdapter;
import com.istic.agetac.controler.adapter.MoyenListExpIntervenantAdapter;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Moyen;
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
        
        return view;
    }


	public void updateTableauDesMoyen(List<Moyen> moyens) {
		// // TODO implï¿½menter la rï¿½ception de la synchro.
		// Log.d( "Synch", " Recieve sync for tableau des moyens : " + moyens ==
		// null ? "Moyen is null" : "Size : " + moyens.size() );
		// List<Moyen> moyensWaiting = new ArrayList<Moyen>();
		// for ( Moyen moyenServer : moyens )
		// {
		//
		// boolean found = false;
		// for ( Moyen localMoyen : mListMoyen )
		// {
		// if ( localMoyen.getId().equals( moyenServer.getId() ) )
		// {
		// found = true;
		// localMoyen = moyenServer;
		// break;
		// }
		// }
		// if ( !found )
		// {
		// moyensWaiting.add( moyenServer );
		// }
		// }
		// for ( Moyen moyen : moyensWaiting )
		// {
		// mListMoyen.add( moyen );
		// }
		// this.mAdapterMoyens.notifyDataSetChanged();
	}

	private void stopSynchronisation() {
		AlarmManager alarm = (AlarmManager) getActivity().getSystemService(
				Context.ALARM_SERVICE);
		PendingIntent pi = receiver.getPendingIntent();
		alarm.cancel(pi);
	}

	@Override
	public void onStop() {
		stopSynchronisation();
		super.onStop();
	}

	@Override
	public void onResume() {

		PoolSynchronisation pool = AgetacppApplication.getPoolSynchronisation();

		receiver = new TableauDesMoyensReceiver(this);
		TableauDesMoyensSync sync = new TableauDesMoyensSync();

		pool.registerServiceSync(TableauDesMoyensSync.FILTER_MESSAGE_RECEIVER,
				sync, receiver);
		super.onResume();
	}

	public boolean ismIsCreating() {
		return mIsCreating;
	}

	public void setmIsCreating(boolean mIsCreating) {
		this.mIsCreating = mIsCreating;
	}

//	public class SectorRecuperator extends APersitantRecuperator<Secteur> {
//
//		public SectorRecuperator(String idIntervention) {
//			super(Secteur.class);
//		}
//
//		@Override
//		public void onErrorResponse(VolleyError error) {
//			Toast.makeText(getActivity(), "Impossible de récupérer les moyens",
//					Toast.LENGTH_SHORT).show();
//		}
//
//		@Override
//		public void onResponse(List<Secteur> secteurs) {
//			mAdapterMoyens.setSectorAvailable(secteurs);
//			mAdapterMoyens.notifyDataSetChanged();
//		}
//	}

}
