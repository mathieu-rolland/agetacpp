package com.istic.agetac.controllers.listeners.constitutionGroupCrm;

import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.utils.SauvegardeStateCrm;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

/**
* Classe ListenerCollapse : Listener
*
* @author Anthony LE MEE - 10003134
*/
public class ListenerCollapse implements OnClickListener{

	/** Attributs */
	private int position;
	private int nbToHideOrDisplay;
	private ListView listview;
	
	/**
	 * Contructeur de ListenerAddMoyen
	 * @param user 
	 * @param ConstitutionGroupCrmFragment 
	 */
	public ListenerCollapse(int position, int nbToHideOrDisplay, ListView listview) {
		
		this.setPosition(position);
		this.setNbToHideOrDisplay(nbToHideOrDisplay);
		this.setListview(listview);		
		
	} // méthode

	/**
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		
		for (int nextPosition = this.position + 1 ; nextPosition <= this.position + this.getNbToHideOrDisplay() ; nextPosition++) {
			if (this.listview.getChildAt(nextPosition).getVisibility() == View.GONE) {
				SauvegardeStateCrm.getInstance().getMoyenListItemModified().add(nextPosition,SauvegardeStateCrm.getInstance().getMoyenListItemOriginal().get(nextPosition));
			}
			else
			{
				SauvegardeStateCrm.getInstance().getMoyenListItemModified().remove(nextPosition);
			}
		}
		
	} // méthode
	
	/*******************************************************************
	 * GETTERS & SETTERS
	 ******************************************************************/

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the nbToHideOrDisplay
	 */
	public int getNbToHideOrDisplay() {
		return nbToHideOrDisplay;
	}

	/**
	 * @param nbToHideOrDisplay the nbToHideOrDisplay to set
	 */
	public void setNbToHideOrDisplay(int nbToHideOrDisplay) {
		this.nbToHideOrDisplay = nbToHideOrDisplay;
	}

	/**
	 * @return the listview
	 */
	public ListView getListview() {
		return listview;
	}

	/**
	 * @param listview the listview to set
	 */
	public void setListview(ListView listview) {
		this.listview = listview;
	}

} // class
