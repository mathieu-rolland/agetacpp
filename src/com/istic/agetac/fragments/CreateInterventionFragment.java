package com.istic.agetac.fragments;

import com.istic.agetac.R;
import com.istic.agetac.activities.CreateInterventionActivity.CurrentFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

public class CreateInterventionFragment extends Fragment{
	
	private Button mValidButton;
	private Button mSwitchButton;
	private FrameLayout mFragment;
	private CurrentFragment mCurrentFragment;
	
	public CreateInterventionFragment()
	{
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.activity_create_intervention, container, false);
		
	
		mValidButton= (Button) view.findViewById(R.id.activity_intervention_buttonValid);
		mSwitchButton = (Button) view.findViewById(R.id.activity_intervention_buttonSwitch);
		
		mFragment = (FrameLayout) view.findViewById(R.id.activity_intervention_frame_tableau);
        
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(mFragment != null) {
        	ft.replace(R.id.activity_intervention_frame_tableau, new TableauMoyenFragment());
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
		return container;	
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
		FragmentManager fm = getActivity().getSupportFragmentManager();
		Fragment fragment = null;
		
		if(mCurrentFragment==CurrentFragment.tableau)
		{
			fragment = new DemandeDeMoyensFragment();
			mCurrentFragment = CurrentFragment.ajoutMoyen;
		}
		else
		{
			fragment = new TableauMoyenFragment();
			mCurrentFragment = CurrentFragment.tableau;
		}
		
		
		FragmentTransaction ft = fm.beginTransaction();
		if (mFragment != null) {
			ft.replace(R.id.activity_intervention_frame_tableau,fragment);
			ft.addToBackStack(null);
		}
		ft.commit();
	}
	
}
