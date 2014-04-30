package com.istic.agetac.controler.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.controllers.listeners.demandeDeMoyens.ListenerSupressionItem;
import com.istic.agetac.fragments.DemandeDeMoyensFragment;
import com.istic.agetac.view.item.DemandeDeMoyensItem;

/**
 * class DemandeDeMoyenListAdapter : Adapter permettant de charger le layout d'un item dans la liste de demande de 
 * moyens.
 * 
 * @author Anthony LE MEE - 10003134
*/
public class DemandeDeMoyenListAdapter extends ArrayAdapter<DemandeDeMoyensItem> {
	    
	/** Attributs */
	private ArrayList<DemandeDeMoyensItem> listItems; // Liste de demande de moyens
	private DemandeDeMoyensFragment demandeDeMoyens;    
	
	/**
	 * Constructeur de la classe DemandeDeMoyenListAdapter
	 * @param f - context
	 * @param textViewResourceId - ressource item
	 * @param listItems - liste des item à afficher
	 */
	public DemandeDeMoyenListAdapter(Fragment f, int textViewResourceId, ArrayList<DemandeDeMoyensItem> listItems) {
	
		super(f.getActivity().getApplicationContext(), textViewResourceId, listItems);
		this.listItems = listItems;
		this.demandeDeMoyens = (DemandeDeMoyensFragment) f;
	    
	}// méthode
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		// ON récupère la vue affichée
		View v = convertView;
	  
		// Si la vue n'existe pas 
		if (v == null) {
	    
			// Alors on la créer via le layout maquette_item_moyens
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			v = inflater.inflate(R.layout.item_demande_de_moyens_listview, null);
	  
		}
			
		// On récupère les infos que l'on souhaite afficher
		DemandeDeMoyensItem itemMoyen = listItems.get(position);
		  
		// Si on à bien récupéré les infos
		if (itemMoyen != null && itemMoyen.getType() != null) {
	            
			// Alors on charge chacunes d'elles à leur place dans le layout
			TextView nameMoyen 			= (TextView) v.findViewById(R.id.demande_de_moyen_item_TextView_name);
			TextView quantityMoyen 		= (TextView) v.findViewById(R.id.demande_de_moyen_item_TextView_Quantity);
	        RelativeLayout layoutItem 	= (RelativeLayout) v.findViewById(R.id.demande_de_moyen_ListView_relativelayout);
	        Button buttonDeleteItem 	= (Button) v.findViewById(R.id.demande_de_moyen_item_Button_Delete_icon);
	        
	        // chargement du nom du moyen
	        nameMoyen.setText(itemMoyen.getType().toString());
	        
	        // chargement du nombre de moyen de ce type voulu
	        quantityMoyen.setText("" + String.valueOf(itemMoyen.getNombre()) + "");
	        
	        // Ajout du listener de supression au boutton corbeille
	        buttonDeleteItem.setOnClickListener(new ListenerSupressionItem(itemMoyen, this.demandeDeMoyens));
	  
		}// if

		return v;
	    
	}// méthode
	
}// classe
