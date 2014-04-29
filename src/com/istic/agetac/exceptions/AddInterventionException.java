package com.istic.agetac.exceptions;

public class AddInterventionException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3104587462700123464L;

	@Override
	public String getMessage() {
		return "Un intervenant ne peut avoir qu'une seule intervention !";
	}
	
	
}
