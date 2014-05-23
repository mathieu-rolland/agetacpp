package com.istic.agetac.utils;

import java.util.ArrayList;
import java.util.List;

import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.controler.adapter.ConstitutionGroupCrmListGroupAdapter;

/**
 * Sauvegarde des données de la liste des groupes constitués
 * @author Anthony LE MÉE - 10003134
 */
public class SauvegardeStateCrm {

	private List<IMoyen> 							moyenListItemOriginal;
	private List<IMoyen> 							moyenListItemModified;
	private ConstitutionGroupCrmListGroupAdapter 	adapterGroup;
	private static SauvegardeStateCrm 				instance;
	
	private SauvegardeStateCrm () {
		this.setMoyenListItemOriginal(new ArrayList<IMoyen>());
	}
	
	public final static SauvegardeStateCrm getInstance() {
        if (SauvegardeStateCrm.instance == null) {
           synchronized(SauvegardeStateCrm.class) {
             if (SauvegardeStateCrm.instance == null) {
               SauvegardeStateCrm.instance = new SauvegardeStateCrm();
             }
           }
        }
        return SauvegardeStateCrm.instance;
    }
	
	public void setAdapterList(ConstitutionGroupCrmListGroupAdapter adapterGroup) {
		this.adapterGroup = adapterGroup;
	}
	
	public ConstitutionGroupCrmListGroupAdapter getAdapterList() {
		return this.adapterGroup;
	}

	/**
	 * @return the moyenListItemOriginal
	 */
	public List<IMoyen> getMoyenListItemOriginal() {
		return moyenListItemOriginal;
	}

	/**
	 * @param moyenListItemOriginal the moyenListItemOriginal to set
	 */
	public void setMoyenListItemOriginal(List<IMoyen> moyenListItemOriginal) {
		this.moyenListItemOriginal = moyenListItemOriginal;
		if (this.getMoyenListItemModified() == null || this.getMoyenListItemModified().size() == 0) {
			this.moyenListItemModified = moyenListItemOriginal;
		}
		else
		{
			for(IMoyen imoyen : this.getMoyenListItemOriginal()) {
				if (this.getMoyenListItemModified().indexOf(imoyen) < 0) {
					if (imoyen.isGroup()) {
						this.getMoyenListItemModified().add(imoyen);
					}
					else
					{
						this.getMoyenListItemModified().add(imoyen);
					}
				}
			}
		}
	}

	//public IMoyen 
	
	/**
	 * @return the moyenListItemModified
	 */
	public List<IMoyen> getMoyenListItemModified() {
		return moyenListItemModified;
	}

	/**
	 * @param moyenListItemModified the moyenListItemModified to set
	 */
	public void setMoyenListItemModified(List<IMoyen> moyenListItemModified) {
		this.moyenListItemModified = moyenListItemModified;
	}

} // class
