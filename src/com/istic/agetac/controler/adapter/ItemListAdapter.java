package com.istic.agetac.controler.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.istic.agetac.api.view.ItemView;

public class ItemListAdapter<T> extends BaseAdapter {

	private List<ItemView<T>> items;
	private Context context;
	
	public ItemListAdapter( Context context ){
		this.items = new ArrayList<ItemView<T>>();
		this.context = context;
	}
	
	public ItemListAdapter( Context context, List<ItemView<T>> items ){
		this.items = items;
		this.context = context;
	}
	
	public List<ItemView<T>> getItems(){
		return items;
	}
	
	public void addItem( ItemView<T> item ){
		this.items.add(item);
	}
	
	public void removeItem( ItemView<T> item ){
		this.items.remove(item);
	}
	
	public void addLast( ItemView<T> item ){
		this.items.add(getCount(), item);
	}
	
	public void addFirst( ItemView<T> item ){
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
		ItemView<T> item = (ItemView<T>) items.get(position);
		
		return item.getView(context, view, root);
		
//		View convertView = LayoutInflater.from(context).inflate(R.layout.item_message_view, null);
//        convertView.findViewById( R.id.list_moyen_logo );
//		return convertView;
		
	}

}
