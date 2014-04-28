package com.istic.agetac.controler.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.controler.adapter.AMoyenListAdapter.ViewHolder;
import com.istic.agetac.controllers.listeners.tableauMoyen.ListenerEngage;
import com.istic.agetac.model.Moyen;

public class MoyenListIntervenantAdapter extends AMoyenListAdapter {

	public MoyenListIntervenantAdapter(Context context, List<Moyen> moyens) {
		super(context, moyens);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		SimpleDateFormat  formater = new SimpleDateFormat("ddMM '-' hhmm");
		Date aujourdhui = new Date();
		Moyen current = mList.get(position);
		
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_moyen, null);
            
            holder.logo =(ImageView) convertView.findViewById( R.id.list_moyen_logo );
            holder.hourDemand =(TextView) convertView.findViewById( R.id.list_moyen_hour_demand );
            holder.hourEngage =(TextView) convertView.findViewById( R.id.list_moyen_hour_engage );
            holder.hourArrived =(TextView) convertView.findViewById( R.id.list_moyen_hour_arrive );
            holder.hourFree =(TextView) convertView.findViewById( R.id.list_moyen_hour_free );
            holder.sector =(TextView) convertView.findViewById( R.id.list_moyen_sector );
            holder.buttonDemand = (Button)convertView.findViewById(R.id.list_moyen_button_engage);
            holder.name =(TextView)convertView.findViewById(R.id.list_moyen_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        
        holder.logo.setImageResource(R.drawable.fpt_1_alim);
        holder.hourDemand.setText(formater.format(current.getmHourDemand()));
        
        if(current.getmHourDemand()!=null)
        {
        	holder.hourDemand.setText(formater.format(current.getmHourDemand()));      
        	
        }
        if(current.getmHourEngagement()!=null)        
        {
        	holder.hourEngage.setText(formater.format(current.getmHourEngagement())); 
        	holder.buttonDemand.setVisibility(Button.GONE);
        	ViewGroup.LayoutParams params = holder.hourDemand.getLayoutParams();
        	params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        	holder.hourDemand.setLayoutParams(params);
        	holder.hourDemand.setGravity(Gravity.CENTER);
        	holder.hourDemand.setTextSize(18);
        	holder.name.setText(current.getLibelle());
        }
        
        if(current.getmHourArrivedOnSite()!=null)
        {
        	holder.hourArrived.setText(formater.format(current.getmHourArrivedOnSite()));
        }
        
        if(current.getmHourFree()!=null)
        {
        	holder.hourFree.setText(formater.format(current.getmHourFree()));
        }
        
        return convertView;
	}

}
