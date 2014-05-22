package com.istic.agetac.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.InterventionAdapter;
import com.istic.agetac.controllers.dao.UserDao;
import com.istic.agetac.fragments.PagerFragment.MODE;
import com.istic.agetac.model.Codis;
import com.istic.agetac.model.Intervenant;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.User;

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
        
        Codis codis;
        if(AgetacppApplication.getListIntervention().isEmpty()){
        	codis =  (Codis)(AgetacppApplication.getUser());
        }
        else{
        	codis = AgetacppApplication.getListIntervention().get(0).getCodis();
        }
       
        mAdapter.addAll(codis.getInterventions());
		mAdapter.notifyDataSetChanged();
		
		UserDao users = new UserDao(new UsersViewReceiver());
		users.findAll();
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

	public class InterventionViewReceiver implements IViewReceiver<Intervention> {
		
		public InterventionViewReceiver(CodisActivity activity)
		{
		}
		
		@Override
		public void notifyResponseSuccess(List<Intervention> interventions) {
			
			mAdapter.addAll(interventions);
			mAdapter.notifyDataSetChanged();
			
			UserDao users = new UserDao(new UsersViewReceiver());
			users.findAll();
		}

		@Override
		public void notifyResponseFail(VolleyError error) {
			Toast.makeText(getApplicationContext(), "Impossible de r�cup�rer les interventions",	Toast.LENGTH_SHORT).show();
		}		
	}
	
	public class UsersViewReceiver implements IViewReceiver<User> {

		@Override
		public void notifyResponseSuccess(List<User> users) {
			HashMap<String, Intervention> hashMap = new HashMap<String, Intervention>();
			for (Intervention item : mAdapter.getAll()) {
				hashMap.put(item.getId()+"", item);
			}
			
			for (User user : users) {
				if(user.getRole()!=Role.codis)
				{
					Intervenant intervenant = (Intervenant)user;
					
					if( intervenant.getIntervention() != null
							&& hashMap.get(intervenant.getIntervention().getId()) != null ){
						hashMap.get(intervenant.getIntervention().getId()).addIntervenant(intervenant);
					}
				}
			}
		}

		@Override
		public void notifyResponseFail(VolleyError error) {
			// TODO Auto-generated method stub
		}
	}

}