package com.istic.agetac.controllers.listeners.tableauMoyen;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

import com.istic.agetac.fragments.TableauMoyenFragment;
import com.istic.agetac.model.Secteur;

/**
* Classe SwitchSector de TableauMoyenFragment : sélection d'un secteur => changement de la couleur des moyens
* 
* @author Anthony LE MEE - 10003134
*/
public class SwitchSector implements AdapterView.OnItemSelectedListener, OnValueChangeListener {
		
	/** Instance de l'activity principale */
	private TableauMoyenFragment tableauMoyenFragment;
	
	/**
	 * Constructeur SwitchSector
	 * @param a TableauMoyenFragment
	 */
	public SwitchSector (TableauMoyenFragment a) {
		
		this.tableauMoyenFragment = a;
	
	}
	
	/**
	 * Méthode onItemSelected
	 * @param parent AdapterView<?>
	 * @param vue View 
	 * @param position int
	 * @param id long
	 */
	public void onItemSelected(AdapterView<?> parent, View vue, int position, long id) {
		
		
		
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
