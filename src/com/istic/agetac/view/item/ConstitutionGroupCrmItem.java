package com.istic.agetac.view.item;

import java.io.Serializable;

import com.istic.agetac.model.TypeMoyen;

public class ConstitutionGroupCrmItem  implements Serializable{

	/** Attributs */
	private static final long serialVersionUID = 1L;
	private TypeMoyen typeOfMoyen;
	private String nameOfMoyen;

	/**
	 * Constructeur ConstitutionGroupCrmItem
	 * 
	 * @param typeMoyen
	 *            TypeMoyen - type du moyen 
	 * @param name
	 *            String - nom du moyen
	 */
	public ConstitutionGroupCrmItem(TypeMoyen typeMoyen, String name) {

		this.setTypeOfMoyen(typeMoyen);
		this.setNameOfMoyen(name);

	}// m�thode

	/*****************************************************************
	 * GETTERS & SETTERS
	 ****************************************************************/
	
	/**
	 * @return the typeOfMoyen
	 */
	public TypeMoyen getTypeOfMoyen() {
		return typeOfMoyen;
	}

	/**
	 * @param string the typeOfMoyen to set
	 */
	public void setTypeOfMoyen(TypeMoyen string) {
		this.typeOfMoyen = string;
	}

	/**
	 * @return the nameOfMoyen
	 */
	public String getNameOfMoyen() {
		return nameOfMoyen;
	}

	/**
	 * @param nameOfMoyen the nameOfMoyen to set
	 */
	public void setNameOfMoyen(String nameOfMoyen) {
		this.nameOfMoyen = nameOfMoyen;
	}

} // class
