package com.istic.agetac.controllers.listeners.demandeDeMoyens;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.istic.agetac.fragments.DemandeDeMoyensFragment;

/**
* Classe ListenerMoyens de DemandeDeMoyensFragment : 
*
* @author Anthony LE MEE - 10003134
*/
public class AutoCompleteField implements TextWatcher, OnItemClickListener  {

	/** Instance de l'activity principale */
	private DemandeDeMoyensFragment demandeDeMoyens;
	
	/**
	 * Constructeur AutoCompleteField
	 * @param a DemandeDeMoyensFragment
	 */
	public AutoCompleteField (DemandeDeMoyensFragment a) {
		
		this.setDemandeDeMoyens(a);
		
	}
	
	/**
	 * Méthode beforeTextChanged
	 * @param chaine CharSequence
	 * @param debut int
	 * @param apres int
	 * @param nombre int
	 */
	public void beforeTextChanged(CharSequence chaine, int debut, int apres, int nombre) {
		
		this.getDemandeDeMoyens().getSauvegarde().setIndiceMoyen(-1);
		
	}

	/**
	 * Méthode onTextChanged
	 * @param chaine CharSequence
	 * @param debut int
	 * @param apres int
	 * @param nombre int 
	 */
	public void onTextChanged(CharSequence chaine, int debut, int fin, int nombre) { }

	/**
	 * Méthode afterTextChanged
	 * @param chaine Editable
	 */
	public void afterTextChanged(Editable chaine) {	}
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		this.getDemandeDeMoyens().getSauvegarde().setIndiceMoyen(-1);
		
		// Cache le clavier
		((InputMethodManager) this.getDemandeDeMoyens().getActivity().getSystemService(
				Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
						this.getDemandeDeMoyens().getTextViewAutresMoyens().getWindowToken(), 0);
		
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
		return demandeDeMoyens;
	}
	
} // class AutoCompleteField
