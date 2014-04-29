package com.istic.agetac.controllers.listeners.tableauMoyen;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Spinner;

import com.istic.agetac.controler.adapter.MoyenListIntervenantAdapter;
import com.istic.agetac.model.Secteur;

/**
* Classe SwitchSector de TableauMoyenFragment : sélection d'un secteur => changement de la couleur des moyens
* 
* @author Anthony LE MEE - 10003134
*/
public class SwitchSector implements AdapterView.OnItemSelectedListener, OnValueChangeListener {
		
	/** Instance de l'activity principale */
	private MoyenListIntervenantAdapter moyenListIntervenantAdapter;
	private Spinner spinnerSelected;
	
	/**
	 * Constructeur SwitchSector
	 * @param moyenListIntervenantAdapter MoyenListIntervenantAdapter
	 */
	public SwitchSector (MoyenListIntervenantAdapter moyenListIntervenantAdapter, Spinner spinner) {
		
		this.setMoyenListIntervenantAdapter(moyenListIntervenantAdapter);
		this.spinnerSelected = spinner;
	}
	
	/**
	 * Méthode onItemSelected
	 * @param parent AdapterView<?>
	 * @param vue View 
	 * @param position int
	 * @param id long
	 */
	public void onItemSelected(AdapterView<?> parent, View vue, int position, long id) {
		Secteur selectedSecteur = ((Secteur)this.spinnerSelected.getSelectedItem());
		this.spinnerSelected.getChildAt(0).setBackgroundColor(Color.parseColor(selectedSecteur.getColor()));
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

	/**
	 * @return the moyenListIntervenantAdapter
	 */
	public MoyenListIntervenantAdapter getMoyenListIntervenantAdapter() {
		return moyenListIntervenantAdapter;
	}

	/**
	 * @param moyenListIntervenantAdapter the moyenListIntervenantAdapter to set
	 */
	public void setMoyenListIntervenantAdapter(
			MoyenListIntervenantAdapter moyenListIntervenantAdapter) {
		this.moyenListIntervenantAdapter = moyenListIntervenantAdapter;
	}
	
}// Classe SwitchSector
