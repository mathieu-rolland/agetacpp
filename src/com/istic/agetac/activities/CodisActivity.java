package com.istic.agetac.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.controler.adapter.InterventionAdapter;
import com.istic.agetac.controllers.dao.InterventionDao;
import com.istic.agetac.model.Intervention;

public class CodisActivity extends FragmentActivity{

	public static void launchActivity(Context context) {
		Intent intent = new Intent(context, CodisActivity.class);
		context.startActivity(intent);
	}
	
	private Button mValidButton;
	private ListView mListIntervention;
	private InterventionAdapter mAdapter;
	
	private List<Intervention> mList;
	
	public CodisActivity()
	{
		mList = new ArrayList<Intervention>();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_codis);
		
		mValidButton= (Button) findViewById(R.id.activity_codis_buttonValid);        
		mListIntervention = (ListView) findViewById(R.id.activity_codis_list_intervention);
		mAdapter = new InterventionAdapter(this, mList);
		mListIntervention.setAdapter(mAdapter);
		
        mValidButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        CreateIntervention();
		    }
		});		
        
        InterventionDao intervention = new InterventionDao(new InterventionViewReceiver());
        intervention.findAll();
        
        
       /* mList.add(new Intervention("istic", "feu"));
        mList.add(new Intervention("RU", "feu"));
        mList.add(new Intervention("istic", "SAP"));*/
        
	}
	
	public void CreateIntervention() {
		
	}		
	
	public class InterventionViewReceiver implements IViewReceiver<Intervention> {

		@Override
		public void notifyResponseSuccess(List<Intervention> interventions) {			
			mAdapter = new InterventionAdapter(getApplicationContext(), interventions);
			mListIntervention.setAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();
		}

		@Override
		public void notifyResponseFail(VolleyError error) {
			Toast.makeText(getApplicationContext(), "Impossible de récupérer les interventions",	Toast.LENGTH_SHORT).show();
		}		
	}
}