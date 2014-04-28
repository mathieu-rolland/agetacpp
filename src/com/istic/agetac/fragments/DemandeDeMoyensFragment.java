package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.controler.adapter.DemandeDeMoyenGridViewAdapter;
import com.istic.agetac.controler.adapter.DemandeDeMoyenListAdapter;
import com.istic.agetac.controllers.dao.MoyensDao;
import com.istic.agetac.controllers.listeners.demandeDeMoyens.AddToList;
import com.istic.agetac.controllers.listeners.demandeDeMoyens.AutoCompleteField;
import com.istic.agetac.controllers.listeners.demandeDeMoyens.SpinnerVariation;
import com.istic.agetac.filters.FilterInputNumber;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.saveInstanceState.DemandeMoyensSavedInstanceState;
import com.istic.agetac.view.item.DemandeDeMoyensItem;

/**
* Classe DemandeDeMoyensFragment : affiche la fen�tre de demande des moyens et permet de cr�er une liste de demandes de moyens
* et de la soumettre ensuite.
* 
* @author Anthony LE MEE - 10003134
*/
public class DemandeDeMoyensFragment extends Fragment implements IViewReceiver<Moyen> {
	
	
	public static DemandeDeMoyensFragment newInstance() {
		DemandeDeMoyensFragment fragment = new DemandeDeMoyensFragment();
		return fragment;
	}
	
	/** Instances des mod�les � utiliser */
	private MoyensDao mMoyens;
	
	/** Instances des controlers � utiliser */
	private AddToList 				cAddToList;					// Listener (Controler) sur le boutton d'ajout � la liste des moyens
	private SpinnerVariation 		cQuantiteMoyens;			// Listener (Controler) sur le choix de la quantit� de moyens voulu via les boutons +/-
	private AutoCompleteField 		cAutresMoyens;				// Listener (Controler) sur le choix d'un autre moyen via le champs autre moyens
	
	/** �l�ments de la vue */
	private GridView 				gridViewMoyens; 			// GridView correspondant � l'ensemble des moyens
	private AutoCompleteTextView 	textViewAutresMoyens; 		// Champs d'auto-compl�tion pour la recherche d'un autre moyen 
	private Button 					buttonAddToList; 			// Boutton d'ajout du moyen � la liste 
	private Button 					buttonQuantityMore; 		// Augmente le nombre de moyens � ajouter 
	private Button 					buttonQuantityLess; 		// Diminue le nombre de moyens � ajouter 
	private EditText 				editTextQuantity; 			// Nombre de ce moyen � ajouter � la liste 
	private ListView 				listViewMoyensToSend; 		// Liste des �l�ments ajout� et que l'on va envoyer au serveur 
	
	/** Donn�es de sauvegarde au flip orientation */
	private DemandeMoyensSavedInstanceState sauvegarde = DemandeMoyensSavedInstanceState.getInstance(); // LastSave
	
	/** Donn�es (r�cup�r�es via les mod�les) � afficher dans la vue */
	private String[]							namesOfAllMoyens;			// Liste des noms des moyens que l'on chargera dans la vue
	private String[] 							namesOfUsestMoyens;			// Liste (limit�e au plus utilis�s) des noms des moyens que l'on chargera dans la vue
	private ArrayList<DemandeDeMoyensItem>		allMoyenAddedToList;   		// Liste des moyens ajout�s � la liste de demande de moyens
	private DemandeDeMoyenListAdapter 			adapterListToSend;			// Liste des moyens de la liste de demande de moyens
	
	/**
	 * M�thode qui affiche un toast suite � la r�ception d'un message
	 * @param message
	 */
	public void onMessageReveive(String message) {
		Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	
	/** M�thode onCreate */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
	}
	
	/** M�thode onCreateView */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
	   	/** Chargement du layout */
		View view = inflater.inflate(R.layout.fragment_demande_de_moyens, container, false);
		
		/** Instanciations des contr�lers */
		this.cAddToList 		= new AddToList(this);
		this.cQuantiteMoyens 	= new SpinnerVariation(this);
		this.cAutresMoyens 		= new AutoCompleteField(this);
		
		/** Instanciations des mod�les */
		this.mMoyens 			= new MoyensDao(this);
		
		/** R�cup�rations des donn�es via les mod�les */
		this.mMoyens.findAll();
		
		/** Chargements des donn�es dans les attributs correspondants */
		this.namesOfAllMoyens 			= new String[]{"FPT","VSAV","TGD", "RPI","ESPA","POI","RGH"};
		this.namesOfUsestMoyens 		= new String[]{"FPT","VSAV", "TGD", "RPI","ESPA","POI"};
		this.allMoyenAddedToList		= new ArrayList<DemandeDeMoyensItem>();
		this.adapterListToSend 			= new DemandeDeMoyenListAdapter(this, android.R.layout.simple_list_item_1, this.allMoyenAddedToList);		
		
		/** LOG */
		Log.d("Antho",  "Instanciations faites");
		
		return view;
		
	}
	
	/** M�thode onActivityCreated */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);			
		
		// R�cup�ration d'une sauvegarde pr�-existante
		if (savedInstanceState != null) {
	        sauvegarde = (DemandeMoyensSavedInstanceState) savedInstanceState.getSerializable("sauvegarde");
	        Log.d("Antho",  sauvegarde.getIndiceMoyen() + "");
	    }
					
		// R�cup�ration des �l�ments 
		buttonAddToList 		= (Button) getActivity().findViewById(R.id.demande_de_moyen_Button_AddToList);
		buttonQuantityMore 		= (Button) getActivity().findViewById(R.id.demande_de_moyen_Button_OneMore);
		buttonQuantityLess 		= (Button) getActivity().findViewById(R.id.demande_de_moyen_Button_OneLess);
		textViewAutresMoyens 	= (AutoCompleteTextView) getActivity().findViewById(R.id.demande_de_moyen_AutoCompleteTextView_TextField);
		editTextQuantity 		= (EditText) getActivity().findViewById(R.id.demande_de_moyen_EditText_DefaultQuantity);
		gridViewMoyens 			= (GridView) getActivity().findViewById(R.id.demande_de_moyen_GridView);
		listViewMoyensToSend	= (ListView) getActivity().findViewById(R.id.demande_de_moyen_ListView);
		
		// Ajout listener sur le boutton d'ajout � la liste des moyens
		buttonAddToList.setOnClickListener(this.cAddToList);
		
		// Ajout du filtre de domaine sur le champs numerique de quantite de moyens
		editTextQuantity.setFilters(new InputFilter[]{new FilterInputNumber("1", "100")});
		
		// Ajout listener sur + et -
		getBouttonAjoutQuantite().setOnClickListener(cQuantiteMoyens);
		getBouttonRetireQuantite().setOnClickListener(cQuantiteMoyens);

		// Creation de la grille de moyens
		getGridViewMoyens().setAdapter(new DemandeDeMoyenGridViewAdapter(this, this.namesOfUsestMoyens)); 
		
		// Cr�ation du champs d'auto-compl�tion pour la recherche de d'autres moyens
	    getTextViewAutresMoyens().addTextChangedListener(cAutresMoyens);
	    getTextViewAutresMoyens().setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, this.namesOfAllMoyens));
	    getTextViewAutresMoyens().setOnItemClickListener(cAutresMoyens);
	    
	    /*
	     * Remise en �tat suivant la sauvegarde
	     */
	    
	    // Liste des moyens demand�s : Si la sauvegarde a du contenu qui nous manque alors on le charge
	    if (this.allMoyenAddedToList.size() < sauvegarde.getDonneesMoyensAddedToList().size()) {
	    	this.allMoyenAddedToList 	= sauvegarde.getDonneesMoyensAddedToList();
	    	this.adapterListToSend 		= new DemandeDeMoyenListAdapter(this, android.R.layout.simple_dropdown_item_1line, this.allMoyenAddedToList);
	    }
	
	 	// Cr�ation du ListView de la liste de moyens demand�s	 
	    getListViewMoyensToSend().setAdapter(this.adapterListToSend);
	    
	}
	
	/** M�thode onSaveInstanceState */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		
		/** LOG */
		Log.d("Antho", "Sauvegarde faite : " + sauvegarde + "");
		
		/** Sauvegarde */
		savedInstanceState.putSerializable("sauvegarde", sauvegarde);
	    super.onSaveInstanceState(savedInstanceState);
	    
	}
    
    /********************************************************************************************************/
    /** GETTEURS ET SETTEURS
    /********************************************************************************************************/

	/**
	 * @return the cAjoutToList
	 */
	public AddToList getcAjoutToList() {
		return cAddToList;
	}

	/**
	 * @param cAjoutToList the cAjoutToList to set
	 */
	public void setcAjoutToList(AddToList cAjoutToList) {
		this.cAddToList = cAjoutToList;
	}

	/**
	 * @return the cQuantiteMoyens
	 */
	public SpinnerVariation getcQuantiteMoyens() {
		return cQuantiteMoyens;
	}

	/**
	 * @param cQuantiteMoyens the cQuantiteMoyens to set
	 */
	public void setcQuantiteMoyens(SpinnerVariation cQuantiteMoyens) {
		this.cQuantiteMoyens = cQuantiteMoyens;
	}

	/**
	 * @return the cAutresMoyens
	 */
	public AutoCompleteField getcAutresMoyens() {
		return cAutresMoyens;
	}

	/**
	 * @param cAutresMoyens the cAutresMoyens to set
	 */
	public void setcAutresMoyens(AutoCompleteField cAutresMoyens) {
		this.cAutresMoyens = cAutresMoyens;
	}

	/**
	 * @return the gridViewMoyens
	 */
	public GridView getGridViewMoyens() {
		return gridViewMoyens;
	}

	/**
	 * @param gridViewMoyens the gridViewMoyens to set
	 */
	public void setGridViewMoyens(GridView gridViewMoyens) {
		this.gridViewMoyens = gridViewMoyens;
	}

	/**
	 * @return the textViewAutresMoyens
	 */
	public AutoCompleteTextView getTextViewAutresMoyens() {
		return textViewAutresMoyens;
	}

	/**
	 * @param textViewAutresMoyens the textViewAutresMoyens to set
	 */
	public void setTextViewAutresMoyens(AutoCompleteTextView textViewAutresMoyens) {
		this.textViewAutresMoyens = textViewAutresMoyens;
	}

	/**
	 * @return the buttonAddToList
	 */
	public Button getBouttonAjoutMoyenToList() {
		return buttonAddToList;
	}

	/**
	 * @param buttonAddToList the buttonAddToList to set
	 */
	public void setBouttonAjoutMoyenToList(Button bouttonAjoutMoyenToList) {
		this.buttonAddToList = bouttonAjoutMoyenToList;
	}

	/**
	 * @return the buttonQuantityMore
	 */
	public Button getBouttonAjoutQuantite() {
		return buttonQuantityMore;
	}

	/**
	 * @param buttonQuantityMore the buttonQuantityMore to set
	 */
	public void setBouttonAjoutQuantite(Button bouttonAjoutQuantite) {
		this.buttonQuantityMore = bouttonAjoutQuantite;
	}

	/**
	 * @return the buttonQuantityLess
	 */
	public Button getBouttonRetireQuantite() {
		return buttonQuantityLess;
	}

	/**
	 * @param buttonQuantityLess the buttonQuantityLess to set
	 */
	public void setBouttonRetireQuantite(Button bouttonRetireQuantite) {
		this.buttonQuantityLess = bouttonRetireQuantite;
	}

	/**
	 * @return the editTextQuantity
	 */
	public EditText getEditTextNombreMoyens() {
		return editTextQuantity;
	}

	/**
	 * @param editTextQuantity the editTextQuantity to set
	 */
	public void setEditTextNombreMoyens(EditText editTextNombreMoyens) {
		this.editTextQuantity = editTextNombreMoyens;
	}

	/**
	 * @return the listViewMoyensToSend
	 */
	public ListView getListViewMoyensToSend() {
		return listViewMoyensToSend;
	}

	/**
	 * @param listViewMoyensToSend the listViewMoyensToSend to set
	 */
	public void setListViewMoyensToSend(ListView listViewMoyensToSend) {
		this.listViewMoyensToSend = listViewMoyensToSend;
	}

	/**
	 * @return the sauvegarde
	 */
	public DemandeMoyensSavedInstanceState getSauvegarde() {
		return sauvegarde;
	}

	/**
	 * @param sauvegarde the sauvegarde to set
	 */
	public void setSauvegarde(DemandeMoyensSavedInstanceState sauvegarde) {
		this.sauvegarde = sauvegarde;
	}

	/**
	 * @return the namesOfAllMoyens
	 */
	public String[] getDonneesNomsAllMoyens() {
		return namesOfAllMoyens;
	}

	/**
	 * @param namesOfAllMoyens the namesOfAllMoyens to set
	 */
	public void setDonneesNomsAllMoyens(String[] donneesNomsAllMoyens) {
		this.namesOfAllMoyens = donneesNomsAllMoyens;
	}

	/**
	 * @return the namesOfUsestMoyens
	 */
	public String[] getDonneesNomsUsestMoyens() {
		return namesOfUsestMoyens;
	}

	/**
	 * @param namesOfUsestMoyens the namesOfUsestMoyens to set
	 */
	public void setDonneesNomsUsestMoyens(String[] donneesNomsUsestMoyens) {
		this.namesOfUsestMoyens = donneesNomsUsestMoyens;
	}

	/**
	 * @return the allMoyenAddedToList
	 */
	public ArrayList<DemandeDeMoyensItem> getAllMoyenAddedToList() {
		return allMoyenAddedToList;
	}

	/**
	 * @param allMoyenAddedToList the allMoyenAddedToList to set
	 */
	public void setAllMoyenAddedToList(
			ArrayList<DemandeDeMoyensItem> allMoyenAddedToList) {
		this.allMoyenAddedToList = allMoyenAddedToList;
	}

	/**
	 * @return the adapterListToSend
	 */
	public DemandeDeMoyenListAdapter getAdapterListToSend() {
		return adapterListToSend;
	}

	/**
	 * @param adapterListToSend the adapterListToSend to set
	 */
	public void setAdapterListToSend(DemandeDeMoyenListAdapter adapterListToSend) {
		this.adapterListToSend = adapterListToSend;
	}

	/**
	 * @return the mMoyens
	 */
	public MoyensDao getmMoyens() {
		return mMoyens;
	}

	/**
	 * @param mMoyens the mMoyens to set
	 */
	public void setmMoyens(MoyensDao mMoyens) {
		this.mMoyens = mMoyens;
	}

	/********************************************************************************************************/
    /** IViewReceiver methods
    /********************************************************************************************************/

	@Override
	public void notifyResponseSuccess(List<Moyen> objects) {
		this.namesOfAllMoyens 	= new String[]{"FPT","VSAV"};
		this.namesOfUsestMoyens = new String[]{"FPT"};
		((DemandeDeMoyenGridViewAdapter)getGridViewMoyens().getAdapter()).notifyDataSetChanged();
		((ArrayAdapter)getTextViewAutresMoyens().getAdapter()).notifyDataSetChanged();
		onMessageReveive("R�cup�ration des donn�es MOYEN r�ussie !"); 
	}

	@Override
	public void notifyResponseFail(VolleyError error) {
		Log.e("Antho",  "FAIL to get datas MOYEN - " + error.toString());
		Log.e("Antho", error.getMessage());
		onMessageReveive("Impossible de r�cup�rer les donn�es MOYEN !");
	}

}// Class DemandeDeMoyensFragment
