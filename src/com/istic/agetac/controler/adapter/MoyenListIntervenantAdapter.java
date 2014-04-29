package com.istic.agetac.controler.adapter;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.controllers.listeners.tableauMoyen.ListenerFree;
import com.istic.agetac.model.Moyen;

public class MoyenListIntervenantAdapter extends AMoyenListAdapter {

	public MoyenListIntervenantAdapter(Context context, List<Moyen> moyens) {
		super(context, moyens);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		Date aujourdhui = new Date();
		Moyen current = mList.get(position);
		
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_moyen, null);
            
            holder.logo =(ImageView) convertView.findViewById( R.id.list_moyen_logo );
            holder.hourDemand =(TextView) convertView.findViewById( R.id.list_moyen_hour_demand );
            holder.hourEngage =(TextView) convertView.findViewById( R.id.list_moyen_hour_engage );
            holder.hourArrived =(TextView) convertView.findViewById( R.id.list_moyen_hour_arrive );
            holder.hourFree =(TextView) convertView.findViewById( R.id.list_moyen_hour_free );
            holder.buttonFree = (Button) convertView.findViewById(R.id.list_moyen_button_free);
            holder.buttonFree.setOnClickListener(new ListenerFree(current, this));
            holder.buttonSector =(Spinner) convertView.findViewById( R.id.list_moyen_button_sector );
            holder.buttonDemand = (Button)convertView.findViewById(R.id.list_moyen_button_engage);
            holder.name =(TextView)convertView.findViewById(R.id.list_moyen_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.buttonFree.setVisibility(Button.GONE);
        holder.buttonSector.setVisibility(Button.GONE);
        holder.buttonDemand.setVisibility(Button.GONE);        
        
        holder.logo.setImageResource(R.drawable.fpt_1_alim);
               
        if(current.getHDemande()!=null)
        {
        	holder.hourDemand.setText(current.getHDemande());
        	if(current.getHEngagement()==null)
        	{
        		holder.hourEngage.setText("En attente du codis");
        		holder.hourEngage.setVisibility(TextView.VISIBLE);
        	}
        }
        
        if(current.getHEngagement()!=null)        
        {
        	holder.hourEngage.setText(current.getHEngagement());
        	holder.hourEngage.setVisibility(TextView.VISIBLE);
        	if(current.getSecteur()==null)
        	{
        		holder.buttonSector.setVisibility(Button.VISIBLE);
        	}

        	holder.name.setText(current.getLibelle());
        }
        
        if(current.getHArrival()!=null)
        {
        	holder.hourArrived.setText(current.getHArrival());
        	holder.hourArrived.setVisibility(TextView.VISIBLE);
        }
        
        if(current.getSecteur()!=null)
        {
        	holder.buttonFree.setVisibility(Button.VISIBLE);
        }
        
        if(current.getHFree()!=null)
        {
        	holder.hourFree.setVisibility(View.VISIBLE);
        	holder.buttonFree.setVisibility(View.GONE);
        	holder.hourFree.setText(current.getHFree());        	
        }
        
        return convertView;
	}

	public void setFree(Moyen mItemMoyen, Date date) {
		mItemMoyen.setHFree(date);
		this.notifyDataSetChanged();
	}

}
