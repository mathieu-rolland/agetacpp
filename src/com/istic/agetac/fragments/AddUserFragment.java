package com.istic.agetac.fragments;

import java.util.List;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IUser;
import com.istic.agetac.controler.adapter.MoyenListIntervenantAdapter;
import com.istic.agetac.controler.adapter.UserAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class AddUserFragment extends Fragment{

	private ListView mListDispo;
	private ListView mListAdded;
	private Button mGreaterThan;
	private Button mLowerThan;
	
	private UserAdapter mAdapterDispo;
	private UserAdapter mAdapterAdded;
	
	private List<IUser> mDispo;
	private List<IUser> mAdded;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_add_user, container, false);

		mListDispo = (ListView) view.findViewById(R.id.fragment_add_user_label_dispo);
		mListAdded = (ListView) view.findViewById(R.id.fragment_add_user_label_to_add);
		mGreaterThan = (Button) view.findViewById(R.id.fragment_add_user_button_add);
		mLowerThan = (Button) view.findViewById(R.id.fragment_add_user_button_remove);
		
		mAdapterDispo = new UserAdapter(this.getActivity(), mDispo);
		mListDispo.setAdapter(mAdapterDispo);
		
		mAdapterAdded = new UserAdapter(this.getActivity(), mAdded);
		mListAdded.setAdapter(mAdapterAdded);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
}
