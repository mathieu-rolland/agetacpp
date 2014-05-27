package com.istic.agetac.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.istic.agetac.R;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.model.Codis;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.Moyen;
import com.istic.agetac.model.OCT;
import com.istic.agetac.model.Secteur;

public class CreateInterventionActivity extends FragmentActivity{
	
	public static void launchActivity(Context context) {
		Intent intent = new Intent(context, CreateInterventionActivity.class);
		context.startActivity(intent);
	}
	
	// Layout
	private Button mValidButton;
	private EditText mAddress;
	private EditText mCode;
	private EditText mName;
	
	private Intervention mInterventionCurrent; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_intervention);
		mValidButton= (Button) findViewById(R.id.activity_intervention_buttonValid);
		mAddress = (EditText) findViewById(R.id.activity_intervention_adresseIntervention);
		mCode = (EditText) findViewById(R.id.activity_intervention_codeIntervention);		
		mName = (EditText) findViewById( R.id.activity_intervention_nom );
		
		mInterventionCurrent = new Intervention();
		
		
		mValidButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        ValidNewIntervention();
		    }
		});
		
	}	
	
	public void updateMoyenIntervention(List<Moyen> listMoyen)
	{
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
        
        OCT oct = new OCT( sap, sll , crm, alim, "70", "70", "30", "30", "11", "30", "12", "30", "13", "30", "14" );
        mInterventionCurrent.setOct( oct );
        
        mInterventionCurrent.setSecteurs( list );
    }
	
	public enum CurrentFragment
	{
		tableau,
		ajoutMoyen
	}
	
}
