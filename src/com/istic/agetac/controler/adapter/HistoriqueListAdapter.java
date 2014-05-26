package com.istic.agetac.controler.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.fragments.HistoriqueFragment;
import com.istic.agetac.model.Action;
import com.istic.agetac.model.Moyen;

	/**
	 * class HistoriqueListAdapter : Adapter permettant de charger le layout d'un item dans la liste des actions.
	 * 
	 * @author Marion GUILLEMIN - 29001737
	*/
	public class HistoriqueListAdapter extends ArrayAdapter<Action> {
		    
		/** Attributs */
		private ArrayList<Action> listItems; // Liste des actions
		private HistoriqueFragment historique;    
		
		/**
		 * Constructeur de la classe HistoriqueListAdapter
		 * @param f - context
		 * @param textViewResourceId - ressource item
		 * @param listItems - liste des item � afficher
		 */
		public HistoriqueListAdapter(Fragment f, int textViewResourceId, ArrayList<Action> listItems) {
		
			super(f.getActivity().getApplicationContext(), textViewResourceId, listItems);
			this.listItems = listItems;
			this.historique = (HistoriqueFragment) f;
		    
		}// m�thode
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		
			// ON récupère la vue affichée
			View v = convertView;
		  
			// Si la vue n'existe pas 
			if (v == null) {
		    
				// Alors on la cr�er via le layout maquette_item_moyens
				LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
				v = inflater.inflate(R.layout.item_historique_listview, null);
		  
			}
				
			// On r�cup�re les infos que l'on souhaite afficher
			Action itemAction = listItems.get(position);
			  
			// Si on � bien r�cup�r� les infos
			if (itemAction != null && itemAction.getUser() != null) {
		            
				// Alors on charge chacunes d'elles � leur place dans le layout
				TextView userName 			= (TextView) v.findViewById(R.id.historique_item_TextView_Username);
				TextView date 		= (TextView) v.findViewById(R.id.historique_item_TextView_Date);
				TextView action 		= (TextView) v.findViewById(R.id.historique_item_TextView_Action);
				LinearLayout layoutItem 	= (LinearLayout) v.findViewById(R.id.historique_ListView_linearlayout);
		        
		        // chargement du nom de l'utilisateur
		        userName.setText(itemAction.getUser());
		        
		        // chargement de la date
		        date.setText(Moyen.FORMATER.format(itemAction.getDateAction()));
		        
		        // chargement de la nature de l'action
		        action.setText(itemAction.getNatureAction());
			}// if

			return v;
		    
		} // methode
		
	} // classe
	