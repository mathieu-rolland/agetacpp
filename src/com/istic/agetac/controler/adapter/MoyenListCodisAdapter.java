package com.istic.agetac.controler.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.istic.agetac.R;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controllers.listeners.tableauMoyen.ListenerEngage;
import com.istic.agetac.model.Moyen;

import android.content.Context;
import android.opengl.Visibility;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MoyenListCodisAdapter extends AMoyenListAdapter {
	
	public MoyenListCodisAdapter(Context context,List<Moyen> moyens) {
		super(context, moyens);       
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
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
        
        if(current.getmHourDemand()!=null)
        {
        	holder.hourDemand.setText(mFormater.format(current.getmHourDemand()));  
        	holder.hourDemand.setVisibility(TextView.VISIBLE);
        	holder.buttonDemand.setOnClickListener(new ListenerEngage(current,this));
        }
        
        if(current.getmHourEngagement()!=null)        
        {
        	holder.hourEngage.setText(mFormater.format(current.getmHourEngagement())); 
        	holder.hourEngage.setVisibility(TextView.VISIBLE);
        	holder.buttonDemand.setVisibility(Button.GONE);
//        	ViewGroup.LayoutParams params = holder.hourDemand.getLayoutParams();
//        	params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        	holder.hourDemand.setLayoutParams(params);
//        	holder.hourDemand.setGravity(Gravity.CENTER);
//        	holder.hourDemand.setTextSize(18);
        	holder.name.setText(current.getLibelle());
        }
        
        if(current.getmHourArrivedOnSite()!=null)
        {
        	holder.hourArrived.setText(mFormater.format(current.getmHourArrivedOnSite()));
        	holder.buttonSector.setVisibility(Button.GONE);
//        	holder.sector.setText(current.getmSector());
//        	holder.sector.setVisibility(TextView.VISIBLE);
        }
        
        if(current.getmHourFree()!=null)
        {
        	holder.hourFree.setText(mFormater.format(current.getmHourFree()));
        }
        
        return convertView;
	}
	
	public void setEngage(Moyen item, Date dateEngage) {
		item.setmHourEngagement(dateEngage);
		this.notifyDataSetChanged();
	}
}
