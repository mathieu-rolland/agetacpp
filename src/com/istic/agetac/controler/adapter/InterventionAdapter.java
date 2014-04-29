package com.istic.agetac.controler.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.model.Intervention;

public class InterventionAdapter extends BaseAdapter{

	private List<Intervention> mList;	
	private LayoutInflater mInflater;
	
	public InterventionAdapter(Context context, List<Intervention> intervention)
	{
		this.mList = intervention;
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

        if(convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_intervention, null);  
            holder.address = (TextView) convertView.findViewById( R.id.item_intervention_address );
            holder.code = (TextView) convertView.findViewById( R.id.item_intervention_code );
            
            holder.address.setText(mList.get(position).getAdresse());
            holder.code.setText(mList.get(position).getCodeSinistre());
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        return convertView;
	}

	public class ViewHolder {		
		TextView address;
		TextView code;
    }
}
