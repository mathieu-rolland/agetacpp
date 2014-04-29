package com.istic.agetac.controllers.listeners.users;

import java.util.List;

import android.view.View;
import android.view.View.DragShadowBuilder;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.istic.agetac.controler.adapter.UserAdapter;
import com.istic.agetac.model.User;

public class ListenerUser implements OnItemLongClickListener{

	private UserAdapter mAdapter;
	
	public ListenerUser( UserAdapter adapter)
	{
		this.mAdapter = adapter;
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		User selectedUser = (User) mAdapter.getItem(position);		
			DragShadowBuilder productShadow = new DragShadowBuilder(view);
	
			view.startDrag(null,	//ClipData
	                productShadow,		//View.DragShadowBuilder 
	                selectedUser,		//Object myLocalState
	                0);	
			
			mAdapter.Remove(selectedUser);
			
			return true;
		
	}
}
