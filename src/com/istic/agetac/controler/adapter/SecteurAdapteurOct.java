package com.istic.agetac.controler.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.model.Secteur;

public class SecteurAdapteurOct extends BaseAdapter {

	private List<Secteur> secteurs;
	private LayoutInflater inflater;
	
	public SecteurAdapteurOct( Context context ){
		this.secteurs = new ArrayList<Secteur>();
		this.inflater = LayoutInflater.from(context);
	}
	
	public SecteurAdapteurOct( Context context, List<Secteur> secteurs ){
		this.secteurs = secteurs;
		this.inflater = LayoutInflater.from(context);
	}
	
	public void addSecteur( Secteur secteur ){
		secteurs.add(secteur);
	}
	
	public void addAll( List<Secteur> secteurs ){
		this.secteurs.addAll(secteurs);
	}
	
	public void remove( Secteur secteur ){
		secteurs.remove(secteur);
	}
	
	public boolean isEmpty(){
		return secteurs.isEmpty();
	}
	
	@Override
	public int getCount() {
		return secteurs.size();
	}

	@Override
	public Object getItem(int arg0) {
		return secteurs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		
		Secteur secteur = secteurs.get(position);
		
		if( view == null ){
			view = inflater.inflate(R.layout.item_secteur_oct,  null );
		}
		
		TextView txt = (TextView) view.findViewById(R.id.item_secteur_oct_label);
		txt.setText( secteur.getName() );
		
		return view;
	}
	
}
