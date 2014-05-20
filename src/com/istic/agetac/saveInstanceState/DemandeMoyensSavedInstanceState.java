package com.istic.agetac.saveInstanceState;

import java.io.Serializable;
import java.util.ArrayList;

import com.istic.agetac.view.item.DemandeDeMoyenItem;

/**
* Classe DemandeMoyensSavedInstanceState de VueDemandeDeMoyens : Object que l'on va cr�er afin d'assurer la sauvegarde
* de l'�tat de la vue lors du flip orientation.
* 
* @author Anthony LE MEE - 10003134
*/
public class DemandeMoyensSavedInstanceState implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** Indice du moyen actuellement s�lectionn� */
	private int indexSelectedMoyen;
	
	/** Quantit� d'un secteur actuellement voulu */
	private int quantityMoyen;
	
	/** Le texte saisie comme autre moyen */
	private String otherMoyenAutoCompleteField;
	
	/** Liste des moyens ajout�s � la liste de demande de moyens */
	private ArrayList<DemandeDeMoyenItem> moyensAddedToList;
		
	/** instance courante : Pattern SINGLETON */
	private static DemandeMoyensSavedInstanceState currentInstance;
	
	/**
	 * Constructeur de DemandeMoyensSavedInstanceState
	 */
	private DemandeMoyensSavedInstanceState () {
		
		this.indexSelectedMoyen 			= -1;
		this.quantityMoyen 					= 1;
		this.moyensAddedToList				= new ArrayList<DemandeDeMoyenItem>();
		this.otherMoyenAutoCompleteField 	= "";
		
	}
	
	/**
	 * M�thode de r�cup�ration de l'instance courant de la classe DemandeMoyensSavedInstanceState
	 * @return DemandeMoyensSavedInstanceState
	 */
	public static DemandeMoyensSavedInstanceState getInstance() {
		
		//Le "Double-Checked Singleton"/"Singleton doublement v�rifi�" permet d'�viter un appel co�teux � synchronized, 
        //une fois que l'instanciation est faite.
		if(currentInstance == null) { 
			// Le mot-cl� synchronized sur ce bloc emp�che toute instanciation multiple m�me par diff�rents "threads".
            // Il est TRES important.
			synchronized(DemandeMoyensSavedInstanceState.class) {
				if (currentInstance == null) {
					currentInstance = new DemandeMoyensSavedInstanceState();
				}
			}
		}
		
		return currentInstance;
		
	}
	
	/********************************************************************************************************/
    /** GETTEURS ET SETTEURS
    /********************************************************************************************************/

	/**
	 * @param indexSelectedMoyen the indexSelectedMoyen to set
	 */
	public void setIndiceMoyen(int indiceMoyen) {
		this.indexSelectedMoyen = indiceMoyen;
	}

	/**
	 * @return the indexSelectedMoyen
	 */
	public int getIndiceMoyen() {
		return indexSelectedMoyen;
	}

	/**
	 * @param otherMoyenAutoCompleteField the otherMoyenAutoCompleteField to set
	 */
	public void setAutreMoyen(String autreMoyen) {
		this.otherMoyenAutoCompleteField = autreMoyen;
	}

	/**
	 * @return the otherMoyenAutoCompleteField
	 */
	public String getAutreMoyen() {
		return otherMoyenAutoCompleteField;
	}

	/**
	 * @param quantiteMoyen the quantiteMoyen to set
	 */
	public void setQuantiteMoyen(int quantiteMoyen) {
		this.quantityMoyen = quantiteMoyen;
	}

	/**
	 * @return the quantiteMoyen
	 */
	public int getQuantiteMoyen() {
		return quantityMoyen;
	}

	/**
	 * @param arrayList the moyensAddedToList to set
	 */
	public void setDonneesMoyensAddedToList(ArrayList<DemandeDeMoyenItem> arrayList) {
		this.moyensAddedToList = arrayList;
	}

	/**
	 * @return the moyensAddedToList
	 */
	public ArrayList<DemandeDeMoyenItem> getDonneesMoyensAddedToList() {
		return moyensAddedToList;
	}

} // Classe DemandeMoyensSavedInstanceState
