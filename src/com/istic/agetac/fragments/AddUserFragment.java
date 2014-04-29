package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.controler.adapter.UserAdapter;
import com.istic.agetac.controllers.dao.UserDao;
import com.istic.agetac.controllers.listeners.users.ListenerUser;
import com.istic.agetac.model.User;

public class AddUserFragment extends Fragment implements OnDragListener{

	private ListView mListDispo;
	private ListView mListAdded;
	
	private UserAdapter mAdapterDispo;
	private UserAdapter mAdapterAdded;
	
	private List<User> mDispo;
	private List<User> mAdded;
	
	private Button mButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		
		View view = inflater.inflate(R.layout.fragment_add_user, container, false);

		mListDispo = (ListView) view.findViewById(R.id.fragment_add_user_list_user_dispo);
		mListAdded = (ListView) view.findViewById(R.id.fragment_add_user_list_to_add);
		mButton = (Button)view.findViewById(R.id.fragment_add_user_button_send);
		
		mDispo = new ArrayList<User>();
		mAdded = new ArrayList<User>();
		
		mAdapterDispo = new UserAdapter(this.getActivity(), mDispo);
		mListDispo.setAdapter(mAdapterDispo);
		
		mAdapterAdded = new UserAdapter(this.getActivity(), mAdded);
		mListAdded.setAdapter(mAdapterAdded);
		
		mListAdded.setOnDragListener(this);
		mListDispo.setOnDragListener(this);
		mListAdded.setOnItemLongClickListener(new ListenerUser(mAdapterAdded));
		mListDispo.setOnItemLongClickListener(new ListenerUser(mAdapterDispo));
		
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Send();
			}
		});
		
		UserDao user = new UserDao(new UserViewReceiver());
		user.findAll();
		return view;
	}	
	
	public void Send()
	{
		this.getActivity().finish();
	}
	
	public class UserViewReceiver implements IViewReceiver<User>
	{
		@Override
		public void notifyResponseSuccess(List<User> users) {
			
			for (User user : users) {
				if(user.getRole()!=Role.codis)
					mAdapterDispo.Add(user);
			}			
		}

		@Override
		public void notifyResponseFail(VolleyError error) {
			Toast.makeText(getActivity(), "Impossible de charger les utilisateurs", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	@Override
	public boolean onDrag(View v, DragEvent event) {
		final int action = event.getAction();
		switch(action) {
			case DragEvent.ACTION_DRAG_STARTED:
				return true;
			case DragEvent.ACTION_DRAG_ENTERED:
				return true;
			case DragEvent.ACTION_DRAG_LOCATION:
				return true;
			case DragEvent.ACTION_DRAG_EXITED:
				return true;
			case DragEvent.ACTION_DROP:
				if(v == mListAdded) {
					User u = (User) event.getLocalState();					
					mAdapterAdded.Add(u);
					return true;
				} else if(v == mListDispo){
					User u = (User) event.getLocalState();
					mAdapterDispo.Add(u);
					return true;
				}else
				{					
					return false;
				}
			case DragEvent.ACTION_DRAG_ENDED:
				if (event.getResult()) {					
					return true;
				} else {					
					if(v == mListAdded) {
						User u = (User) event.getLocalState();					
						mAdapterDispo.Add(u);
					} else if(v == mListDispo){
						User u = (User) event.getLocalState();
						mAdapterAdded.Add(u);
					}
					return false;
				}
			default :
				return false;
		}
	}

}
