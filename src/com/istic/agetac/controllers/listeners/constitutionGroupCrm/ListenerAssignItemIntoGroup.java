package com.istic.agetac.controllers.listeners.constitutionGroupCrm;

import com.istic.agetac.fragments.ConstitutionGroupCrmFragment;
import com.istic.agetac.fragments.DemandeDeMoyensFragment;
import com.istic.agetac.view.item.ConstitutionGroupCrmItem;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * Listener qui permet l'ajout d'un moyen (au CRM) à groupe souhaité.
 * 
 * @author Anthony LE MÉE - 10003134
 */
public class ListenerAssignItemIntoGroup implements OnClickListener {

	private ConstitutionGroupCrmItem itemMoyen;
	private ConstitutionGroupCrmFragment constitutionGroupCrm;
	
	/**
	 * Constructeur de la classe ListenerAssignItemIntoGroup
	 * @param itemMoyen
	 * @param constitutionGroupCrm2
	 */
	public ListenerAssignItemIntoGroup(ConstitutionGroupCrmItem itemMoyen, ConstitutionGroupCrmFragment constitutionGroupCrm2) {
		this.setItemMoyen(itemMoyen);
		this.setConstitutionGroupCrm(constitutionGroupCrm2);
	}

	@Override
	public void onClick(View v) {
		
	}

	/**
	 * @return the itemMoyen
	 */
	public ConstitutionGroupCrmItem getItemMoyen() {
		return itemMoyen;
	}

	/**
	 * @param itemMoyen the itemMoyen to set
	 */
	public void setItemMoyen(ConstitutionGroupCrmItem itemMoyen) {
		this.itemMoyen = itemMoyen;
	}

	/**
	 * @return the constitutionGroupCrm
	 */
	public ConstitutionGroupCrmFragment getConstitutionGroupCrm() {
		return constitutionGroupCrm;
	}

	/**
	 * @param constitutionGroupCrm2 the constitutionGroupCrm to set
	 */
	public void setConstitutionGroupCrm(ConstitutionGroupCrmFragment constitutionGroupCrm2) {
		this.constitutionGroupCrm = constitutionGroupCrm2;
	}

} // class
