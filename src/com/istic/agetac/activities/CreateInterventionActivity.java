package com.istic.agetac.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.istic.agetac.R;
import com.istic.agetac.fragments.DemandeDeMoyensFragment;
import com.istic.agetac.fragments.MessageFragment;
import com.istic.agetac.fragments.TableauMoyenFragment;

public class CreateInterventionActivity extends FragmentActivity{
	
	public static void launchActivity(Context context) {
		Intent intent = new Intent(context, CreateInterventionActivity.class);
		context.startActivity(intent);
	}
	
	// Layout
	private Button mValidButton;
	private Button mSwitchButton;
	private FrameLayout mFragment;
	private Fragment mFragmentTableauDesMoyens;
	private Fragment mFragmentDemandeMoyens;
	private CurrentFragment mCurrentFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_intervention);
		mValidButton= (Button) findViewById(R.id.activity_intervention_buttonValid);
		mSwitchButton = (Button) findViewById(R.id.activity_intervention_buttonSwitch);
		
		mFragmentDemandeMoyens = new DemandeDeMoyensFragment();
		mFragmentTableauDesMoyens = new TableauMoyenFragment();
		
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void ValidNewIntervention()
	{
		/*Intent intent = new Intent(this, SecondActivity.class);
		Bundle b = new Bundle();
		b.putInt("key", 1); //Your id
		intent.putExtras(b); //Put your id to your next Intent
		startActivity(intent);
		finish();*/
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
