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
import com.istic.agetac.controllers.listeners.tableauMoyen.ListenerEngage;
import com.istic.agetac.model.Moyen;

public class MoyenListCodisAdapter extends AMoyenListAdapter {
	
	public MoyenListCodisAdapter(Context context,List<Moyen> moyens) {
		super(context, moyens); 
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
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
            holder.buttonSector =(Spinner) convertView.findViewById( R.id.list_moyen_button_sector);
            holder.buttonDemand = (Button)convertView.findViewById(R.id.list_moyen_button_engage);
            holder.name =(TextView)convertView.findViewById(R.id.list_moyen_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.buttonFree.setVisibility(Button.GONE);
        holder.buttonSector.setVisibility(Button.GONE);
        holder.logo.setImageResource(R.drawable.fpt_1_alim);
        
        if(current.getLibelle()!="")
        {
        	holder.name.setText(current.getLibelle());
        }
        
        if(current.getHDemande()!=null)
        {
        	holder.hourDemand.setText(current.getHDemande());  
        	holder.hourDemand.setVisibility(TextView.VISIBLE);
        	holder.buttonDemand.setOnClickListener(new ListenerEngage(current,this));
        }
        
        if(current.getHEngagement()!=null)        
        {
        	holder.hourEngage.setText(current.getHEngagement()); 
        	holder.hourEngage.setVisibility(TextView.VISIBLE);
        	holder.buttonDemand.setVisibility(Button.GONE);
//        	ViewGroup.LayoutParams params = holder.hourDemand.getLayoutParams();
//        	params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        	holder.hourDemand.setLayoutParams(params);
//        	holder.hourDemand.setGravity(Gravity.CENTER);
//        	holder.hourDemand.setTextSize(18);
        	holder.name.setText(current.getLibelle());
        }
        
        if(current.getHArrival()!=null)
        {
        	holder.hourArrived.setText(current.getHArrival());
        	holder.buttonSector.setVisibility(Button.GONE);
//        	holder.sector.setText(current.getmSector());
//        	holder.sector.setVisibility(TextView.VISIBLE);
        }
        
        if(current.getHFree()!=null)
        {
        	holder.hourFree.setText(current.getHFree());
        }
        
        return convertView;
	}
	
	public void setEngage(Moyen item, Date dateEngage) {
		item.setHEngagement(dateEngage);
		this.notifyDataSetChanged();
	}
}
