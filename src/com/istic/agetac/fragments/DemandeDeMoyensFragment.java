package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.activities.CreateInterventionActivity;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.DemandeDeMoyenGridViewAdapter;
import com.istic.agetac.controler.adapter.DemandeDeMoyenListAdapter;
import com.istic.agetac.controllers.dao.MoyensDao;
import com.istic.agetac.controllers.listeners.demandeDeMoyens.AddToList;
import com.istic.agetac.controllers.listeners.demandeDeMoyens.AutoCompleteField;
import com.istic.agetac.controllers.listeners.demandeDeMoyens.SpinnerVariation;
import com.istic.agetac.filters.FilterInputNumber;
import com.istic.agetac.model.CreationBase;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.TypeMoyen;
import com.istic.agetac.saveInstanceState.DemandeMoyensSavedInstanceState;
import com.istic.agetac.view.item.DemandeDeMoyenItem;
import com.istic.sit.framework.model.Representation;

/**
* Classe DemandeDeMoyensFragment : affiche la fenétre de demande des moyens et permet de créer une liste de demandes de moyens
* et de la soumettre ensuite.
* 
* @author Anthony LE MEE - 10003134
*/
public class DemandeDeMoyensFragment extends Fragment implements IViewReceiver<Moyen> {
	
	
	public static DemandeDeMoyensFragment newInstance() {
		DemandeDeMoyensFragment fragment = new DemandeDeMoyensFragment();
		return fragment;
	}
	
	/** Instances des modéles é utiliser */
	private MoyensDao mMoyens;
	
	/** Instances des controlers é utiliser */
	private AddToList 				cAddToList;					// Listener (Controler) sur le boutton d'ajout é la liste des moyens
	private SpinnerVariation 		cQuantiteMoyens;			// Listener (Controler) sur le choix de la quantité de moyens voulu via les boutons +/-
	private AutoCompleteField 		cAutresMoyens;				// Listener (Controler) sur le choix d'un autre moyen via le champs autre moyens
	
	/** éléments de la vue */
	private GridView 				gridViewMoyens; 			// GridView correspondant é l'ensemble des moyens
	private AutoCompleteTextView 	textViewAutresMoyens; 		// Champs d'auto-complétion pour la recherche d'un autre moyen 
	private Button 					buttonAddToList; 			// Boutton d'ajout du moyen é la liste 
	private Button 					buttonQuantityMore; 		// Augmente le nombre de moyens é ajouter 
	private Button 					buttonQuantityLess; 		// Diminue le nombre de moyens é ajoute
	private Button					buttonSend;
	private EditText 				editTextQuantity; 			// Nombre de ce moyen é ajouter é la liste 
	private ListView 				listViewMoyensToSend; 		// Liste des éléments ajouté et que l'on va envoyer au serveur 
	
	/** Données de sauvegarde au flip orientation */
	private DemandeMoyensSavedInstanceState sauvegarde = DemandeMoyensSavedInstanceState.getInstance(); // LastSave
	
	/** Données (récupérées via les modéles) é afficher dans la vue */
	private TypeMoyen[]							namesOfAllMoyens;			// Liste des noms des moyens que l'on chargera dans la vue
	private TypeMoyen[]							namesOfUsestMoyens;			// Liste (limitée au plus utilisés) des noms des moyens que l'on chargera dans la vue
	private ArrayList<DemandeDeMoyenItem>		allMoyenAddedToList;   		// Liste des moyens ajoutés é la liste de demande de moyens
	private DemandeDeMoyenListAdapter 			adapterListToSend;			// Liste des moyens de la liste de demande de moyens
	private TypeMoyen selectedTypeMoyen;
	
	private ArrayList<TypeMoyen> mTypeMoyen;
	
	private List<Moyen> mListMoyen;
	
	/**
	 * Méthode qui affiche un toast suite é la réception d'un message
	 * @param message
	 */
	public void onMessageReveive(String message) {
		try {
			Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
		}
	}
	
	/** Méthode onCreate */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
	}
	
	/** Méthode onCreateView */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
	   	/** Chargement du layout */
		View view = inflater.inflate(R.layout.fragment_demande_de_moyens, container, false);
		
		/** Instanciations des contrélers */
		this.cAddToList 		= new AddToList(this);
		this.cQuantiteMoyens 	= new SpinnerVariation(this);
		this.cAutresMoyens 		= new AutoCompleteField(this);
		
		/** Instanciations des modéles */
		this.mMoyens 			= new MoyensDao(this);
		this.mTypeMoyen = new ArrayList<TypeMoyen>();
		this.mTypeMoyen.add(TypeMoyen.VSAV);
		
		/** Récupérations des données via les modéles */
		this.mMoyens.findAll();
		
		/** Chargements des données dans les attributs correspondants */
		this.namesOfAllMoyens 			= toArray(this.mTypeMoyen);
		this.namesOfUsestMoyens 		= toArray(this.mTypeMoyen);
		this.allMoyenAddedToList		= new ArrayList<DemandeDeMoyenItem>();
		this.adapterListToSend 			= new DemandeDeMoyenListAdapter(this, android.R.layout.simple_list_item_1, this.allMoyenAddedToList);		
		
		/** LOG */
		Log.d("Antho",  "Instanciations faites");
		
		return view;
		
	}
	
	private TypeMoyen[] toArray(ArrayList<TypeMoyen> mTypeMoyen) {
		TypeMoyen[] typeMoyenArray = new TypeMoyen[mTypeMoyen.size()];
		for (TypeMoyen typeMoyen : mTypeMoyen) {
			typeMoyenArray[mTypeMoyen.indexOf(typeMoyen)] = typeMoyen;
		}
		return typeMoyenArray;
	}

	/** Méthode onActivityCreated */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);			
		
		// Récupération d'une sauvegarde pré-existante
		if (savedInstanceState != null) {
	        sauvegarde = (DemandeMoyensSavedInstanceState) savedInstanceState.getSerializable("sauvegarde");
	        Log.d("Antho",  sauvegarde.getIndiceMoyen() + "");
	    }
					
		// Récupération des éléments 
		buttonAddToList 		= (Button) getActivity().findViewById(R.id.demande_de_moyen_Button_AddToList);
		buttonQuantityMore 		= (Button) getActivity().findViewById(R.id.demande_de_moyen_Button_OneMore);
		buttonQuantityLess 		= (Button) getActivity().findViewById(R.id.demande_de_moyen_Button_OneLess);
		buttonSend				= (Button) getActivity().findViewById(R.id.demande_de_moyen_Button_SendList);
		textViewAutresMoyens 	= (AutoCompleteTextView) getActivity().findViewById(R.id.demande_de_moyen_AutoCompleteTextView_TextField);
		editTextQuantity 		= (EditText) getActivity().findViewById(R.id.demande_de_moyen_EditText_DefaultQuantity);
		gridViewMoyens 			= (GridView) getActivity().findViewById(R.id.demande_de_moyen_GridView);
		listViewMoyensToSend	= (ListView) getActivity().findViewById(R.id.demande_de_moyen_ListView);
		
		this.getTextViewAutresMoyens().setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
		    	TypeMoyen selection = (TypeMoyen)parent.getItemAtPosition(position);
		    	setSelectedTypeMoyen(selection);
		    }
		});
		
		// Ajout listener sur le boutton d'ajout à la liste des moyens
		buttonAddToList.setOnClickListener(this.cAddToList);
		
		// Ajout du filtre de domaine sur le champs numerique de quantite de moyens
		editTextQuantity.setFilters(new InputFilter[]{new FilterInputNumber("1", "100")});
		
		// Ajout listener sur + et -
		getBouttonAjoutQuantite().setOnClickListener(cQuantiteMoyens);
		getBouttonRetireQuantite().setOnClickListener(cQuantiteMoyens);

		// Creation de la grille de moyens
		getGridViewMoyens().setAdapter(new DemandeDeMoyenGridViewAdapter(this, this.namesOfUsestMoyens)); 
		
		// Création du champs d'auto-complétion pour la recherche de d'autres moyens
	    getTextViewAutresMoyens().addTextChangedListener(cAutresMoyens);
	    getTextViewAutresMoyens().setAdapter(new ArrayAdapter<TypeMoyen>(getActivity(), android.R.layout.simple_dropdown_item_1line, this.namesOfAllMoyens));
	    getTextViewAutresMoyens().setOnItemClickListener(cAutresMoyens);
	    
	    /*
	     * Remise en état suivant la sauvegarde
	     */
	    
	    // Liste des moyens demandés : Si la sauvegarde a du contenu qui nous manque alors on le charge
	    if (this.allMoyenAddedToList.size() < sauvegarde.getDonneesMoyensAddedToList().size()) {
	    	this.allMoyenAddedToList 	= sauvegarde.getDonneesMoyensAddedToList();
	    	this.adapterListToSend 		= new DemandeDeMoyenListAdapter(this, android.R.layout.simple_dropdown_item_1line, this.allMoyenAddedToList);
	    }
	
	 	// Création du ListView de la liste de moyens demandés	 
	    getListViewMoyensToSend().setAdapter(this.adapterListToSend);
	    
	    
	    buttonSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				send();
			}			
		});
	}
	
	private void send() {
		if(AgetacppApplication.getUser().getRole()==Role.codis)
		{
			mListMoyen = new ArrayList<Moyen>();
			Moyen m =new Moyen(TypeMoyen.VSAV);
			m.setHDemande(new Date());
			mListMoyen.add(m);
			Moyen m2 =new Moyen(TypeMoyen.VSAV);
			m2.setHDemande(new Date(2014,01,01));
			mListMoyen.add(m2);
			CreateInterventionActivity activityParent = (CreateInterventionActivity)getActivity();
			activityParent.updateMoyenIntervention(mListMoyen);
		}
	}
	
	/** Méthode onSaveInstanceState */
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
	public TypeMoyen[] getDonneesNomsAllMoyens() {
		return namesOfAllMoyens;
	}

	/**
	 * @param namesOfAllMoyens the namesOfAllMoyens to set
	 */
	public void setDonneesNomsAllMoyens(TypeMoyen[] donneesNomsAllMoyens) {
		this.namesOfAllMoyens = donneesNomsAllMoyens;
	}

	/**
	 * @return the namesOfUsestMoyens
	 */
	public TypeMoyen[] getDonneesNomsUsestMoyens() {
		return namesOfUsestMoyens;
	}

	/**
	 * @param namesOfUsestMoyens the namesOfUsestMoyens to set
	 */
	public void setDonneesNomsUsestMoyens(TypeMoyen[] donneesNomsUsestMoyens) {
		this.namesOfUsestMoyens = donneesNomsUsestMoyens;
	}

	/**
	 * @return the allMoyenAddedToList
	 */
	public ArrayList<DemandeDeMoyenItem> getAllMoyenAddedToList() {
		return allMoyenAddedToList;
	}

	/**
	 * @param allMoyenAddedToList the allMoyenAddedToList to set
	 */
	public void setAllMoyenAddedToList(
			ArrayList<DemandeDeMoyenItem> allMoyenAddedToList) {
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
		//this.namesOfAllMoyens 	= new String[]{"FPT","VSAV"};
		//this.namesOfUsestMoyens = new String[]{"FPT"};
		//((DemandeDeMoyenGridViewAdapter)getGridViewMoyens().getAdapter()).notifyDataSetChanged();
		//((ArrayAdapter)getTextViewAutresMoyens().getAdapter()).notifyDataSetChanged();
		onMessageReveive("Récupération des données MOYEN réussie !"); 
	}

	@Override
	public void notifyResponseFail(VolleyError error) {
		Log.e("Antho",  "FAIL to get datas MOYEN - " + error.toString());
		Log.e("Antho", error.getMessage());
		onMessageReveive("Impossible de récupérer les données MOYEN !");
	}

	/**
	 * @return the selectedTypeMoyen
	 */
	public TypeMoyen getSelectedTypeMoyen() {
		return selectedTypeMoyen;
	}

	/**
	 * @param selectedTypeMoyen the selectedTypeMoyen to set
	 */
	public void setSelectedTypeMoyen(TypeMoyen selectedTypeMoyen) {
		this.selectedTypeMoyen = selectedTypeMoyen;
	}

}// Class DemandeDeMoyensFragment
