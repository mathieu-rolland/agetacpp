package com.istic.agetac.fragments;

import java.util.ArrayList;
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
import com.istic.agetac.controllers.listeners.tableauMoyen.SwitchSector;
import com.istic.agetac.model.CreationBase;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.sync.tableaumoyens.TableauDesMoyensReceiver;
import com.istic.agetac.sync.tableaumoyens.TableauDesMoyensSync;
import com.istic.sit.framework.sync.PoolSynchronisation;

public class TableauMoyenFragment extends Fragment {

	/* Instances des modèles è utiliser */
	private MoyensDao mMoyen; // Modèle Moyen

	/* Donnèes rècupèrèes */
	private List<Moyen> datasMoyen; // Datas moyens rècupèrès

	/* èlèments graphiques */
	private ListView listViewMoyen; // ListView des moyens
	private AMoyenListAdapter adapterMoyens; // Adapter des moyens

	/* Controlers */
	private SwitchSector cSecteur;

	private TableauDesMoyensReceiver receiver;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_tableau_moyen,
				container, false);

		//CreationBase.createSecteur();

		listViewMoyen = (ListView) view
				.findViewById(R.id.fragment_tableau_moyen_list_view);

		/* Rècupèrations des donnèes via les modèles */
		datasMoyen = new ArrayList<Moyen>();
		mMoyen = new MoyensDao(new MoyenViewReceiver());
		mMoyen.findAll();

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public static TableauMoyenFragment newInstance() {
		TableauMoyenFragment fragment = new TableauMoyenFragment();
		return fragment;
	}

	public AMoyenListAdapter getAdapter() {
		return adapterMoyens;
	}

	public class MoyenViewReceiver implements IViewReceiver<Moyen> {
		@Override
		public void notifyResponseSuccess(List<Moyen> moyens) {
			datasMoyen = moyens;
			if (AgetacppApplication.getUser().getRole() == Role.codis) {
				adapterMoyens = new MoyenListCodisAdapter(getActivity(),
						datasMoyen);
			} else {
				adapterMoyens = new MoyenListIntervenantAdapter(getActivity(),
						datasMoyen);
			}
			adapterMoyens.notifyDataSetChanged();
			listViewMoyen.setAdapter(adapterMoyens);
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
		// TODO implémenter la réception de la synchro.
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
