package com.istic.agetac.controler.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.model.Intervenant;
import com.istic.agetac.model.User;

public class UserAdapter extends BaseAdapter {

	private List<Intervenant> mList;
	private LayoutInflater mInflater;

	public UserAdapter(Context context) {
		this.mList = new ArrayList<Intervenant>();
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public List<Intervenant> getAll()
	{
		return mList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_user, null);
			holder.name = (TextView) convertView.findViewById(R.id.item_user_name);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.name.setText(mList.get(position).getName());

		return convertView;
	}

	public class ViewHolder {
		TextView name;
	}

	public void add(Intervenant u) {
		if (!contains(u)) {
			mList.add(u);
			notifyDataSetChanged();
		}
	}
	
	public void addAll(List<Intervenant> list)
	{
		for (Intervenant item : list) {
			mList.add(item);
		}
		
		notifyDataSetChanged();
	}

	public void remove(Intervenant u) {
		if (contains(u)) {
			mList.remove(u);
			notifyDataSetChanged();
		}
	}

	public boolean contains(User u) {
		return mList.contains(u);
	}

}
