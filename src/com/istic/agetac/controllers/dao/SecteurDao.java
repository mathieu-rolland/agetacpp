package com.istic.agetac.controllers.dao;

import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.model.ADao;
import com.istic.agetac.model.Secteur;

/**
* Classe SecteurDao
* 
* @author Anthony LE MEE - 10003134
*/
public class SecteurDao extends ADao<Secteur> {
	
	/**
	 * Constructeur de la classe SecteurDao
	 */
	public SecteurDao (IViewReceiver<Secteur> iViewReceiver) {
		super(iViewReceiver);
	} // constructeur
	
	/**
	 * Method which return all Moyen.
	 * @return List<Secteur>
	 */
	public void findAll() {
		super.executeFindAll(Secteur.class);
	} // method

} // class MoyensDao
