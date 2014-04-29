package com.istic.agetac.controler.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.model.User;

public class UserAdapter extends BaseAdapter {

	private List<User> mList;
	private LayoutInflater mInflater;

	public UserAdapter(Context context, List<User> users) {
		this.mList = users;
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

	public void Add(User u) {
		if (!Contains(u)) {
			mList.add(u);
			notifyDataSetChanged();
		}
	}

	public void Remove(User u) {
		if (Contains(u)) {
			mList.remove(u);
			notifyDataSetChanged();
		}
	}

	public boolean Contains(User u) {
		return mList.contains(u);
	}
}
