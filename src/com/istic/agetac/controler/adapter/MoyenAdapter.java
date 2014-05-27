package com.istic.agetac.controler.adapter;

import java.util.ArrayList;
import java.util.List;

import com.istic.agetac.R;
import com.istic.agetac.activities.UserActivity;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.controler.adapter.InterventionAdapter.ViewHolder;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;

import de.greenrobot.event.EventBus;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MoyenAdapter extends BaseAdapter {

	private List<IMoyen> mListMoyen = new ArrayList<IMoyen>();
	private LayoutInflater mInflater;
	
	public MoyenAdapter(Context context, List<IMoyen> lMoyen)
	{
		this.mListMoyen = lMoyen;
		this.mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return mListMoyen.size();
	}

	@Override
	public Object getItem(int position) {
		return mListMoyen.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addAll(List<Moyen> list)
	{
		for (Moyen item : list) {
			mListMoyen.add(item);
		}
		
		notifyDataSetChanged();
	}
	
	public List<IMoyen> getAll()
	{
		return mListMoyen;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		IMoyen moyen = mListMoyen.get(position);

        convertView = mInflater.inflate(R.layout.item_moyen_oct, null);  
                   
        TextView txt = (TextView) convertView.findViewById(R.id.list_moyen_oct_name);
		txt.setText( moyen.getLibelle());
		
        return convertView;
	}
	
	public void add(Moyen moyen) {
		mListMoyen.add(moyen);
	}

	public void clear() {
		mListMoyen.clear();
	}
	

}
