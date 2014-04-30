package com.istic.agetac.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.istic.agetac.controler.adapter.UserAdapter;
import com.istic.agetac.controllers.dao.ADao;
import com.istic.agetac.controllers.listeners.users.ListenerUser;
import com.istic.agetac.model.Intervenant;
import com.istic.agetac.model.Intervention;

import de.greenrobot.event.EventBus;

public class AddUserFragment extends Fragment implements OnDragListener{

	private ListView mListDispo;
	private ListView mListAdded;
	
	private UserAdapter mAdapterDispo;
	private UserAdapter mAdapterAdded;
		
	private Button mButton;
	private Intervention mIntervention;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		Log.e("VINCENT", "LALALA");
		View view = inflater.inflate(R.layout.fragment_add_user, container, false);

		mListDispo = (ListView) view.findViewById(R.id.fragment_add_user_list_user_dispo);
		mListAdded = (ListView) view.findViewById(R.id.fragment_add_user_list_to_add);
		mButton = (Button)view.findViewById(R.id.fragment_add_user_button_send);
		
		mIntervention = EventBus.getDefault().removeStickyEvent(Intervention.class);
		
//		mIntervention.getIntervenants(new Observer() {			
//			@Override
//			public void update(Subject subject) {
//				Intervention intervention = (Intervention) subject;	
//				Log.e("Vincent", intervention.getIntervenants().size()+"");
//				mAdapterAdded.addAll(intervention.getIntervenants());			
//			}
//		});
			
		
		mAdapterAdded = new UserAdapter(getActivity());
		mAdapterAdded.addAll(mIntervention.getIntervenants());
		mListAdded.setAdapter(mAdapterAdded);
		
		mAdapterDispo = new UserAdapter(this.getActivity());
		mListDispo.setAdapter(mAdapterDispo);
				
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
				
		ADao<Intervenant> intervenantdao = new ADao<Intervenant>(new IntervenantViewReceiver());
		intervenantdao.executeFindAll(Intervenant.class);
		
		return view;
	}	
	
	public class IntervenantViewReceiver implements IViewReceiver<Intervenant>
	{
		@Override
		public void notifyResponseSuccess(List<Intervenant> intervenants) {
			for (Intervenant intervenant : intervenants) {
				if(intervenant.getIntervention()==null)
				{
					mAdapterDispo.add(intervenant);			
				}
			}
			mAdapterDispo.notifyDataSetChanged();
		}

		@Override
		public void notifyResponseFail(VolleyError error) {
			Toast.makeText(getActivity(), "Impossible de charger les utilisateurs disponibles", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public void Send()
	{
		mIntervention.getIntervenants().clear();
		
		for (Intervenant item : mAdapterAdded.getAll()) {
			item.setIntervention(mIntervention);
			item.save();
			mIntervention.addIntervenant(item);
		}
		
		for (Intervenant item : mAdapterDispo.getAll()) {
			item.setIntervention(null);
			item.save();
		}
		
		mIntervention.save();
		
		this.getActivity().finish();
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
					Intervenant u = (Intervenant) event.getLocalState();					
					mAdapterAdded.add(u);
					return true;
				} else if(v == mListDispo){
					Intervenant u = (Intervenant) event.getLocalState();
					mAdapterDispo.add(u);
					return true;
				}else
				{					
					return false;
				}
			case DragEvent.ACTION_DRAG_ENDED:
				if (event.getResult()) {					
					return true;
				} else {					
					Intervenant u = (Intervenant) event.getLocalState();
					if(v == mListAdded) {											
						mAdapterDispo.add(u);
					} else if(v == mListDispo){						
						mAdapterAdded.add(u);
					}
					return false;
				}
			default :
				return false;
		}
	}

}
