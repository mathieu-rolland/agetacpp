package com.istic.agetac.controler.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.activities.UserActivity;
import com.istic.agetac.model.Intervention;

import de.greenrobot.event.EventBus;

public class InterventionAdapter extends BaseAdapter{

	private List<Intervention> mList;	
	private LayoutInflater mInflater;
	private Context mContext;
	
	public InterventionAdapter(Context context)
	{
		this.mList = new ArrayList<Intervention>();
		this.mInflater = LayoutInflater.from(context);
		this.mContext = context;
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

	public void addAll(List<Intervention> list)
	{
		for (Intervention item : list) {
			mList.add(item);
		}
		
		notifyDataSetChanged();
	}
	
	public List<Intervention> getAll()
	{
		return mList;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

        if(convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_intervention, null);  
            holder.nom = (TextView) convertView.findViewById( R.id.item_intervention_nom );
            holder.address = (TextView) convertView.findViewById( R.id.item_intervention_address );
            holder.code = (TextView) convertView.findViewById( R.id.item_intervention_code );
            holder.button = (Button) convertView.findViewById(R.id.item_intervention_image_button);
                        
            holder.nom.setText( mList.get(position).getNom() );
            holder.address.setText(mList.get(position).getAdresse());
            holder.code.setText(mList.get(position).getCodeSinistre());
            
            holder.button.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					showDialogUser(mList.get(position));
				}
			});
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        return convertView;
	}
	
	public void add(Intervention intervention) {
		mList.add(intervention);
	}

	public void clear() {
		mList.clear();
	}
	
	public class ViewHolder {		
		TextView address;
		TextView code;
		TextView nom;
		Button button;
    }
	
	public void showDialogUser(Intervention intervention)
	{
		UserActivity.launchActivity(mContext); 
		EventBus.getDefault().postSticky(intervention);
	}
}
