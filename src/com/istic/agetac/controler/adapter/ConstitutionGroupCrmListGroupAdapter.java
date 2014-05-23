package com.istic.agetac.controler.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.controllers.listeners.constitutionGroupCrm.ListenerCollapse;
import com.istic.agetac.fragments.ConstitutionGroupCrmFragment;
import com.istic.agetac.model.Groupe;
import com.istic.agetac.model.Moyen;

/**
 * class ConstitutionGroupCrmListGroupAdapter : 
 * @author Anthony LE MEE - 10003134
*/
public class ConstitutionGroupCrmListGroupAdapter extends ArrayAdapter<IMoyen> {
	    
	/** Attributs */
	private List<IMoyen> listItems; // Liste des moyens au CRM regroupé
	private ConstitutionGroupCrmFragment constitutionGroupCrm;    
	
	/**
	 * Constructeur de la classe ConstitutionGroupCrmListAdapter
	 * @param f - context
	 * @param textViewResourceId - ressource item
	 * @param moyenListItem - liste des item à afficher
	 */
	public ConstitutionGroupCrmListGroupAdapter(ConstitutionGroupCrmFragment f, int textViewResourceId, List<IMoyen> moyenListItem) {
	
		super(f.getActivity().getApplicationContext(), textViewResourceId, moyenListItem);
		this.listItems 				= moyenListItem;
		this.constitutionGroupCrm 	= f;
	    
	}// méthode
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		// ON récupère la vue affichée
		View v = convertView;
	  
		// On récupére les infos que l'on souhaite afficher
				IMoyen itemMoyen = listItems.get(position);
		
		// Si la vue n'existe pas 
		if (v == null) {
	    
			// Alors on la créer via le layout maquette_item_moyens
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			if (itemMoyen != null && itemMoyen.isGroup()) {
				v = inflater.inflate(R.layout.item_constitution_groupe_crm_listview_group, null);
			}
			else
			{
				v = inflater.inflate(R.layout.item_constitution_groupe_crm_listview_moyen, null);
			}
	  
		}
			
		
		  
		// Si on à bien récupèré les infos
		if (itemMoyen != null) {
	            
			if (itemMoyen.isGroup()) {
				
				// Création de la vue pour un group
				
				TextView groupName		= (TextView) v.findViewById(R.id.consitution_group_crm_item_listViewGroup_Group_TextView_nameMoyen);
		        ImageButton collapse 	= (ImageButton) v.findViewById(R.id.consitution_group_crm_item_listViewGroup_Group_Button_assignToGroup);
		        
		        groupName.setText(((Groupe)itemMoyen).getNom());
				// Ajout du listener de collapse du group au click
		        collapse.setOnClickListener(new ListenerCollapse(position, ((Groupe)itemMoyen).getMoyens().size(), (ListView)this.constitutionGroupCrm.getActivity().findViewById(R.id.consitution_group_crm_listGroup)));
			}
			else
			{
				// Création de la vue pour un group
				TextView groupName		= (TextView) v.findViewById(R.id.consitution_group_crm_item_listViewGroup_Moyen_TextView_nameMoyen);
				groupName.setText(((Moyen)itemMoyen).getLibelle());
				// Ajout du listener demande de suppression au long click
			}
			
		}// if

		return v;
	    
	}// méthode
	
}// classe
