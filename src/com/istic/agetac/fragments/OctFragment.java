package com.istic.agetac.fragments;



import java.util.ArrayList;
import java.util.List;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.app.AgetacppApplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class OctFragment extends Fragment{

	public static OctFragment newInstance() {
		OctFragment fragment = new OctFragment();
		return fragment;
	}
	
	/** �l�ments de la vue */
	private Button buttonSend;
	private Spinner spin1;
	private Spinner spin2;
	private Spinner spin3;
	private Spinner spin4;
	private ListView lvListeS1;
	private ListView lvListeS2;
	private ListView lvListeS3;
	private ListView lvListeS4;
		
	/** M�thode onCreate */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
	}
	
	/** M�thode onCreateView */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
	   	/** Chargement du layout */
		View view = inflater.inflate(R.layout.fragment_oct, container, false);
		
		/** Instanciations des contr�lers */
		
		
		/** Instanciations des mod�les */
//		
		/** R�cup�rations des donn�es via les mod�les */
//		this.mMoyens.findAll();
		
		/** Chargements des donn�es dans les attributs correspondants */
//		this.namesOfAllMoyens 			= toArray(this.mTypeMoyen);
//		this.namesOfUsestMoyens 		= toArray(this.mTypeMoyen);
//		this.allMoyenAddedToList		= new ArrayList<DemandeDeMoyensItem>();
//		this.adapterListToSend 			= new DemandeDeMoyenListAdapter(this, android.R.layout.simple_list_item_1, this.allMoyenAddedToList);		
//		
		/** LOG */
		Log.d("Marion",  "Instanciations faites");
		
		return view;
		
	}
	
	/** M�thode onActivityCreated */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);			
					
		// R�cup�ration des �l�ments 
		buttonSend = (Button) getActivity().findViewById(R.id.fragment_OCT_button_send);
		spin1 = (Spinner) getActivity().findViewById(R.id.spin_secteur1);
		spin2 = (Spinner) getActivity().findViewById(R.id.spin_secteur2);
		spin3 = (Spinner) getActivity().findViewById(R.id.spin_secteur3);
		spin4 = (Spinner) getActivity().findViewById(R.id.spin_secteur4);

		lvListeS1 = (ListView)getActivity().findViewById(R.id.lv_moyensSecteur1);
		lvListeS2 = (ListView)getActivity().findViewById(R.id.lv_moyensSecteur2);
		lvListeS3 = (ListView)getActivity().findViewById(R.id.lv_moyensSecteur3);
		lvListeS4 = (ListView)getActivity().findViewById(R.id.lv_moyensSecteur4);
		
	    String[] listeStrings = {"FPT1","FPT2","FPT3"};
	    String[] listeStrings2 = {"VSAV1","VSAV2","VSAV3"};
	 
	    lvListeS1.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,listeStrings));
	    lvListeS2.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,listeStrings2));
	    lvListeS3.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,listeStrings));
	    lvListeS4.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,listeStrings2));
	    
	    
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
		        R.array.secteurs_array, R.layout.spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spin1.setAdapter(adapter);
		spin2.setAdapter(adapter);
		spin3.setAdapter(adapter);
		spin4.setAdapter(adapter);
		
		
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
//			mListMoyen = new ArrayList<Moyen>();
//			Moyen m =new Moyen(TypeMoyen.VSAV);
//			m.setRepresentationOK(new Representation(R.drawable.fpt_1_alim));
//			m.setHDemande(new Date());
//			mListMoyen.add(m);
//			Moyen m2 =new Moyen(TypeMoyen.VSAV);
//			m2.setRepresentationOK(new Representation(R.drawable.fpt_2_inc));
//			m2.setHDemande(new Date(2014,01,01));
//			mListMoyen.add(m2);
//			CreateInterventionActivity activityParent = (CreateInterventionActivity)getActivity();
//			activityParent.updateMoyenIntervention(mListMoyen);
		}
	}

	/** M�thode onSaveInstanceState */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		
//		/** LOG */
//		Log.d("Marion", "Sauvegarde faite : " + sauvegarde + "");
//		
//		/** Sauvegarde */
//		savedInstanceState.putSerializable("sauvegarde", sauvegarde);
	    super.onSaveInstanceState(savedInstanceState);
		}
}
