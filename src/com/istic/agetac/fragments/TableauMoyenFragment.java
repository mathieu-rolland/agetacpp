package com.istic.agetac.fragments;

import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.AMoyenListAdapter;
import com.istic.agetac.controler.adapter.MoyenListCodisAdapter;
import com.istic.agetac.controler.adapter.MoyenListIntervenantAdapter;
import com.istic.agetac.controllers.dao.MoyensDao;
import com.istic.agetac.controllers.dao.SecteurDao;
import com.istic.agetac.controllers.listeners.tableauMoyen.SwitchSector;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;
import com.istic.agetac.sync.tableaumoyens.TableauDesMoyensReceiver;
import com.istic.agetac.sync.tableaumoyens.TableauDesMoyensSync;
import com.istic.sit.framework.sync.PoolSynchronisation;

public class TableauMoyenFragment extends Fragment {

	public static TableauMoyenFragment newInstance() {
		TableauMoyenFragment fragment = new TableauMoyenFragment();
		return fragment;
	}
	
	/* Instances des mod�les � utiliser */
	private MoyensDao mMoyen; // Mod�le Moyen

	/* �l�ments graphiques */
	private ListView mListViewMoyen; // ListView des moyens
	private AMoyenListAdapter mAdapterMoyens; // Adapter des moyens

	/* Controlers */
	private SwitchSector cSecteur;

	private TableauDesMoyensReceiver receiver;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_tableau_moyen,
				container, false);

		//CreationBase.createSecteur();

		mListViewMoyen = (ListView) view
				.findViewById(R.id.fragment_tableau_moyen_list_view);

		/* R�cup�rations des donn�es via les mod�les */
		mMoyen = new MoyensDao(new MoyenViewReceiver());
		mMoyen.findAll();
		
		if (AgetacppApplication.getUser().getRole() == Role.codis) {
			mAdapterMoyens = new MoyenListCodisAdapter(getActivity());
		} else {
			mAdapterMoyens = new MoyenListIntervenantAdapter(getActivity());
		}
		
		SecteurDao sdao = new SecteurDao( new OnSecteurReceived() );
		sdao.findAll();
		
		return view;
	}

	//Récupération des secteurs :
	private class OnSecteurReceived implements IViewReceiver<Secteur>
	{
		@Override
		public void notifyResponseSuccess(List<Secteur> objects) {
			if( objects != null ){
				TableauMoyenFragment.this.mAdapterMoyens.addAllSecteurs(objects);
			}
			mAdapterMoyens.notifyDataSetChanged();
		}

		@Override
		public void notifyResponseFail(VolleyError error) {
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public List<Moyen> getAllMoyen()
	{
		return mAdapterMoyens.getAll();
	}

	public AMoyenListAdapter getAdapter() {
		return mAdapterMoyens;
	}

	public class MoyenViewReceiver implements IViewReceiver<Moyen> {
		@Override
		public void notifyResponseSuccess(List<Moyen> moyens) {
			mAdapterMoyens.addAll(moyens);
			
			mAdapterMoyens.notifyDataSetChanged();
			mListViewMoyen.setAdapter(mAdapterMoyens);
		}

		@Override
		public void notifyResponseFail(VolleyError error) {
			Toast.makeText(getActivity(), "Impossible de récupérer les moyens",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * @return the mMoyen
	 */
	public MoyensDao getmMoyen() {
		return mMoyen;
	}

	/**
	 * @param mMoyen
	 *            the mMoyen to set
	 */
	public void setmMoyen(MoyensDao mMoyen) {
		this.mMoyen = mMoyen;
	}

	public void updateTableauDesMoyen(List<Moyen> moyens) {
		// TODO impl�menter la r�ception de la synchro.
		Log.d("Synch",
				" Recieve sync for tableau des moyens : " + moyens == null ? "Moyen is null"
						: "Size : " + moyens.size());
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

}
