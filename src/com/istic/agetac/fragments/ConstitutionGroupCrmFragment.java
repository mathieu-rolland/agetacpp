package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.ConstitutionGroupCrmListAdapter;
import com.istic.agetac.controler.adapter.ConstitutionGroupCrmListGroupAdapter;
import com.istic.agetac.model.Groupe;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.utils.SauvegardeStateCrm;
import com.istic.sit.framework.couch.APersitantRecuperator;
import com.istic.sit.framework.couch.CouchDBUtils;

/**
 * Classe DemandeDeMoyensFragment : affiche la fenétre de constitution des
 * groupes avec les moyen présents au CRM.
 * 
 * @author Anthony LE MEE - 10003134
 */
public class ConstitutionGroupCrmFragment extends Fragment {

	/** Instances des modéles é utiliser */
	private List<Moyen> 	mListMoyen;
	private List<Groupe> 	mListGroup;

	/** Instances des modéles formattés */
	private List<IMoyen> moyenListItemGroup;

	private ListView 	listViewMoyensAtCrm;
	private ListView 	listViewMoyensGroup;

	private ConstitutionGroupCrmListAdapter 		adapterMoyen;
	private ConstitutionGroupCrmListGroupAdapter 	adapterGroup;

	private boolean isGroupRetreived = false;
	private boolean isMoyenRetreived = false;
	
	/**
	 * Méthode qui affiche un toast suite é la réception d'un message
	 * @param message
	 */
	public void onMessageReveive(String message) {
		try {
			Toast.makeText(getActivity().getApplicationContext(), message,
					Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
		}
	}

	/**
	 * Methode qui créé une nouvelle instance du fragment
	 * 
	 * @return ConstitutionGroupCrmFragment
	 */
	public static ConstitutionGroupCrmFragment newInstance() {
		ConstitutionGroupCrmFragment fragment = new ConstitutionGroupCrmFragment();
		return fragment;
	}

	/** Méthode onCreate */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

	}

	/** Méthode onCreateView */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		/** Chargement du layout */
		View view = inflater.inflate(R.layout.fragment_consitution_groupe_crm,
				container, false);

		/** Instanciations des contrôlers */

		/** Instanciations des modéles */
		this.setmListMoyen(new ArrayList<Moyen>());
		this.setmListGroup(new ArrayList<Groupe>());

		/** Initialisation des modéles formatés */
		this.moyenListItemGroup = new ArrayList<IMoyen>();

		/** Instanciation des adapters */
		this.setAdapterMoyen(new ConstitutionGroupCrmListAdapter(this,
				android.R.layout.simple_dropdown_item_1line, this.mListMoyen));
		
		SauvegardeStateCrm.getInstance().setAdapterList(new ConstitutionGroupCrmListGroupAdapter(this,
				android.R.layout.simple_dropdown_item_1line,
				SauvegardeStateCrm.getInstance().getMoyenListItemOriginal()));
		
		/** Récupérations des données via les modèles */
		CouchDBUtils.getFromCouch(new MoyenRecuperator(AgetacppApplication
				.getIntervention().getId()));
		CouchDBUtils.getFromCouch(new GroupeRecuperator());
		
		/** LOG */
		Log.d("Antho", "Instanciations faites");

		return view;

	}

	/** Méthode onActivityCreated */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Récupération des éléments
		this.listViewMoyensAtCrm = (ListView) getActivity().findViewById(
				R.id.consitution_group_crm_listviewMoyensArrived);
		this.listViewMoyensAtCrm.setAdapter(this.adapterMoyen);
		this.listViewMoyensGroup = (ListView) getActivity().findViewById(
				R.id.consitution_group_crm_listGroup);
		this.listViewMoyensGroup.setAdapter(SauvegardeStateCrm.getInstance().getAdapterList());
	}

	/********************************************************************************************************/
	/** IViewReceiver methods */
	/********************************************************************************************************/

	public class MoyenRecuperator extends APersitantRecuperator<Moyen> {

		public MoyenRecuperator(String idIntervention) {
			super(Moyen.class, "agetacpp", "get_moyens_by_intervention",
					idIntervention);
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.e("Antho", "FAIL to get datas MOYEN - " + error.toString());
			Log.e("Antho", error.getMessage() == null ? "null" : error.getMessage() );
			onMessageReveive("Impossible de récupérer les données MOYEN !");
		}

		@Override
		public void onResponse(List<Moyen> moyens) {
			setMoyenRetreived(true);
			mListMoyen.addAll(moyens);
			adapterMoyen.notifyDataSetChanged();
			listViewMoyensAtCrm.setAdapter(adapterMoyen);
			onMessageReveive("Récupération des données MOYEN réussie !");
		}
	}
	
	public class GroupeRecuperator extends APersitantRecuperator<Groupe> {

		public GroupeRecuperator() {
			super(Groupe.class, "agetacpp", "get");
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.e("Antho", "FAIL to get datas GROUPE - " + error.toString());
			Log.e("Antho", error.getMessage() == null ? "null" : error.getMessage() );
			onMessageReveive("Impossible de récupérer les données GROUPE !");
		}

		@Override
		public void onResponse(List<Groupe> groupes) {
			
			setGroupRetreived(true);
			setmListGroup(groupes);
			
			SauvegardeStateCrm.getInstance().getMoyenListItemOriginal().clear();
			for(Groupe g : groupes) {
				
				SauvegardeStateCrm.getInstance().getMoyenListItemOriginal().add(g);
				List<Moyen> listMoyenOfGroup = g.getMoyens();
				for (Moyen moyen : listMoyenOfGroup) {
					SauvegardeStateCrm.getInstance().getMoyenListItemOriginal().add(moyen);
				}
				
			}
			
			SauvegardeStateCrm.getInstance().getAdapterList().notifyDataSetChanged();
			listViewMoyensGroup.setAdapter(SauvegardeStateCrm.getInstance().getAdapterList());
			onMessageReveive("Récupération des données GROUPE réussie !");
			
		}
	}
	
	/********************************************************************************************************/
	/** GETTEURS ET SETTEURS /
	 ********************************************************************************************************/

	/**
	 * @return the mListGroup
	 */
	public List<Groupe> getmListGroup() {
		return mListGroup;
	}

	/**
	 * @param mListGroup
	 *            the mListGroup to set
	 */
	public void setmListGroup(List<Groupe> mListGroup) {
		this.mListGroup = mListGroup;
	}

	/**
	 * @return the adapterGroup
	 */
	public ConstitutionGroupCrmListGroupAdapter getAdapterGroup() {
		return adapterGroup;
	}

	/**
	 * @param adapterGroup
	 *            the adapterGroup to set
	 */
	public void setAdapterGroup(
			ConstitutionGroupCrmListGroupAdapter adapterGroup) {
		this.adapterGroup = adapterGroup;
	}

	/**
	 * @return the adapterMoyen
	 */
	public ConstitutionGroupCrmListAdapter getAdapterMoyen() {
		return adapterMoyen;
	}

	/**
	 * @param adapterMoyen
	 *            the adapterMoyen to set
	 */
	public void setAdapterMoyen(ConstitutionGroupCrmListAdapter adapterMoyen) {
		this.adapterMoyen = adapterMoyen;
	}

	/**
	 * @return the mListMoyen
	 */
	public List<Moyen> getmListMoyen() {
		return mListMoyen;
	}

	/**
	 * @param mListMoyen
	 *            the mListMoyen to set
	 */
	public void setmListMoyen(List<Moyen> mListMoyen) {
		this.mListMoyen = mListMoyen;
	}

	/**
	 * @return the isGroupRetreived
	 */
	public boolean isGroupRetreived() {
		return isGroupRetreived;
	}

	/**
	 * @param isGroupRetreived the isGroupRetreived to set
	 */
	public void setGroupRetreived(boolean isGroupRetreived) {
		this.isGroupRetreived = isGroupRetreived;
	}

	/**
	 * @return the isMoyenRetreived
	 */
	public boolean isMoyenRetreived() {
		return isMoyenRetreived;
	}

	/**
	 * @param isMoyenRetreived the isMoyenRetreived to set
	 */
	public void setMoyenRetreived(boolean isMoyenRetreived) {
		this.isMoyenRetreived = isMoyenRetreived;
	}

}// Class DemandeDeMoyensFragment
