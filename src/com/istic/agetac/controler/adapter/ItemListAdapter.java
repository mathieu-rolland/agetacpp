package com.istic.agetac.controler.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.api.view.ItemView;

public class ItemListAdapter extends BaseAdapter {

	private ArrayList<ItemView> items;
	private Context context;
	
	public ItemListAdapter( Context context ){
		this.items = new ArrayList<ItemView>();
		this.context = context;
	}
	
	public ArrayList<ItemView> getItems(){
		return items;
	}
	
	public void addItem( ItemView item ){
		this.items.add(item);
	}
	
	public void removeItem( ItemView item ){
		this.items.remove(item);
	}
	
	public void addLast( ItemView item ){
		this.items.add(getCount(), item);
	}
	
	public void addFirst( ItemView item ){
		this.items.add(0, item);
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getId();
	}

	@Override
	public View getView(int position, View view, ViewGroup root) {
		ItemView item = items.get(position);
		
		// FIXME NullPointerException
		// return item.getView(context, view, root);
		
		View convertView = LayoutInflater.from(context).inflate(R.layout.item_message_view, null);
        convertView.findViewById( R.id.list_moyen_logo );
		return convertView;
		
	}

}
