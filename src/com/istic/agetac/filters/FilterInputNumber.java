/*	
* Projet AGETAC	
* Anthony LE MEE	
* Universite de Rennes 1	
* ISTIC	
*/
package com.istic.agetac.filters;

import android.text.InputFilter;
import android.text.Spanned;

/**
* Classe FilterInputNumber : Filtre de domaine pour les champs num�riques.
*
* @version 1.0 17/01/2013
* @author Anthony LE MEE - 10003134
*/
public class FilterInputNumber implements InputFilter {
	
	/** Valeur minimale des champs num�riques */
	private int min;
	/** Valeur maximale des champs num�riques */
	private int max;
 
	/**
	 * Constructeur avec entiers
	 * @param min int
	 * @param max int
	 */
	public FilterInputNumber(int min, int max) {
		this.min = min;
		this.max = max;
	}
 
	/**
	 * Constructeur avec cha�nes de caract�res
	 * @param min String
	 * @param max String 
	 */
	public FilterInputNumber(String min, String max) {
		this.min = Integer.parseInt(min);
		this.max = Integer.parseInt(max);
	}

	/**
	 * Filtre
	 * @param source CharSequence
	 * @param start int
	 * @param end int 
	 * @param dest Spanned
	 * @param dstart int
	 * @param dend int
	 */
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {	
		try {
			int input = Integer.parseInt(dest.toString() + source.toString());
			return "" + isInRange(min, max, input) + "";
		} catch (NumberFormatException nfe) { }
		return null;		
	}
 
	/**
	 * Retourne un entier en fonction de si oui ou non le pr�cendent �tait dans l'intervalle souhait�
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	private int isInRange(int a, int b, int c) {
		if (c>b) { return b;}
		if (c<b && c>a) { return c;}
		if (c<a) { return a;}
		return c;
	}
	
}