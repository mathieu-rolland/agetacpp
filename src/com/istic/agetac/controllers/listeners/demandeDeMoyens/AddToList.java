package com.istic.agetac.controllers.listeners.demandeDeMoyens;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.istic.agetac.fragments.DemandeDeMoyensFragment;
import com.istic.agetac.model.TypeMoyen;
import com.istic.agetac.view.item.DemandeDeMoyensItem;

/**
 * Classe AddToList : Listener déclenché lors de la demande d'ajout
 * d'un moyens à la liste de demande
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

		// Récupération de l'indice de l'élement sélectionner dans la grille. SI
		// aucun, alors on aura -1
		int indiceMoyensSelected = this.demandeDeMoyens.getSauvegarde()
				.getIndiceMoyen();

		/*
		 * Le champs d'auto-complétion "autre moyen" à le dessus sur la grille.
		 * Si on saisie quelque chose dedans alors c'est que l'on souhaite
		 * l'ajouter. Dans le cas contraire, il faudra alors obligatoirement
		 * sélectionner un moyen dans la grille
		 */
		if (this.demandeDeMoyens.getTextViewAutresMoyens().getText() != null
				&& !(this.demandeDeMoyens.getTextViewAutresMoyens().getText()
						.toString().equals("")) && this.demandeDeMoyens.getSelectedTypeMoyen() == null) {

			
			
			// On créer l'élément
			DemandeDeMoyensItem nomElementSelectionne = new DemandeDeMoyensItem(
					this.demandeDeMoyens.getSelectedTypeMoyen(),
					Integer.parseInt(this.demandeDeMoyens
							.getEditTextNombreMoyens().getText() + ""));

			// Si j'ai déjà ajouté ce type de moyen pour ce secteur, alors
			// j'augmente seulement sont nombre de +1
			DemandeDeMoyensItem searchSameElement = this.searchSameMoyenAddedPreviously(nomElementSelectionne);
			
			if (searchSameElement != null) {
				searchSameElement.setNombre(searchSameElement.getNombre()
						+ Integer.parseInt(this.demandeDeMoyens
								.getEditTextNombreMoyens().getText() + ""));
			} else {
				this.demandeDeMoyens.getAllMoyenAddedToList().add(0,
						nomElementSelectionne);
			}

			// Mise à jour de la list afin qu'elle prenne en compte le nouvel
			// élément
			this.demandeDeMoyens.getAdapterListToSend().notifyDataSetChanged();

			// Sauvegarde
			this.demandeDeMoyens.getSauvegarde().setDonneesMoyensAddedToList(
					this.demandeDeMoyens.getAllMoyenAddedToList());

			Toast.makeText(
					v.getContext().getApplicationContext(),
					nomElementSelectionne.getType() + " ajouté...", Toast.LENGTH_SHORT).show();

		}
		// Sinon si j'ai sélectionné un moyen dans la grille
		else if (indiceMoyensSelected >= 0) {
			
			// On créer l'élément
			DemandeDeMoyensItem nomElementSelectionne = new DemandeDeMoyensItem(
					(TypeMoyen) this.demandeDeMoyens.getDonneesNomsUsestMoyens()[indiceMoyensSelected],
					Integer.parseInt(this.demandeDeMoyens
							.getEditTextNombreMoyens().getText() + ""));

			// Si j'ai déjà ajouté ce type de moyen pour ce secteur, alors
			// j'augmente seulement sont nombre de +1
			DemandeDeMoyensItem searchSameElement = this.searchSameMoyenAddedPreviously(nomElementSelectionne);
	
			if (searchSameElement != null) {
				searchSameElement.setNombre(searchSameElement.getNombre()
						+ Integer.parseInt(this.demandeDeMoyens
								.getEditTextNombreMoyens().getText() + ""));
			} else {
				this.demandeDeMoyens.getAllMoyenAddedToList().add(0,
						nomElementSelectionne);
			}

			// Mise à jour de la list afin qu'elle prenne en compte le nouvel
			// élément
			this.demandeDeMoyens.getAdapterListToSend().notifyDataSetChanged();

			// Sauvegarde
			this.demandeDeMoyens.getSauvegarde().setDonneesMoyensAddedToList(
					this.demandeDeMoyens.getAllMoyenAddedToList());

			Toast.makeText(
					v.getContext().getApplicationContext(),
					nomElementSelectionne.getType() + " ajouté...", Toast.LENGTH_SHORT).show();

		}
		// dans tous les autres cas
		else {
			Toast.makeText(v.getContext().getApplicationContext(),
					"Veuillez sélectionner un moyen à ajouter",
					Toast.LENGTH_SHORT).show();
		} // if else

	} // méthode

	/********************************************************************************************************/
	/** GETTEURS ET SETTEURS **/
	/********************************************************************************************************/

	/**
	 * Méthode qui dit si un moyen du même nom à déjà été ajouté.
	 * @param nomElementSelectionne
	 * @return DemandeDeMoyensItem trouvé
	 */
	private DemandeDeMoyensItem searchSameMoyenAddedPreviously(
			DemandeDeMoyensItem elementSelectionne) {
		if (this.demandeDeMoyens.getAllMoyenAddedToList().size() <= 0) {
			return null;
		}
		else
		{
			for (DemandeDeMoyensItem item : this.demandeDeMoyens.getAllMoyenAddedToList()) {
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