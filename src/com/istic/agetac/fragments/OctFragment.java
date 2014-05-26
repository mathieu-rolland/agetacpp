package com.istic.agetac.fragments;



import java.util.ArrayList;
import java.util.List;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.MoyenAdapter;
import com.istic.agetac.controler.adapter.SecteurAdapter;
import com.istic.agetac.controler.adapter.SecteurAdapteurOct;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;
import com.istic.agetac.sync.message.MessageBroadcastReceiver;
import com.istic.agetac.sync.message.MessageServiceSynchronisation;
import com.istic.agetac.sync.oct.OctBroadcastReceiver;
import com.istic.agetac.sync.oct.OctServiceSynchronisation;
import com.istic.sit.framework.application.FrameworkApplication;
import com.istic.sit.framework.sync.PoolSynchronisation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class OctFragment extends Fragment{

	private transient Intervention intervention;
	
	private OctBroadcastReceiver receiver;
	private OctServiceSynchronisation serviceSync;
	
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
		
	private List<Secteur> listeSecteurs;
	
	private List<Moyen> listeMoyenS1;
	private List<Moyen> listeMoyenS2;
	private List<Moyen> listeMoyenS3;
	private List<Moyen> listeMoyenS4;
	
	private Secteur s1 = new Secteur();
	private Secteur s2 = new Secteur();
	private Secteur s3 = new Secteur();
	private Secteur s4 = new Secteur();
	
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
		intervention = AgetacppApplication.getIntervention();
		listeSecteurs = new ArrayList<Secteur>();
		listeSecteurs = intervention.getSecteurs();
		
		// Récupération des éléments 
		buttonSend = (Button) view.findViewById(R.id.fragment_OCT_button_send);
		spin1 = (Spinner) view.findViewById(R.id.spin_secteur1);
		spin2 = (Spinner) view.findViewById(R.id.spin_secteur2);
		spin3 = (Spinner) view.findViewById(R.id.spin_secteur3);
		spin4 = (Spinner) view.findViewById(R.id.spin_secteur4);
		
		lvListeS1 = (ListView) view.findViewById(R.id.lv_moyensSecteur1);
		
		lvListeS2 = (ListView)view.findViewById(R.id.lv_moyensSecteur2);
		lvListeS3 = (ListView)view.findViewById(R.id.lv_moyensSecteur3);
		lvListeS4 = (ListView)view.findViewById(R.id.lv_moyensSecteur4);
	
		// Adaptateurs pour listes des moyens
		listeMoyenS1 = new ArrayList<Moyen>();
		listeMoyenS2 = new ArrayList<Moyen>();
		listeMoyenS3 = new ArrayList<Moyen>();
		listeMoyenS4 = new ArrayList<Moyen>();
		lvListeS1.setAdapter(new MoyenAdapter(this.getActivity(),listeMoyenS1));
		lvListeS2.setAdapter(new MoyenAdapter(this.getActivity(),listeMoyenS2));
		lvListeS3.setAdapter(new MoyenAdapter(this.getActivity(),listeMoyenS3));
		lvListeS4.setAdapter(new MoyenAdapter(this.getActivity(),listeMoyenS4));
		
		
		// Apply the adapter to the spinner
		spin1.setAdapter(new SecteurAdapteurOct(this.getActivity(), listeSecteurs));
		spin2.setAdapter(new SecteurAdapteurOct(this.getActivity(), listeSecteurs));
		spin3.setAdapter(new SecteurAdapteurOct(this.getActivity(), listeSecteurs));
		spin4.setAdapter(new SecteurAdapteurOct(this.getActivity(), listeSecteurs));
		
		// Evênements
		buttonSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				send();
			}			
		});
		
		spin1.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        s1 = listeSecteurs.get(position); 
		        listeMoyenS1 = s1.getMoyens();
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		    	s1 = new Secteur();
		    }

		});
		
		spin2.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        s2 = listeSecteurs.get(position); 
		        listeMoyenS2 = s2.getMoyens();
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		    	s2 = new Secteur();
		    }

		});
		
		spin3.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        s3 = listeSecteurs.get(position); 
		        listeMoyenS3 = s3.getMoyens();
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		    	s3 = new Secteur();
		    }

		});
		
		spin4.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        s4 = listeSecteurs.get(position); 
		        listeMoyenS4 = s4.getMoyens();
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        s4 = new Secteur();
		    }

		});
	

		//Start sync :
		receiver = new OctBroadcastReceiver(this);
		serviceSync = new OctServiceSynchronisation("Service oct sync");
		
		PoolSynchronisation synchro = FrameworkApplication.getPoolSynchronisation();
		synchro.registerServiceSync(OctServiceSynchronisation.FILTER_OCT_RECEIVER, serviceSync, receiver);
	
		/** LOG */
		Log.d("Marion",  "Instanciations faites");
		
		return view;
		
	}
	
//	/** M�thode onActivityCreated */
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		
//		super.onCreate(savedInstanceState);			
//					
//		// R�cup�ration des �l�ments 
//		buttonSend = (Button) getActivity().findViewById(R.id.fragment_OCT_button_send);
//		spin1 = (Spinner) getActivity().findViewById(R.id.spin_secteur1);
//		spin2 = (Spinner) getActivity().findViewById(R.id.spin_secteur2);
//		spin3 = (Spinner) getActivity().findViewById(R.id.spin_secteur3);
//		spin4 = (Spinner) getActivity().findViewById(R.id.spin_secteur4);
//
//		lvListeS1 = (ListView)getActivity().findViewById(R.id.lv_moyensSecteur1);
//		lvListeS2 = (ListView)getActivity().findViewById(R.id.lv_moyensSecteur2);
//		lvListeS3 = (ListView)getActivity().findViewById(R.id.lv_moyensSecteur3);
//		lvListeS4 = (ListView)getActivity().findViewById(R.id.lv_moyensSecteur4);
//		
//	    String[] listeStrings = {"FPT1","FPT2","FPT3"};
//	    String[] listeStrings2 = {"VSAV1","VSAV2","VSAV3"};
//	 
//	    lvListeS1.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,listeStrings));
//	    lvListeS2.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,listeStrings2));
//	    lvListeS3.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,listeStrings));
//	    lvListeS4.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,listeStrings2));
//	    
//	    
//		// Create an ArrayAdapter using the string array and a default spinner layout
//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
//		        R.array.secteurs_array, R.layout.spinner_item);
//		// Specify the layout to use when the list of choices appears
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		// Apply the adapter to the spinner
//		spin1.setAdapter(adapter);
//		spin2.setAdapter(adapter);
//		spin3.setAdapter(adapter);
//		spin4.setAdapter(adapter);
//		
//		
//	    buttonSend.setOnClickListener(new OnClickListener() {
//			
//	    	@Override
//			public void onClick(View v) {
//				send();
//			}			
//		});
//	}
//	

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

	public void update(List parcelableArrayList) {
		// TODO Auto-generated method stub
		
	}
}
