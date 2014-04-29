package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.istic.agetac.controllers.dao.SecteurDao;
import com.istic.agetac.model.Moyen;

public class TableauMoyenFragment extends Fragment {

	/** Instances des modèles à utiliser */
	private SecteurDao mSecteur;
	private ListView mListViewMoyen;
	private List<Moyen> mListMoyen;
	private AMoyenListAdapter mAdapterMoyen;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_tableau_moyen,
				container, false);

		mListViewMoyen = (ListView) view
				.findViewById(R.id.fragment_tableau_moyen_list_view);

		// MoyensDao moyen = new MoyensDao(new MoyenViewReceiver());
		// moyen.findAll();
		mListMoyen = new ArrayList<Moyen>();
		Moyen m1 = new Moyen("essai");
		mListMoyen.add(m1);
		Moyen m2 = new Moyen("essai2");
		m2.setHDemande(new Date());
		m2.setHEngagement(new Date());
		m2.setLibelle("ambulance");
		mListMoyen.add(m2);
		Moyen m3 = new Moyen("essai3");
		m3.setHDemande(new Date(2014, 06, 06));
		m3.setHEngagement(new Date(2014, 07, 07));
		m3.setSecteur("secteur");
		m3.setHArrival(new Date(2014, 8, 8));
		m3.setHFree(new Date());
		m3.setLibelle("camion rouge");
		mListMoyen.add(m3);

		if (AgetacppApplication.getUser().getRole() == Role.codis) {
			mAdapterMoyen = new MoyenListCodisAdapter(this.getActivity(),mListMoyen);
		} else {
			mAdapterMoyen = new MoyenListIntervenantAdapter(this.getActivity(),	mListMoyen);
		}

		mListViewMoyen.setAdapter(mAdapterMoyen);

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
		return mAdapterMoyen;
	}

	public class MoyenViewReceiver implements IViewReceiver<Moyen> {
		@Override
		public void notifyResponseSuccess(List<Moyen> moyens) {
			mListMoyen = moyens;
			mAdapterMoyen.notifyDataSetChanged();
		}

		@Override
		public void notifyResponseFail(VolleyError error) {
			Toast.makeText(getActivity(), "Impossible de récupérer les moyens",
					Toast.LENGTH_SHORT).show();
		}

	}

	/**
	 * @return the mSecteur
	 */
	public SecteurDao getmSecteur() {
		return mSecteur;
	}

	/**
	 * @param mSecteur
	 *            the mSecteur to set
	 */
	public void setmSecteur(SecteurDao mSecteur) {
		this.mSecteur = mSecteur;
	}

}
