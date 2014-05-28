/**
 * 
 */
package com.istic.agetac.activities;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.model.IUser;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controllers.dao.EnvironnementsStaticDao;
import com.istic.agetac.controllers.dao.UserAvailableDao;
import com.istic.agetac.exceptions.UserNotFoundException;
import com.istic.agetac.fragments.PagerFragment;
import com.istic.agetac.fragments.PagerFragment.MODE;
import com.istic.agetac.model.EnvironnementsStatic;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.User;
import com.istic.agetac.model.UserAvailable;
import com.istic.agetac.model.receiver.AUserViewReceiver;
import com.istic.sit.framework.couch.CouchDBUtils;
import com.istic.sit.framework.couch.DataBaseCommunication;

/**
 * @author Christophe
 * 
 */
public class ContainerActivity extends FragmentActivity {

	public static final String MODE_EXTRA = "MODE_EXTRA";
	
	public static void launchActivity(PagerFragment.MODE mode, Context context) {
		Intent intent = new Intent(context, ContainerActivity.class);
		intent.putExtra(MODE_EXTRA, mode.toString());
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_container);
		

		//getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setHomeButtonEnabled(true);

		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			if (extras.getString(MODE_EXTRA).equals(MODE.CODIS.toString())){
				displayFragment(PagerFragment.newInstance(MODE.CODIS));
			}else if (extras.getString(MODE_EXTRA).equals(MODE.INTERVENANT.toString())){
				displayFragment(PagerFragment.newInstance(MODE.INTERVENANT));
			}
		}
	}

	private void displayFragment(Fragment fragment) {
		FrameLayout frameHome = (FrameLayout) findViewById(R.id.framelayoutContainer);

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		if (frameHome != null) {
			ft.replace(R.id.framelayoutContainer, fragment);
		}
		ft.commit();
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

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
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
