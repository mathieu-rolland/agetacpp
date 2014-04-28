	
package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.List;

import com.istic.agetac.R;
import com.istic.agetac.controler.adapter.MoyenListCodisAdapter;
import com.istic.agetac.model.Moyen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

public class TableauMoyenFragment extends Fragment {

	private List<Moyen> mListMoyen;
	private MoyenListCodisAdapter mAdapterMoyen;

	private ListView mListViewMoyen;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_tableau_moyen,
				container, false);

		mListViewMoyen = (ListView) view
				.findViewById(R.id.fragment_tableau_moyen_list_view);
		mListMoyen = new ArrayList<Moyen>();
		mListMoyen.add(new Moyen("essai"));
		mListMoyen.add(new Moyen("essai"));

		mAdapterMoyen = new MoyenListCodisAdapter(this.getActivity(),
				mListMoyen);
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

	public MoyenListCodisAdapter getAdapter() {
		return mAdapterMoyen;
	}
}
