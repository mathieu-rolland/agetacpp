package com.istic.agetac.controler.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.model.Secteur;

public class SpinnerSectorAdapter extends ArrayAdapter<String>
{
    private List<String> items;
    private HashMap<String, Secteur> mMap;
    private LayoutInflater mInflater;

    public SpinnerSectorAdapter( Context ctx, int txtViewResourceId, HashMap<String, Secteur> mapSecteur, LayoutInflater inflater )
    {
        super( ctx, txtViewResourceId, mapSecteur.keySet().toArray(new String[mapSecteur.keySet().size()]) );
       mInflater = inflater;
        items = new ArrayList<String>(mapSecteur.keySet());
        mMap = mapSecteur;
    }

    @Override
    public View getDropDownView( int position, View cnvtView, ViewGroup prnt )
    {
        return getCustomView( position, cnvtView, prnt );
    }

    @Override
    public View getView( int pos, View cnvtView, ViewGroup prnt )
    {
        return getCustomView( pos, cnvtView, prnt );
    }

    public View getCustomView( int position, View convertView, ViewGroup parent )
    {        
        View mySpinner = mInflater.inflate( R.layout.item_sector_spinner, parent, false );
        TextView text = (TextView) mySpinner.findViewById( R.id.item_sector_name );
        text.setText( items.get( position ) );

        Secteur secteur = mMap.get( items.get( position ) );
        String color = secteur.getColor();
        text.setTextColor( Color.parseColor( color ) );
        return mySpinner;
    }
}