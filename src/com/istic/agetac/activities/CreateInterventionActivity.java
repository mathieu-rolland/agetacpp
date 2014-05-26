package com.istic.agetac.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.istic.agetac.R;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.fragments.DemandeDeMoyensFragment;
import com.istic.agetac.fragments.TableauMoyenFragment;
import com.istic.agetac.model.Codis;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.Secteur;

public class CreateInterventionActivity extends FragmentActivity{
	
	public static void launchActivity(Context context) {
		Intent intent = new Intent(context, CreateInterventionActivity.class);
		context.startActivity(intent);
	}
	
	// Layout
	private Button mValidButton;
	private Button mSwitchButton;
	private FrameLayout mFragment;
	private EditText mAddress;
	private EditText mCode;
	private EditText mName;
	
	private TableauMoyenFragment mFragmentTableauDesMoyens;
	private DemandeDeMoyensFragment mFragmentDemandeMoyens;
	private CurrentFragment mCurrentFragment;
	
	private Intervention mInterventionCurrent; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_intervention);
		mValidButton= (Button) findViewById(R.id.activity_intervention_buttonValid);
		mSwitchButton = (Button) findViewById(R.id.activity_intervention_buttonSwitch);
		mAddress = (EditText) findViewById(R.id.activity_intervention_adresseIntervention);
		mCode = (EditText) findViewById(R.id.activity_intervention_codeIntervention);		
		mName = (EditText) findViewById( R.id.activity_intervention_nom );
		
		mFragmentDemandeMoyens = DemandeDeMoyensFragment.newInstance();
		mFragmentTableauDesMoyens = TableauMoyenFragment.newInstance(true);
		mInterventionCurrent = new Intervention();
		
		
		mFragment = (FrameLayout) findViewById(R.id.activity_intervention_frame_tableau);
        
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(mFragment != null) {
        	ft.replace(R.id.activity_intervention_frame_tableau, mFragmentTableauDesMoyens);
        	ft.addToBackStack(null);
        	mCurrentFragment = CurrentFragment.tableau;
        }
        ft.commit();
		
		
		mValidButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        ValidNewIntervention();
		    }
		});
		
		mSwitchButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        SwitchFragment();
		    }
		});
	}	
	
	public void updateMoyenIntervention(List<Moyen> listMoyen)
	{
	//	mInterventionCurrent.addMoyens(listMoyen);		
//		mFragmentTableauDesMoyens.AddAllMoyen(listMoyen);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void ValidNewIntervention()
	{
		if(mCode.getText().toString().equals("") || mAddress.getText().toString().equals("") || mName.getText().toString().equals(""))
		{
			Toast.makeText(getApplicationContext(), "Remplissez les champs obligatoires", Toast.LENGTH_SHORT).show();
			return;
		}		

		mInterventionCurrent.setAdresse(mAddress.getText().toString());
		mInterventionCurrent.setCodeSinistre(mCode.getText().toString());
		mInterventionCurrent.setNom( mName.getText().toString() );
		Codis codis = (Codis)AgetacppApplication.getUser();
		mInterventionCurrent.setCodis(codis);
		AgetacppApplication.getListIntervention().add(mInterventionCurrent);
		preconfigure();
		mInterventionCurrent.save();
		
		finish();
	}
	
	private void preconfigure()
    {
        List<Secteur> list = new ArrayList<Secteur>();
        Secteur secteur = new Secteur();
        secteur.setName( "INC" );
        secteur.setColor( Color.parseColor("#66CCFF" ));
        list.add( secteur );

        Secteur sap = new Secteur();
        sap.setName( "SAP" );
        sap.setColor( Color.parseColor("#FF1919" ));
        list.add( sap );

        Secteur alim = new Secteur();
        alim.setName( "ALIM" );
        alim.setColor( Color.parseColor("#0000FF" ));
        list.add( alim );

        Secteur sll = new Secteur();
        sll.setName( "SLL" );
        sll.setColor( Color.parseColor("#CCCCCC" ));
        list.add( sll );
        
        Secteur crm = new Secteur();
        crm.setName( "CRM" );
        crm.setColor( Color.parseColor("#FF0000" ));
        list.add( crm );
        
        mInterventionCurrent.setSecteurs( list );
    }
	
	private void SwitchFragment()
	{
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = null;
		
		if(mCurrentFragment==CurrentFragment.tableau)
		{
			fragment = mFragmentDemandeMoyens;
			mCurrentFragment = CurrentFragment.ajoutMoyen;
		}
		else
		{
			fragment = mFragmentTableauDesMoyens;
			mCurrentFragment = CurrentFragment.tableau;
		}
				
		FragmentTransaction ft = fm.beginTransaction();
		if (mFragment != null) {
			ft.replace(R.id.activity_intervention_frame_tableau,fragment);
			ft.addToBackStack(null);
		}
		ft.commit();
	}
		
	
	public enum CurrentFragment
	{
		tableau,
		ajoutMoyen
	}
	
}
