package com.istic.agetac.controllers.listeners.tableauMoyen;

import java.util.Date;
import java.util.HashMap;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;

import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.controler.adapter.MoyenListExpIntervenantAdapter;
import com.istic.agetac.model.Secteur;


public class ListenerSpinner implements OnItemSelectedListener {
	private ArrayAdapter<String> mArrayAdapter;
	private MoyenListExpIntervenantAdapter mAdapter;

	private HashMap<String, Secteur> mMap;

	private IMoyen mMoyen;

	public ListenerSpinner(MoyenListExpIntervenantAdapter adapter,
			ArrayAdapter<String> arrayAdapter, HashMap<String, Secteur> map,
			IMoyen moyen) {
		mAdapter = adapter;
		mArrayAdapter = arrayAdapter;
		mMoyen = moyen;
		mMap = map;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		String sector = mArrayAdapter.getItem(position);

		if (mMap.containsKey(sector)) {
			mMoyen.setSecteur(mMap.get(sector));

			if (mMoyen.getHArrival() == null) {
				mMoyen.setHArrival(new Date());
				mAdapter.notifyDataSetChanged();
				mMoyen.save();
			}
		} else {
			//mMoyen.setSecteur("");
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
}
