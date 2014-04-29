package com.istic.agetac.controllers.dao;

import java.util.List;

import com.android.volley.VolleyError;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.sit.framework.couch.APersitantRecuperator;
import com.istic.sit.framework.couch.CouchDBUtils;
import com.istic.sit.framework.couch.IPersistant;

/**
* Classe Abstraite ADao
* 
* @author Anthony LE MEE - 10003134
*/
public abstract class ADao<T extends IPersistant> {

	private IViewReceiver<T> iViewReceiver;
	
	/**
	 * Constructeur
	 */
	public ADao(IViewReceiver<T> iViewReceiver) {
		this.setViewReceiver(iViewReceiver);
	}
	
	/**
	 * Method to retrieve all element of the specific model
	 * @param type
	 */
	public final void executeFindAll(Class<T> type) {
						
		CouchDBUtils.getFromCouch(new APersitantRecuperator<T>(type) {

			@Override
			public void onErrorResponse(VolleyError error) {
				onResponseFail(error);
			}

			@Override
			public void onResponse(List<T> objets) {
				onResponseReady(objets);
			}
			
		});
				
	} // method
	
	/**
	 * Set IViewReceiver
	 */
	public void setViewReceiver (IViewReceiver<T> iViewReceiver) {
		this.iViewReceiver = iViewReceiver;
	}

	/**
	 * Get IViewReceiver
	 * @return IViewReceiver
	 */
	public IViewReceiver<T> getViewReceiver () {
		return iViewReceiver;
	}
	
	/**
	 * Method which notifies the view when Success Response occured on CouchDBUtils.getFromCouch call.
	 */
	private void onResponseReady(List<T> objects) {
		iViewReceiver.notifyResponseSuccess(objects);
	}
	
	/**
	 * Method which notifies the view when Fail Response occured on CouchDBUtils.getFromCouch call.
	 */
	private void onResponseFail(VolleyError error) {
		iViewReceiver.notifyResponseFail(error);
	}

} // abstract class
