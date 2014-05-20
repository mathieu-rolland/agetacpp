package com.istic.agetac.controllers.listeners.demandeDeMoyens;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.istic.agetac.R;
import com.istic.agetac.fragments.DemandeDeMoyensFragment;
import com.istic.agetac.view.item.DemandeDeMoyenItem;

/**
* Classe AddToList : Listener déclenché lors de l'appuie sur la corbeille d'un item
*
* @author Anthony LE MEE - 10003134
*/
public class ListenerSupressionItem implements OnClickListener{

	/** Attributs */
	private DemandeDeMoyenItem itemMoyen; 	// Instance de l'item à traiter
	private DemandeDeMoyensFragment vue; 			// Instance de la vue d'où est joué le listener
	
	/**
	 * Contructeur de ListenerSupressionItem
	 * @param user 
	 * @param demandeDeMoyens 
	 */
	public ListenerSupressionItem(DemandeDeMoyenItem user, DemandeDeMoyensFragment demandeDeMoyens) {
		
		this.itemMoyen = user;
		this.vue = demandeDeMoyens;
		
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
		alert.setContentView(R.layout.dialog_demande_de_moyens_delete_item);
		
		// Récupération des données pour les listener internes
		final DemandeDeMoyensFragment vueDemandeMoyen = this.vue;
		final DemandeDeMoyenItem item = this.getItemMoyen();
		final TextView tv = ((TextView)alert.findViewById(R.id.demande_de_moyen_dialog_TextView_Quantity));
		
		// On set la valeur du titre de la boite de Dialog
		((TextView)alert.findViewById(R.id.demande_de_moyen_dialog_TextView_Title)).setText("Choisissez le nombre de " + item.getType() + " à supprimer dans ce secteur");
		
		// On set la valeur par défaut du champs "nombre item to delete"
		tv.setText(String.valueOf(this.getItemMoyen().getNombre())); //

		// On pose le listener d'annulation de suppression
		alert.findViewById(R.id.demande_de_moyen_Dialog_Button_Cancel).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				// On ferme la boite de dialogue
				alert.cancel();

			}});
		
		// On pose le listener de confirmation de suppression
		alert.findViewById(R.id.demande_de_moyen_Dialog_Button_Delete).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				int tmp = Integer.parseInt(((TextView)alert.findViewById(R.id.demande_de_moyen_dialog_TextView_Quantity)).getText().toString());
				
				// Si l'utilisateur souhaite tout supprimer
				if (tmp == item.getNombre()) {
					
					//alors on retire l'instance courante
					vue.getAllMoyenAddedToList().remove(item);
				}
				// Sinon, on diminue l'occurence du type de moyen courant par celui voulu
				else
				{
					if (tmp < item.getNombre()) item.setNombre(item.getNombre() - tmp);
				}
				
				// Lancement de la mise à jour
				vueDemandeMoyen.getAdapterListToSend().notifyDataSetChanged();
				
				// On ferme la boite de dialogue
				alert.cancel();

			}});
		
		// On pose le listener de diminution du nombre de moyen de ce type à supprimer
		alert.findViewById(R.id.demande_de_moyen_dialog_Button_OneLess).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (Integer.parseInt(((TextView)alert.findViewById(R.id.demande_de_moyen_dialog_TextView_Quantity)).getText() + "") > 1) {
					
					int tmp = Integer.parseInt(((TextView)alert.findViewById(R.id.demande_de_moyen_dialog_TextView_Quantity)).getText().toString()) - 1;
					((TextView)alert.findViewById(R.id.demande_de_moyen_dialog_TextView_Quantity)).setText(
							String.valueOf(tmp));
					
				}

			}});
		
		// On pose le listener d'augmentation du nombre de moyen de ce type à supprimer
		alert.findViewById(R.id.demande_de_moyen_Dialog_Button_OneMore).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (Integer.parseInt(((TextView)alert.findViewById(R.id.demande_de_moyen_dialog_TextView_Quantity)).getText() + "") < item.getNombre()) {
					
					int tmp = Integer.parseInt(((TextView)alert.findViewById(R.id.demande_de_moyen_dialog_TextView_Quantity)).getText().toString()) + 1;
					((TextView)alert.findViewById(R.id.demande_de_moyen_dialog_TextView_Quantity)).setText(
							String.valueOf(tmp));
					
				}

			}});
		
		
	} // méthode
	
	/*******************************************************************
	 * GETTERS & SETTERS
	 ******************************************************************/
	
	/**
	 * @param itemMoyen the itemMoyen to set
	 */
	public void setItemMoyen(DemandeDeMoyenItem itemMoyen) {
		this.itemMoyen = itemMoyen;
	}

	/**
	 * @return the itemMoyen
	 */
	public DemandeDeMoyenItem getItemMoyen() {
		return itemMoyen;
	}

	/**
	 * @param vue the vue to set
	 */
	public void setVue(DemandeDeMoyensFragment vue) {
		this.vue = vue;
	}

	/**
	 * @return the vue
	 */
	public DemandeDeMoyensFragment getVue() {
		return vue;
	}

} // class
