package com.istic.agetac.controllers.listeners.constitutionGroupCrm;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.fragments.ConstitutionGroupCrmFragment;
import com.istic.agetac.model.Groupe;
import com.istic.agetac.model.Moyen;

/**
* Classe AddToList : Listener 
*
* @author Anthony LE MEE - 10003134
*/
public class ListenerAddMoyen implements OnClickListener{

	/** Attributs */
	private Moyen itemMoyen; 							// Instance de l'item é traiter
	private ConstitutionGroupCrmFragment vue; 			// Instance de la vue 
	private Groupe groupSelected;
	
	/**
	 * Contructeur de ListenerAddMoyen
	 * @param user 
	 * @param ConstitutionGroupCrmFragment 
	 */
	public ListenerAddMoyen(Moyen moyen, ConstitutionGroupCrmFragment constitutionGroupCrm) {
		
		this.itemMoyen = moyen;
		this.vue = constitutionGroupCrm;
		
	} // méthode

	/**
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		 
		// Création de l'AlertDialog
		AlertDialog.Builder adb = new AlertDialog.Builder(this.vue.getView().getContext());
        final AlertDialog alert = adb.create();	
		alert.show();
		alert.setContentView(R.layout.dialog_constitution_group_crm_item);
		
		// Récupération des données pour les listener internes
		final ConstitutionGroupCrmFragment vueDemandeMoyen = this.vue;
		final Moyen item = this.getItemMoyen();
		final Spinner tv = ((Spinner)alert.findViewById(R.id.constitution_group_crm_spinner_group));
		
		// On set la valeur du titre de la boite de Dialog
		((TextView)alert.findViewById(R.id.constitution_group_crm_dialog_TextView_Title)).setText("Choisissez le groupe dans lequel ajouter " + item.getLibelle());
		
		// On pose le listener d'annulation de suppression
		alert.findViewById(R.id.demande_de_moyen_Dialog_Button_Cancel).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				// On ferme la boite de dialogue
				alert.cancel();

			}});
		
		// On pose le listener de confirmation de suppression
		alert.findViewById(R.id.demande_de_moyen_Dialog_Button_Delete).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				// Ajout au group
				groupSelected.addMoyen(item);
				
				// Ajout en base
				groupSelected.save();
				
				// MAJ ListGroupe
				// TODO
				
				// On ferme la boite de dialogue
				alert.cancel();

			}});
		
	} // méthode
	
	/*******************************************************************
	 * GETTERS & SETTERS
	 ******************************************************************/
	
	/**
	 * @param itemMoyen the itemMoyen to set
	 */
	public void setItemMoyen(Moyen itemMoyen) {
		this.itemMoyen = itemMoyen;
	}

	/**
	 * @return the itemMoyen
	 */
	public Moyen getItemMoyen() {
		return itemMoyen;
	}

	/**
	 * @param vue the vue to set
	 */
	public void setVue(ConstitutionGroupCrmFragment vue) {
		this.vue = vue;
	}

	/**
	 * @return the vue
	 */
	public ConstitutionGroupCrmFragment getVue() {
		return vue;
	}

} // class
