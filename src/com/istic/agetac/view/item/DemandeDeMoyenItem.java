package com.istic.agetac.view.item;

import java.io.Serializable;

import com.istic.agetac.model.TypeMoyen;

/**
 * Class DemandeDeMoyenItem : Mod�le de repr�sentation des items 
 * de la listView de la vue DemandeDeMoyens - s�rializable afin 
 * de permettre la sauvegarde.
 * 
 * @author Anthony LE MEE - 10003134
*/
public class DemandeDeMoyenItem implements Serializable{

	/** Attributs */
	private static final long serialVersionUID = 1L;
	private TypeMoyen typeOfMoyen;	// type du moyen ajout�
	private int quantityOfMoyen;							// quantityOfMoyen de moyen de ce type voulu
	
	/**
	 * Constructeur DemandeDeMoyenItem
	 * @param name String - type du moyen ajout�
	 * @param quantity int - nombre de moyen de ce type voulu
	 */
	public DemandeDeMoyenItem (TypeMoyen type, int quantity) {
	
		this.typeOfMoyen = type;
		this.quantityOfMoyen = quantity;
		
	}// m�thode
	
	/*****************************************************************
	 * GETTERS & SETTERS
	 ****************************************************************/

	/**
	 * @param typeOfMoyen the typeOfMoyen to set
	 */
	public void setType(TypeMoyen type) {
		this.typeOfMoyen = type;
	}

	/**
	 * @return the typeOfMoyen
	 */
	public TypeMoyen getType() {
		return typeOfMoyen;
	}

	/**
	 * @param quantityOfMoyen the quantityOfMoyen to set
	 */
	public void setNombre(int nombre) {
		this.quantityOfMoyen = nombre;
	}

	/**
	 * @return the quantityOfMoyen
	 */
	public int getNombre() {
		return quantityOfMoyen;
	}
	
} // class
