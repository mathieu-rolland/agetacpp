package com.istic.agetac.controllers.listeners.demandeDeMoyens;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

import com.istic.agetac.fragments.DemandeDeMoyensFragment;
import com.istic.agetac.model.Secteur;

/**
* Classe SwitchSector de DemandeDeMoyensFragment : sélection d'un secteur => changement de la couleur des moyens
* 
* @author Anthony LE MEE - 10003134
*/
public class SwitchSector implements AdapterView.OnItemSelectedListener, OnValueChangeListener {
		
	/** Instance de l'activity principale */
	private DemandeDeMoyensFragment demandeDeMoyens;
	
	/**
	 * Constructeur SwitchSector
	 * @param a DemandeDeMoyensFragment
	 */
	public SwitchSector (DemandeDeMoyensFragment a) {
		
		this.demandeDeMoyens = a;
	
	}
	
	/**
	 * Méthode onItemSelected
	 * @param parent AdapterView<?>
	 * @param vue View 
	 * @param position int
	 * @param id long
	 */
	public void onItemSelected(AdapterView<?> parent, View vue, int position, long id) {
		
		Secteur secteurSelected 		= (Secteur)parent.getItemAtPosition(position);
		String 	colorSecteurSelected 	= secteurSelected.getColor();
		
		// Notification du changement de secteur
		//this.demandeDeMoyens.getSauvegarde().setSecteur(secteurSelected);
		
		// On change la couleur en fonction du secteur choisi
		try{
			
			// Pour chaque moyen du GridView
			for (int i = 0 ; i < this.demandeDeMoyens.getGridViewMoyens().getCount(); i++) {
				
				// Changement de couleur du moyen
				((TextView) this.demandeDeMoyens.getGridViewMoyens().getChildAt(i)).setTextColor(Color.parseColor(colorSecteurSelected));
				// Alignement dans le TextView
				((TextView) this.demandeDeMoyens.getGridViewMoyens().getChildAt(i)).setGravity(Gravity.CENTER);
								
			}

			// Changement de couleur du secteur choisi
			//((TextView) this.demandeDeMoyens.getArrayAdapterSecteur().getView(position, vue, parent)).setTextColor(Color.parseColor(colorSecteurSelected));
			
			// Changement de couleur du bouton ajouter
			this.demandeDeMoyens.getBouttonAjoutMoyenToList().setBackgroundColor(Color.parseColor(colorSecteurSelected));
			
			// Changement de couleur du bouton +
			this.demandeDeMoyens.getBouttonAjoutQuantite().setBackgroundColor(Color.parseColor(colorSecteurSelected));
			
			// Changement de couleur du bouton -
			this.demandeDeMoyens.getBouttonRetireQuantite().setBackgroundColor(Color.parseColor(colorSecteurSelected));
			
		}catch(Exception e) {
			
			// Impossible de parser la couleur

		}
		
	}// Méthode onItemSelected

	/**
	 * Méthode onNothingSelected
	 * @param parent AdapterView<?>
	 */
	public void onNothingSelected(AdapterView<?> parent) {		

	}

	/**
	 * Quand le texte à changé
	 * @param picker NumberPicker
	 * @param oldVal int
	 * @param newVal int
	 */
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		
	}
	
}// Classe SwitchSector
