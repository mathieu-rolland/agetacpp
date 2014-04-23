package com.istic.agetac.view.item;

import java.io.Serializable;

/**
 * Class DemandeDeMoyensItem : Modèle de représentation des items 
 * de la listView de la vue DemandeDeMoyens - sérializable afin 
 * de permettre la sauvegarde.
 * 
 * @author Anthony LE MEE - 10003134
*/
public class DemandeDeMoyensItem implements Serializable{

	/** Attributs */
	private static final long serialVersionUID = 1L;
	private String nameOfMoyen;			// type du moyen ajouté
	private int quantityOfMoyen;		// quantityOfMoyen de moyen de ce type voulu
	
	/**
	 * Constructeur DemandeDeMoyensItem
	 * @param name String - type du moyen ajouté
	 * @param quantity int - nombre de moyen de ce type voulu
	 */
	public DemandeDeMoyensItem (String name, int quantity) {
	
		this.nameOfMoyen = name;
		this.quantityOfMoyen = quantity;
		
	}// méthode
	
	/*****************************************************************
	 * GETTERS & SETTERS
	 ****************************************************************/

	/**
	 * @param nameOfMoyen the nameOfMoyen to set
	 */
	public void setNom(String nom) {
		this.nameOfMoyen = nom;
	}

	/**
	 * @return the nameOfMoyen
	 */
	public String getNom() {
		return nameOfMoyen;
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
