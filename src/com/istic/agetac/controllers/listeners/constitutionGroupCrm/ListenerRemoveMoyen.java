package com.istic.agetac.controllers.listeners.constitutionGroupCrm;

import java.util.List;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.fragments.ConstitutionGroupCrmFragment;
import com.istic.agetac.model.Moyen;

/**
* Classe ListenerRemoveMoyen : Listener 
*
* @author Anthony LE MEE - 10003134
*/
public class ListenerRemoveMoyen implements OnClickListener{

	/** Attributs */
	private IMoyen itemMoyen; 							// Instance de l'item à traiter
	private ConstitutionGroupCrmFragment vue; 			// Instance de la vue 
	private IMoyen groupSelected;
	private List<IMoyen> listGroupe;
	
	/**
	 * Contructeur de ListenerRemoveMoyen
	 * @param user 
	 * @param ConstitutionGroupCrmFragment 
	 */
	public ListenerRemoveMoyen(IMoyen moyen, ConstitutionGroupCrmFragment constitutionGroupCrm, IMoyen groupe) {
		
		this.itemMoyen = moyen;
		this.vue = constitutionGroupCrm;
		this.listGroupe = this.vue.getmListGroup();
		this.groupSelected = groupe;
		
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
		final ConstitutionGroupCrmFragment vueDemandeMoyen = this.vue;
		final IMoyen item = this.getItemMoyen();		
		
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
				groupSelected.deleteMoyen(item);
				
				// Ajout en base
				groupSelected.save();
				
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
	 * @param itemMoyen the itemMoyen to set
	 */
	public void setItemMoyen(Moyen itemMoyen) {
		this.itemMoyen = itemMoyen;
	}

	/**
	 * @return the itemMoyen
	 */
	public IMoyen getItemMoyen() {
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
