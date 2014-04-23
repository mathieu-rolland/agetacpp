package com.istic.agetac.controllers.listeners.demandeDeMoyens;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.istic.agetac.fragments.DemandeDeMoyensFragment;

/**
* Classe SpinnerVariation de DemandeDeMoyensFragment : augmentation ou diminution du nombre de moyen à ajouter à la liste
* 
* @author Anthony LE MEE - 10003134
*/
public class SpinnerVariation implements OnClickListener {

	/** Instance de l'activity principale */
	private DemandeDeMoyensFragment demandeDeMoyens;
	
	/**
	 * Constructeur ListenerSecteur
	 * @param a DemandeDeMoyensFragment
	 */
	public SpinnerVariation (DemandeDeMoyensFragment a) {
		
		this.setDemandeDeMoyens(a);
		
	}
	
	/**
	 * Lors d'un click sur la View v
	 * @param v View
	 */
	public void onClick(View v) {
		
		// Convertion de la valeur courant en int
        int nombreMoyen = Integer.parseInt(this.getDemandeDeMoyens().getEditTextNombreMoyens().getText().toString());
		
		if( this.getDemandeDeMoyens().getBouttonAjoutQuantite().getId() == ((Button)v).getId() ){
			
			// On incremente
	        nombreMoyen++;
	        
		}
		else if( this.getDemandeDeMoyens().getBouttonRetireQuantite().getId() == ((Button)v).getId() ){

			// On décrémente
	        nombreMoyen--;
			
		}
		
		// Mise à jour
        this.getDemandeDeMoyens().getEditTextNombreMoyens().setText("" + nombreMoyen + "");
		
	}

	/********************************************************************************************************/
    /** GETTEURS ET SETTEURS
    /********************************************************************************************************/
	
	/**
	 * @param demandeDeMoyens the demandeDeMoyens to set
	 */
	public void setDemandeDeMoyens(DemandeDeMoyensFragment demandeDeMoyens) {
		this.demandeDeMoyens = demandeDeMoyens;
	}

	/**
	 * @return the demandeDeMoyens
	 */
	public DemandeDeMoyensFragment getDemandeDeMoyens() {
		return this.demandeDeMoyens;
	}
	
}
