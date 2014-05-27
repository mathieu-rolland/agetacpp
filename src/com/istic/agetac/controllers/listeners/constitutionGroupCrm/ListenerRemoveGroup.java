package com.istic.agetac.controllers.listeners.constitutionGroupCrm;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.fragments.ConstitutionGroupCrmFragment;

/**
* Classe ListenerRemoveGroup : Listener 
*
* @author Anthony LE MEE - 10003134
*/
public class ListenerRemoveGroup implements OnClickListener{

	/** Attributs */
	private IMoyen itemGroupe; 							// Instance de l'item à traiter
	private ConstitutionGroupCrmFragment vue; 			// Instance de la vue 
	
	/**
	 * Contructeur de ListenerRemoveGroup
	 * @param user 
	 * @param ConstitutionGroupCrmFragment 
	 */
	public ListenerRemoveGroup(IMoyen groupe, ConstitutionGroupCrmFragment constitutionGroupCrm) {
		
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
		final IMoyen item = this.getItemGroupe();		
		
		// On set la valeur du titre de la boite de Dialog
		((TextView)alert.findViewById(R.id.constitution_group_crm_dialog_Delete_TextView_Title)).setText("Confirmez la suppression de " + item.getLibelle() + "");
				
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
	public void setItemGroupe(IMoyen itemGroupe) {
		this.itemGroupe = itemGroupe;
	}

	/**
	 * @return the itemGroupe
	 */
	public IMoyen getItemGroupe() {
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
