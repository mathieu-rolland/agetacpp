package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IMessage;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.HistoriqueListAdapter;
import com.istic.agetac.controler.adapter.ItemListAdapter;
import com.istic.agetac.model.Action;
import com.istic.agetac.model.Intervention;
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
	private ArrayList<Action> listAction;
	private HistoriqueListAdapter histoAdapter;
	private Intervention intervention;

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

		intervention = AgetacppApplication.getIntervention();
		listAction = (ArrayList<Action>) intervention.getHistorique();
		
		/** Chargement du layout */
		View view = inflater.inflate(R.layout.fragment_historique, container, false);

		// Récupération des éléments 
		lvHisto = (ListView)view.findViewById(R.id.lv_Histo);
		
		//Adapter de l'historique.
		histoAdapter = new HistoriqueListAdapter(this, android.R.layout.simple_list_item_1, listAction);
		lvHisto.setAdapter(histoAdapter);

		/** LOG */
		Log.d("Marion",  "Instanciations faites");

		return view;

	}


	/** Méthode onSaveInstanceState */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {

		//		/** LOG */
		//		Log.d("Marion", "Sauvegarde faite : " + sauvegarde + "");
		//		
		//		/** Sauvegarde */
		//		savedInstanceState.putSerializable("sauvegarde", sauvegarde);
		super.onSaveInstanceState(savedInstanceState);
	}

}
