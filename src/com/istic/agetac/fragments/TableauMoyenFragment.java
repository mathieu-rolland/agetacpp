package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.istic.agetac.model.Moyen;

public class TableauMoyenFragment extends Fragment {

	/* Instances des modèles à utiliser */
	private MoyensDao mMoyen; // Modèle Moyen

	/* Données récupérées */
	private List<Moyen> datasMoyen; // Datas moyens récupérés

	/* Éléments graphiques */
	private ListView listViewMoyen; // ListView des moyens
	private AMoyenListAdapter adapterMoyens; // Adapter des moyens

	/* Controlers */
	private SwitchSector cSecteur;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_tableau_moyen,
				container, false);

		listViewMoyen = (ListView) view
				.findViewById(R.id.fragment_tableau_moyen_list_view);

		/* Instanciations des modèles */
		// mMoyen = new MoyensDao(new MoyenViewReceiver());

		/* Récupérations des données via les modèles */
		datasMoyen = new ArrayList<Moyen>();
		// mMoyen.findAll();

		/* Instanciations des contrôlers */
		this.cSecteur = new SwitchSector(this);

		Moyen m1 = new Moyen("essai");
		datasMoyen.add(m1);
		Moyen m2 = new Moyen("essai2");
		m2.setHDemande(new Date());
		m2.setHEngagement(new Date());
		m2.setLibelle("ambulance");
		datasMoyen.add(m2);
		Moyen m3 = new Moyen("essai3");
		m3.setHDemande(new Date(2014, 06, 06));
		m3.setHEngagement(new Date(2014, 07, 07));
		m3.setSecteur("secteur");
		m3.setHArrival(new Date(2014, 8, 8));
		m3.setHFree(new Date());
		m3.setLibelle("camion rouge");
		datasMoyen.add(m3);

		if (AgetacppApplication.getUser().getRole() == Role.codis) {
			adapterMoyens = new MoyenListCodisAdapter(this.getActivity(),
					datasMoyen, null, this.cSecteur);
		} else {
			adapterMoyens = new MoyenListIntervenantAdapter(this.getActivity(),
					datasMoyen, this.cSecteur);
		}

		listViewMoyen.setAdapter(adapterMoyens);

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
			adapterMoyens.notifyDataSetChanged();
		}

		@Override
		public void notifyResponseFail(VolleyError error) {
			Toast.makeText(getActivity(), "Impossible de rï¿½cupï¿½rer les moyens",
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

}
