package com.istic.agetac.controler.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;
import com.istic.agetac.widget.SpinnerWithTextInit;

public abstract class AMoyenListAdapter extends BaseAdapter {

	protected List<Moyen> mList;
	protected HashMap<String,Secteur> mSector;
	protected List<String> mSectorString;
	protected LayoutInflater mInflater;   
	protected static String mWaitingText= "En attente";
	
	public AMoyenListAdapter(Context context) {
		this.mInflater= (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.mList = new ArrayList<Moyen>();

        this.mSector = new HashMap<String, Secteur>();
        this.mSectorString = new ArrayList<String>();
    }
		
	@Override
	public int getCount() {
		return mList.size();
	}

	public void addAll(List<Moyen> moyens) {

		for (Moyen moyen : moyens) {
			if(!mList.contains(moyen))
			{
				mList.add(moyen);
			}
			
		}
		notifyDataSetChanged();
	}

	public List<Moyen> getAll() {
		return mList;
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
	public abstract View getView(int position, View convertView,
			ViewGroup parent);

	protected class ViewHolder {
		ImageView logo;
		TextView hourDemand;
		Button buttonDemand;
		TextView hourEngage;
		TextView hourArrived;
		TextView hourFree;
		Button buttonFree;
		SpinnerWithTextInit spinner;
		TextView sector;
		TextView name;
	}

	public static boolean isNullOrBlank(String param) {
		if (isNull(param) || param.trim().length() == 0 ||  param.equals( mWaitingText )) {
			return true;
		}
		return false;
	}

	public static boolean isNull(String str) {
		return str == null ? true : false;
	}

    public void setSectorAvailable( List<Secteur> sectors )
    {
        for ( Secteur secteur : sectors )
        {
            mSector.put( secteur.getName(), secteur );
        }
        
        mSectorString = new ArrayList<String>(mSector.keySet());
    }
    
}
