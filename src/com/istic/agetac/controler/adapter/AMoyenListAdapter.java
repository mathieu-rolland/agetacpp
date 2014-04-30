package com.istic.agetac.controler.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;

public abstract class AMoyenListAdapter extends BaseAdapter {

	protected List<Moyen> mList;
	protected LayoutInflater mInflater;
	
	/* Donnï¿½es rï¿½cupï¿½rï¿½es */
	protected List<Secteur> datasListSecteur;
	
	public AMoyenListAdapter(Context context) {
		this.mInflater= (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.mList = new ArrayList<Moyen>();
        this.datasListSecteur = new ArrayList<Secteur>();
    }
	
	public void addAllSecteurs( List<Secteur> secteurs ){
		for( Secteur secteur : secteurs ){
			this.datasListSecteur.add(secteur);
		}
		secteurDataChanged();
	}
	
	public abstract void secteurDataChanged( );
	
	@Override
	public int getCount() {
		return mList.size();
	}
	
	public void addAll(List<Moyen> moyens)
	{
		
		for (Moyen moyen : moyens) {
			if(!mList.contains(moyen))
			{
				mList.add(moyen);
			}
			else
			{
				Log.e("Vincent", "moyen PAS ajouté a ladapteur" );
			}
		}
		Log.e("Vincent", "AmoyenlistAdapter notify" );
		notifyDataSetChanged();
	}
	
	public List<Moyen> getAll()
	{
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
	public abstract View getView(int position, View convertView, ViewGroup parent);
	
	protected class ViewHolder {
		ImageView logo;
		TextView hourDemand;
		Button buttonDemand;
        TextView hourEngage;
        TextView hourArrived;
        TextView hourFree;
        Button buttonFree;
        Spinner spinnerChoixSecteurs;
        TextView name;
    }
	
	public static boolean isNullOrBlank(String param) {
        if (isNull(param) || param.trim().length() == 0) {
            return true;
        }
        return false;
    }
	
	public static boolean isNull(String str) {
        return str == null ? true : false;
    }
}
