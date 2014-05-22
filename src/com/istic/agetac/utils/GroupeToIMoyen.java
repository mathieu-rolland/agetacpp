package com.istic.agetac.utils;

import java.util.ArrayList;
import java.util.List;

import com.istic.agetac.model.Groupe;
import com.istic.agetac.model.IMoyen;

/**
 * Class utils de convertion de List<Groupe> en List<IMoyen>
 * @author Anthony LE MÉE - 10003134
 *
 */
public class GroupeToIMoyen {
	
	public static List<IMoyen> convert (List<Groupe> groupes) {
		
		List<IMoyen> imoyens = new ArrayList<IMoyen> ();
		
		for (Groupe groupe : groupes) {
			
			imoyens.add(groupe);
			
		}
		
		return imoyens;
		
	} // method
	
} // class
