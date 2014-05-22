package com.istic.agetac.controler.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.controllers.listeners.constitutionGroupCrm.ListenerAddMoyen;
import com.istic.agetac.fragments.ConstitutionGroupCrmFragment;
import com.istic.agetac.model.Moyen;

/**
 * class ConstitutionGroupCrmListAdapter : Adapter permettant de charger le layout d'un item dans la liste de constitution
 * des groupes au CRM.
 * 
 * @author Anthony LE MEE - 10003134
*/
public class ConstitutionGroupCrmListAdapter extends ArrayAdapter<Moyen> {
	    
	/** Attributs */
	private List<Moyen> listItems; // Liste des moyens au CRM
	private ConstitutionGroupCrmFragment constitutionGroupCrm;    
	
	/**
	 * Constructeur de la classe ConstitutionGroupCrmListAdapter
	 * @param f - context
	 * @param textViewResourceId - ressource item
	 * @param mListMoyen - liste des item à afficher
	 */
	public ConstitutionGroupCrmListAdapter(ConstitutionGroupCrmFragment f, int textViewResourceId, List<Moyen> mListMoyen) {
	
		super(f.getActivity().getApplicationContext(), textViewResourceId, mListMoyen);
		this.listItems 				= mListMoyen;
		this.constitutionGroupCrm 	= f;
	    
	}// méthode
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		// ON récupère la vue affich�e
		View v = convertView;
	  
		// Si la vue n'existe pas 
		if (v == null) {
	    
			// Alors on la créer via le layout maquette_item_moyens
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			v = inflater.inflate(R.layout.item_constitution_groupe_crm_listview, null);
	  
		}
			
		// On récupére les infos que l'on souhaite afficher
		Moyen itemMoyen = listItems.get(position);
		  
		// Si on à bien récupèré les infos
		if (itemMoyen != null && itemMoyen.getType() != null) {
	            
			// Alors on charge chacunes d'elles à leur place dans le layout
			TextView nameMoyen 		= (TextView) v.findViewById(R.id.consitution_group_crm_item_TextView_nameMoyen);
			ImageView typeMoyen 	= (ImageView) v.findViewById(R.id.consitution_group_crm_item_TextView_typeMoyen);
	        Button buttonAssign 	= (Button) v.findViewById(R.id.consitution_group_crm_item_Button_assignToGroup);
	        
	        // chargement du nom du moyen pouvant être assigné
	        nameMoyen.setText(itemMoyen.getLibelle());
	        
	        // chargement du type de moyen pouvant être assigné
	        typeMoyen.setImageDrawable(getContext().getResources().getDrawable(itemMoyen.getType().getRepresentationOK().getDrawable()));
	        
	        // Ajout du listener afin d'assigner le moyen à un group	  
	        buttonAssign.setOnClickListener(new ListenerAddMoyen(itemMoyen, this.constitutionGroupCrm));
	        
		}// if

		return v;
	    
	}// méthode
	
}// classe
