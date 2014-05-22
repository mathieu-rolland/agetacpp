package com.istic.agetac.activities;

import java.util.List;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.istic.agetac.R;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.AMoyenListAdapter;
import com.istic.agetac.controler.adapter.MoyenListCodisAdapter;
import com.istic.agetac.fragments.DemandeDeMoyensFragment;
import com.istic.agetac.fragments.MessageFragment;
import com.istic.agetac.fragments.TableauMoyenFragment;
import com.istic.agetac.model.Codis;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Moyen;

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
		mAddress = (EditText) findViewById(R.id.activity_intervention_nameIntervention);
		mCode = (EditText) findViewById(R.id.activity_intervention_codeIntervention);		
		
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
		mInterventionCurrent.addMoyens(listMoyen);		
		mFragmentTableauDesMoyens.AddAllMoyen(listMoyen);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void ValidNewIntervention()
	{
		if(mCode.getText().toString().equals("") || mAddress.getText().toString().equals(""))
		{
			Toast.makeText(getApplicationContext(), "Remplissez les champs obligatoires", Toast.LENGTH_SHORT).show();
			return;
		}		

		mInterventionCurrent.setAdresse(mAddress.getText().toString());
		mInterventionCurrent.setCodeSinistre(mCode.getText().toString());		
		Codis codis = (Codis)AgetacppApplication.getUser();
		mInterventionCurrent.setCodis(codis);
		AgetacppApplication.getListIntervention().add(mInterventionCurrent);
		mInterventionCurrent.save();
		
		finish();
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
