package com.istic.agetac.exceptions;

public class UserNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6454311650877083765L;

	@Override
	public String getMessage() {
		return "L'utilisateur n'a pas été trouvé !";
	}
}
