package com.istic.agetac.fragments;

import java.util.List;

import com.istic.agetac.R;
import com.istic.agetac.model.Action;
import com.istic.agetac.model.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoriqueFragment extends Fragment{

	private ListView lvHisto;
	private List<Action> listAction;
	
	public static HistoriqueFragment newInstance() {
		HistoriqueFragment fragment = new HistoriqueFragment();
		return fragment;
	}
		
	/** Méthode onCreate */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
	}
	
	/** Méthode onCreateView */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
	   	/** Chargement du layout */
		View view = inflater.inflate(R.layout.fragment_historique, container, false);
		
		/** Récupérations des données via les modéles */
//		this.mMoyens.findAll();
		
		/** LOG */
		Log.d("Marion",  "Instanciations faites");
		
		return view;
		
	}
	
	/** Méthode onActivityCreated */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);			
					
		// Récupération des éléments 
		lvHisto = (ListView)getActivity().findViewById(R.id.lv_Histo);
	    listAction.add(new Action());
	}
	
	/** Méthode onSaveInstanceState */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		
//		/** LOG */
//		Log.d("Marion", "Sauvegarde faite : " + sauvegarde + "");
//		
//		/** Sauvegarde */
//		savedInstanceState.putSerializable("sauvegarde", sauvegarde);
//	    super.onSaveInstanceState(savedInstanceState);
	}

}
