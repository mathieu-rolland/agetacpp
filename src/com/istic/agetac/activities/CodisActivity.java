package com.istic.agetac.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.istic.agetac.R;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.InterventionAdapter;
import com.istic.agetac.fragments.PagerFragment.MODE;
import com.istic.agetac.model.Intervention;

public class CodisActivity extends FragmentActivity implements OnItemClickListener{

	public static void launchActivity(Context context) {
		Intent intent = new Intent(context, CodisActivity.class);
		context.startActivity(intent);
	}
	
	private Button mValidButton;
	private ListView mListIntervention;
	private InterventionAdapter mAdapter;

	
	public CodisActivity()
	{
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_codis);
		
		mValidButton= (Button) findViewById(R.id.activity_codis_buttonValid);        
		mListIntervention = (ListView) findViewById(R.id.activity_codis_list_intervention);
		mAdapter = new InterventionAdapter(this);
		mListIntervention.setAdapter(mAdapter);
		
        mValidButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        CreateIntervention();
		    }
		});
        
        mListIntervention.setOnItemClickListener(this);
        mAdapter.addAll(AgetacppApplication.getListIntervention());
		mAdapter.notifyDataSetChanged();
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if(mAdapter!=null)
		{
			mAdapter.clear();
		}
		else
		{
			mAdapter = new InterventionAdapter(getApplicationContext());
		}
			mAdapter.addAll(AgetacppApplication.getListIntervention());
	};
	
	 @Override
     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intervention intervention =  (Intervention) mListIntervention.getItemAtPosition(position);
     	AgetacppApplication.setIntervention(intervention);
		ContainerActivity.launchActivity(MODE.CODIS, this);
     }
	
	public void CreateIntervention() {
		CreateInterventionActivity.launchActivity(this);
	}
}