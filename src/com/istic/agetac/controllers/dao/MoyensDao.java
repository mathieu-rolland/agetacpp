package com.istic.agetac.controllers.dao;

import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.model.ADao;
import com.istic.agetac.model.Moyen;

/**
* Classe MoyensDao
* 
* @author Anthony LE MEE - 10003134
*/
public class MoyensDao extends ADao<Moyen> {
	
	/**
	 * Constructeur de la classe MoyensDao
	 */
	public MoyensDao (IViewReceiver<Moyen> iViewReceiver) {
		super(iViewReceiver);
	} // constructeur
	
	/**
	 * Method which return all Moyen.
	 * @return List<Moyen>
	 */
	public void findAll() {
		super.executeFindAll(Moyen.class);
	} // method

} // class MoyensDao
