package com.istic.agetac.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.istic.agetac.R;

public class CreateInterventionFragment extends Fragment{
	
	private Button mValidButton;
	
	public CreateInterventionFragment()
	{
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.activity_create_intervention, container, false);
		
	
		mValidButton= (Button) view.findViewById(R.id.activity_intervention_buttonValid);
		
		mValidButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        ValidNewIntervention();
		    }
		});
		
		return container;	
	}
	
	private void ValidNewIntervention()
	{
	}
}
