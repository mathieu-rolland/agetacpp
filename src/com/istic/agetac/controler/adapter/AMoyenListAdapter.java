package com.istic.agetac.controler.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import com.istic.agetac.model.Moyen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public abstract class AMoyenListAdapter extends BaseAdapter {

	protected List<Moyen> mList;
	protected LayoutInflater mInflater;
	protected final static SimpleDateFormat  mFormater = new SimpleDateFormat("ddMM '-' hhmm");
	
	public AMoyenListAdapter(Context context,List<Moyen> moyens) {
        this.mInflater = LayoutInflater.from(context);
        this.mList = moyens;
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
	public abstract View getView(int position, View convertView, ViewGroup parent);
	
	protected class ViewHolder {
		ImageView logo;
		TextView hourDemand;
		Button buttonDemand;
        TextView hourEngage;
        TextView hourArrived;
        TextView hourFree;
        Button buttonFree;
        Spinner buttonSector;
        TextView name;
    }
}
