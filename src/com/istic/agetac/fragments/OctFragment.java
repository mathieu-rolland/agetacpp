package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.istic.agetac.R;
import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.MoyenAdapter;
import com.istic.agetac.controler.adapter.SecteurAdapteurOct;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.OCT;
import com.istic.agetac.model.Secteur;
import com.istic.agetac.sync.oct.OctBroadcastReceiver;
import com.istic.agetac.sync.oct.OctServiceSynchronisation;
import com.istic.sit.framework.application.FrameworkApplication;
import com.istic.sit.framework.sync.PoolSynchronisation;

public class OctFragment extends Fragment {

	private transient Intervention intervention;
	private OCT oct;
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

	private List<IMoyen> listeMoyenS1;
	private List<IMoyen> listeMoyenS2;
	private List<IMoyen> listeMoyenS3;
	private List<IMoyen> listeMoyenS4;

	private Secteur secteur1;
	private Secteur secteur2;
	private Secteur secteur3;
	private Secteur secteur4;

	private EditText frequenceCodis;
	private EditText frequenceCosAsc;
	private EditText frequenceCosDesc;
	private EditText frequenceS1Asc;
	private EditText frequenceS1Desc;
	private EditText frequenceS2Asc;
	private EditText frequenceS2Desc;
	private EditText frequenceS3Asc;
	private EditText frequenceS3Desc;
	private EditText frequenceS4Asc;
	private EditText frequenceS4Desc;

	/** M�thode onCreate */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/** M�thode onCreateView */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		/** Chargement du layout */
		View view = inflater.inflate(R.layout.fragment_oct, container, false);
		intervention = AgetacppApplication.getIntervention();
		oct = intervention.getOct();
		listeSecteurs = intervention.getSecteurs();
		
		secteur1 = oct.getSecteur1();
		secteur2 = oct.getSecteur2();
		secteur3 = oct.getSecteur3();
		secteur4 = oct.getSecteur4();

		// Récupération des éléments spinner
		frequenceCodis = (EditText) view.findViewById(R.id.et_FreqCodis);
		frequenceCosAsc = (EditText) view.findViewById(R.id.et_FreqCos);
		frequenceCosDesc = (EditText) view.findViewById(R.id.et_FreqCos2);
		frequenceS1Asc = (EditText) view.findViewById(R.id.et_FreqS1ascend);
		frequenceS1Desc = (EditText) view.findViewById(R.id.et_FreqS1descend);
		frequenceS2Asc = (EditText) view.findViewById(R.id.et_FreqS2ascend);
		frequenceS2Desc = (EditText) view.findViewById(R.id.et_FreqS2descend);
		frequenceS3Asc = (EditText) view.findViewById(R.id.et_FreqS3ascend);
		frequenceS3Desc = (EditText) view.findViewById(R.id.et_FreqS3descend);
		frequenceS4Asc = (EditText) view.findViewById(R.id.et_FreqS4ascend);
		frequenceS4Desc = (EditText) view.findViewById(R.id.et_FreqS4descend);

		buttonSend = (Button) view.findViewById(R.id.fragment_OCT_button_send);
		spin1 = (Spinner) view.findViewById(R.id.spin_secteur1);
		spin2 = (Spinner) view.findViewById(R.id.spin_secteur2);
		spin3 = (Spinner) view.findViewById(R.id.spin_secteur3);
		spin4 = (Spinner) view.findViewById(R.id.spin_secteur4);


		// Apply the adapter to the spinner
		spin1.setAdapter(new SecteurAdapteurOct(this.getActivity(),
				listeSecteurs));
		spin2.setAdapter(new SecteurAdapteurOct(this.getActivity(),
				listeSecteurs));
		spin3.setAdapter(new SecteurAdapteurOct(this.getActivity(),
				listeSecteurs));
		spin4.setAdapter(new SecteurAdapteurOct(this.getActivity(),
				listeSecteurs));
		// Récupération des éléments listView
		lvListeS1 = (ListView) view.findViewById(R.id.lv_moyensSecteur1);
		lvListeS2 = (ListView) view.findViewById(R.id.lv_moyensSecteur2);
		lvListeS3 = (ListView) view.findViewById(R.id.lv_moyensSecteur3);
		lvListeS4 = (ListView) view.findViewById(R.id.lv_moyensSecteur4);


		// Initialisation des éléments de la vue
		frequenceCodis.setText(oct.getFrequenceCodis());
		frequenceCosAsc.setText(oct.getFrequenceCosAsc());
		frequenceCosDesc.setText(oct.getFrequenceCosDesc());
		frequenceS1Asc.setText(oct.getFrequenceS1Asc());
		frequenceS1Desc.setText(oct.getFrequenceS1Desc());
		frequenceS2Asc.setText(oct.getFrequenceS2Asc());
		frequenceS2Desc.setText(oct.getFrequenceS2Desc());
		frequenceS3Asc.setText(oct.getFrequenceS3Asc());
		frequenceS3Desc.setText(oct.getFrequenceS3Desc());
		frequenceS4Asc.setText(oct.getFrequenceS4Asc());
		frequenceS4Desc.setText(oct.getFrequenceS4Desc());

		spin1.setSelection(getSecteurFromIntervention(secteur1));
		spin2.setSelection(getSecteurFromIntervention(secteur2));
		spin3.setSelection(getSecteurFromIntervention(secteur3));
		spin4.setSelection(getSecteurFromIntervention(secteur4));
		
		// Initialisation avec les listes des moyens des secteurs.
		
		listeMoyenS1 = new ArrayList<IMoyen>();
		listeMoyenS2 = new ArrayList<IMoyen>();
		listeMoyenS3 = new ArrayList<IMoyen>();
		listeMoyenS4 = new ArrayList<IMoyen>();
		
		if (oct.getSecteur1() != null) {
			listeMoyenS1 = oct.getSecteur1().getMoyens();
		}

		if (oct.getSecteur2() != null) {
			listeMoyenS2 = oct.getSecteur2().getMoyens();
		}

		if (oct.getSecteur3() != null) {
			listeMoyenS3 = oct.getSecteur3().getMoyens();
		}

		if (oct.getSecteur4() != null) {
			listeMoyenS4 = oct.getSecteur4().getMoyens();
		}

		// Adaptateurs pour listes des moyens
		lvListeS1
		.setAdapter(new MoyenAdapter(this.getActivity(), listeMoyenS1));
		lvListeS2
		.setAdapter(new MoyenAdapter(this.getActivity(), listeMoyenS2));
		lvListeS3
		.setAdapter(new MoyenAdapter(this.getActivity(), listeMoyenS3));
		lvListeS4
		.setAdapter(new MoyenAdapter(this.getActivity(), listeMoyenS4));

		// Evênements
		buttonSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				send();
			}
		});

		spin1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
			    
			    oct.setSecteur1( OctFragment.this.listeSecteurs.get( position ));
			    
				listeMoyenS1 = oct.getSecteur1().getMoyens();
				lvListeS1.setAdapter(new MoyenAdapter(getActivity(),
						listeMoyenS1));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				secteur1 = new Secteur();
			}

		});

		spin2.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
			    
			    oct.setSecteur2( OctFragment.this.listeSecteurs.get( position ));
				listeMoyenS2 = oct.getSecteur2().getMoyens();
				lvListeS2.setAdapter(new MoyenAdapter(getActivity(),
						listeMoyenS2));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				secteur2 = new Secteur();
			}

		});

		spin3.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
			    oct.setSecteur3( OctFragment.this.listeSecteurs.get( position ));
				listeMoyenS3 = oct.getSecteur3().getMoyens();
				lvListeS3.setAdapter(new MoyenAdapter(getActivity(),
						listeMoyenS3));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				secteur3 = new Secteur();
			}

		});

		spin4.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
			    oct.setSecteur4( OctFragment.this.listeSecteurs.get( position ));
				listeMoyenS4 = oct.getSecteur4().getMoyens();
				lvListeS4.setAdapter(new MoyenAdapter(getActivity(),
						listeMoyenS4));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				secteur4 = new Secteur();
			}

		});

		// Start sync :
		receiver = new OctBroadcastReceiver(this);
		serviceSync = new OctServiceSynchronisation("Service oct sync");

		if( AgetacppApplication.ACTIVE_ALL_SYNCHRO 
				&& AgetacppApplication.ACTIVE_OCT_SYNCHRO ){
			PoolSynchronisation synchro = FrameworkApplication
					.getPoolSynchronisation();
			synchro.registerServiceSync(
					OctServiceSynchronisation.FILTER_OCT_RECEIVER, serviceSync,
					receiver);
		}

		/** LOG */
		Log.d("Marion", "Instanciations faites");

		return view;

	}

	private int getSecteurFromIntervention(Secteur secteur)
	{
		int ret = -1;
		for (Secteur sec : listeSecteurs) {
			if(sec.getId().equals(secteur.getId()))
			{
				ret = listeSecteurs.indexOf(sec);
			}
		}
		return ret;
		
	}
	private void send() {

		oct.setSecteur1(secteur1);
		oct.setSecteur2(secteur2);
		oct.setSecteur3(secteur3);
		oct.setSecteur4(secteur4);

		oct.setFrequenceCodis(frequenceCodis.getText().toString());
		oct.setFrequenceCosAsc(frequenceCosAsc.getText().toString());
		oct.setFrequenceCosDesc(frequenceCosDesc.getText().toString());
		oct.setFrequenceS1Asc(frequenceS1Asc.getText().toString());
		oct.setFrequenceS1Desc(frequenceS1Desc.getText().toString());
		oct.setFrequenceS2Asc(frequenceS2Asc.getText().toString());
		oct.setFrequenceS2Desc(frequenceS2Desc.getText().toString());
		oct.setFrequenceS3Asc(frequenceS3Asc.getText().toString());
		oct.setFrequenceS3Desc(frequenceS3Desc.getText().toString());
		oct.setFrequenceS4Asc(frequenceS4Asc.getText().toString());
		oct.setFrequenceS4Desc(frequenceS4Desc.getText().toString());

		oct.save();
	}

	/** M�thode onSaveInstanceState */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {

		// /** LOG */
		// Log.d("Marion", "Sauvegarde faite : " + sauvegarde + "");
		//
		// /** Sauvegarde */
		// savedInstanceState.putSerializable("sauvegarde", sauvegarde);
		super.onSaveInstanceState(savedInstanceState);
	}

	public void update(List<OCT> parcelableArrayList) {
		Log.d("OCT",
				" Receive data for sync : "
						+ (parcelableArrayList == null ? "null"
								: parcelableArrayList.size()));
	}
}
