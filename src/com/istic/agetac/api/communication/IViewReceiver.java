package com.istic.agetac.api.communication;

import java.util.List;

import com.android.volley.VolleyError;

/**
* Interface définissant une vue dite "Communicable" avec leur controleur.
* 
* @author Anthony LE MEE - 10003134
*/
public interface IViewReceiver<T> {

	public void notifyResponseSuccess(List<T> objects);
	public void notifyResponseFail(VolleyError error);
	
} // interface
