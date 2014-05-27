package com.istic.agetac.controllers.listeners.demandeDeMoyens;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.istic.agetac.fragments.DemandeDeMoyensFragment;
import com.istic.agetac.model.TypeMoyen;
import com.istic.agetac.view.item.DemandeDeMoyenItem;

/**
 * Classe AddToList : Listener d�clench� lors de la demande d'ajout
 * d'un moyens � la liste de demande
 * 
 * @author Anthony LE MEE - 10003134
 */
public class AddToList implements OnClickListener {

	/** Instance du fragment principale de demande de moyens */
	private DemandeDeMoyensFragment demandeDeMoyens;

	/**
	 * Constructeur de AddToList
	 * 
	 * @param vueDemandeDeMoyens
	 */
	public AddToList(DemandeDeMoyensFragment vueDemandeDeMoyens) {

		this.demandeDeMoyens = vueDemandeDeMoyens;

	}

	/**
	 * Lors d'un Click sur la vue v
	 * 
	 * @param v
	 *            view
	 */
	public void onClick(View v) {

		// R�cup�ration de l'indice de l'�lement s�lectionner dans la grille. SI
		// aucun, alors on aura -1
		int indiceMoyensSelected = this.demandeDeMoyens.getSauvegarde()
				.getIndiceMoyen();

		if (indiceMoyensSelected >= 0) {
			
			// On cr�er l'�l�ment
			DemandeDeMoyenItem nomElementSelectionne = new DemandeDeMoyenItem(
					(TypeMoyen) this.demandeDeMoyens.getDonneesNomsUsestMoyens()[indiceMoyensSelected],
					Integer.parseInt(this.demandeDeMoyens
							.getEditTextNombreMoyens().getText() + ""));

			// Si j'ai d�j� ajout� ce type de moyen pour ce secteur, alors
			// j'augmente seulement sont nombre de +1
			DemandeDeMoyenItem searchSameElement = this.searchSameMoyenAddedPreviously(nomElementSelectionne);
	
			if (searchSameElement != null) {
				searchSameElement.setNombre(searchSameElement.getNombre()
						+ Integer.parseInt(this.demandeDeMoyens
								.getEditTextNombreMoyens().getText() + ""));
			} else {
				this.demandeDeMoyens.getAllMoyenAddedToList().add(0,
						nomElementSelectionne);
			}

			// Mise � jour de la list afin qu'elle prenne en compte le nouvel
			// �l�ment
			this.demandeDeMoyens.getAdapterListToSend().notifyDataSetChanged();

			// Sauvegarde
			this.demandeDeMoyens.getSauvegarde().setDonneesMoyensAddedToList(
					this.demandeDeMoyens.getAllMoyenAddedToList());

			Toast.makeText(
					v.getContext().getApplicationContext(),
					nomElementSelectionne.getType() + " ajout�...", Toast.LENGTH_SHORT).show();

		}
		// dans tous les autres cas
		else {
			Toast.makeText(v.getContext().getApplicationContext(),
					"Veuillez s�lectionner un moyen � ajouter",
					Toast.LENGTH_SHORT).show();
		} // if else

	} // m�thode

	/********************************************************************************************************/
	/** GETTEURS ET SETTEURS **/
	/********************************************************************************************************/

	/**
	 * M�thode qui dit si un moyen du m�me nom � d�j� �t� ajout�.
	 * @param nomElementSelectionne
	 * @return DemandeDeMoyenItem trouv�
	 */
	private DemandeDeMoyenItem searchSameMoyenAddedPreviously(
			DemandeDeMoyenItem elementSelectionne) {
		if (this.demandeDeMoyens.getAllMoyenAddedToList().size() <= 0) {
			return null;
		}
		else
		{
			for (DemandeDeMoyenItem item : this.demandeDeMoyens.getAllMoyenAddedToList()) {
				if(item.getType().equals(elementSelectionne.getType())) {
					return item;
				}
			}
			
			return null;
		}
	} // method searchSameMoyenAddedPreviously

	/**
	 * @param demandeDeMoyens
	 *            the demandeDeMoyens to set
	 */
	public void setDemandeDeMoyens(DemandeDeMoyensFragment demandeDeMoyens) {
		this.demandeDeMoyens = demandeDeMoyens;
	}

	/**
	 * @return the demandeDeMoyens
	 */
	public DemandeDeMoyensFragment getDemandeDeMoyens() {
		return demandeDeMoyens;
	}

} // class AddToList