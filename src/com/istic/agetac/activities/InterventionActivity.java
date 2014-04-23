package com.istic.agetac.activities;

import com.istic.agetac.R;
import com.istic.agetac.fragments.TableauMoyenFragment;

import android.app.Activity;
import android.content.Intent;
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

public class InterventionActivity extends FragmentActivity{

	// Layout
	private Button validButton;
	private EditText nameIntervention;
	private EditText codeIntervention;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_intervention);
		validButton= (Button) findViewById(R.id.layout_intervention_buttonValidIntervention);
		nameIntervention =(EditText) findViewById(R.id.layout_intervention_nameIntervention);
		codeIntervention =(EditText) findViewById(R.id.layout_intervention_codeIntervention);
		
		FrameLayout frameHome = (FrameLayout) findViewById(R.id.layout_intervention_frameLayout_tableauMoyen);
        
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(frameHome != null) {
        	ft.replace(R.id.layout_intervention_frameLayout_tableauMoyen, new TableauMoyenFragment());
        }
        ft.commit();
		
		
		validButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        ValidNewIntervention();
		    }
		});
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
	
}
