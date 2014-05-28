package com.istic.agetac.activities;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.model.IUser;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controler.adapter.InterventionAdapter;
import com.istic.agetac.controllers.dao.EnvironnementsStaticDao;
import com.istic.agetac.controllers.dao.UserAvailableDao;
import com.istic.agetac.exceptions.UserNotFoundException;
import com.istic.agetac.fragments.PagerFragment.MODE;
import com.istic.agetac.model.EnvironnementsStatic;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.User;
import com.istic.agetac.model.UserAvailable;
import com.istic.agetac.model.receiver.AUserViewReceiver;
import com.istic.sit.framework.couch.CouchDBUtils;
import com.istic.sit.framework.couch.DataBaseCommunication;

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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			break;
		case R.id.action_settings:
			break;
		case R.id.refresh:
			DataBaseCommunication.sendGet(new myAvailableUserDao());
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class myAvailableUserDao extends UserAvailableDao {

		@Override
		public void onResponse(UserAvailable users) {
			AgetacppApplication.setUserAvailable(users);
			DataBaseCommunication.sendGet(new myEnvironnementsStaticDao());
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.e("LoginActivity", error.toString());
		}

	}

	private class myEnvironnementsStaticDao extends EnvironnementsStaticDao {

		@Override
		public void onResponse(EnvironnementsStatic environnementsStatic) {
			AgetacppApplication.setEnvironnementsStatic(environnementsStatic);
			CouchDBUtils.getFromCouch(new UserViewReceiver(AgetacppApplication.getUser().getUsername(), AgetacppApplication.getUser().getPassword()));
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.e("LoginActivity", error.toString());
		}

	}
	
	private class UserViewReceiver extends AUserViewReceiver {

		public UserViewReceiver(String username, String password) {
			super(username, password);
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.e("LoginActivity", error.getMessage() == null ? error.toString() : error.getMessage());
		}

		@Override
		public void onResponse(List<Intervention> interventions) {
			if (!interventions.isEmpty()) {
				if (AgetacppApplication.getRole() == Role.intervenant) {
					AgetacppApplication.setIntervention(interventions.get(0));
					// Recherche de l'utilisateur dans l'intervention :
					try {
						AgetacppApplication.setUser(findIntervenant(interventions.get(0)));
					} catch (UserNotFoundException e) {
						Log.e("LoginActivity", e.getMessage());
						Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
					}
				} else {
					AgetacppApplication.setListIntervention(interventions);
					AgetacppApplication.setUser(interventions.get(0).getCodis());
				}
			}
		}

		private IUser findIntervenant(Intervention intervention) throws UserNotFoundException {
			for (User user : intervention.getIntervenants()) {
				if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
					return user;
				}
			}
			throw new UserNotFoundException();
		}
	}
}