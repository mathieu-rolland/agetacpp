package com.istic.agetac.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.istic.agetac.R;
import com.istic.agetac.fragments.TableauMoyenFragment;

public class InterventionActivity extends FragmentActivity{

	// Layout
	private Button validButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intervention);
		//validButton= (Button) findViewById(R.id.activity_intervention_buttonValidIntervention);
		
		FrameLayout frameHome = (FrameLayout) findViewById(R.id.activity_intervention_frame_tableau);
        
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(frameHome != null) {
        	ft.replace(R.id.activity_intervention_frame_tableau, new TableauMoyenFragment());
        	ft.addToBackStack(null);
        }
        ft.commit();
		
		
		/*validButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        ValidNewIntervention();
		    }
		});*/
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
