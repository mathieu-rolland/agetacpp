package com.istic.agetac.controllers.listeners.constitutionGroupCrm;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.fragments.ConstitutionGroupCrmFragment;
import com.istic.agetac.model.Groupe;

/**
* Classe ListenerRemoveGroup : Listener 
*
* @author Anthony LE MEE - 10003134
*/
public class ListenerRemoveGroup implements OnClickListener{

	/** Attributs */
	private Groupe itemGroupe; 							// Instance de l'item à traiter
	private ConstitutionGroupCrmFragment vue; 			// Instance de la vue 
	
	/**
	 * Contructeur de ListenerRemoveGroup
	 * @param user 
	 * @param ConstitutionGroupCrmFragment 
	 */
	public ListenerRemoveGroup(Groupe groupe, ConstitutionGroupCrmFragment constitutionGroupCrm) {
		
		this.itemGroupe = groupe;
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
		alert.setContentView(R.layout.dialog_constitution_group_crm_delete);
		
		// Récupération des données pour les listener internes
		final Groupe item = this.getItemGroupe();		
		
		// On set la valeur du titre de la boite de Dialog
		((TextView)alert.findViewById(R.id.constitution_group_crm_dialog_Delete_TextView_Title)).setText("Confirmez la suppression de " + item.getNom() + "");
				
		// On pose le listener d'annulation de suppression
		alert.findViewById(R.id.constitution_group_crm_dialog_Button_Cancel).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				// On ferme la boite de dialogue
				alert.cancel();

			}});
		
		// On pose le listener de confirmation de suppression
		alert.findViewById(R.id.constitution_group_crm_dialog_Button_Delete).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				// Ajout au group
				item.deleteAllMoyen();
				
				// Ajout en base
				item.delete();
				
				// MAJ Groupe et Moyen in the view
				vue.updateVue();
				
				// On ferme la boite de dialogue
				alert.cancel();

			}});
		
	} // méthode
	
	/*******************************************************************
	 * GETTERS & SETTERS
	 ******************************************************************/
	
	/**
	 * @param itemGroupe the itemGroupe to set
	 */
	public void setItemGroupe(Groupe itemGroupe) {
		this.itemGroupe = itemGroupe;
	}

	/**
	 * @return the itemGroupe
	 */
	public Groupe getItemGroupe() {
		return itemGroupe;
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
