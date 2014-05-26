package com.istic.agetac.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.istic.agetac.R;
import com.istic.agetac.api.model.IUser;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controllers.dao.EnvironnementsStaticDao;
import com.istic.agetac.controllers.dao.UserAvailableDao;
import com.istic.agetac.fragments.PagerFragment.MODE;
import com.istic.agetac.model.EnvironnementsStatic;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.User;
import com.istic.agetac.model.UserAvailable;
import com.istic.sit.framework.couch.APersitantRecuperator;
import com.istic.sit.framework.couch.CouchDBUtils;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.JsonSerializer;

public class LoginActivity extends Activity {

	private static final int MIN_SIZE_PASSWORD = 4;

	// Values for email and password at the time of the login attempt.
	private String mUser;
	private String mPassword;

	// UI references.
	private EditText mUserView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Set up the login form.
		mUserView = (EditText) findViewById(R.id.activity_login_user);

		mPasswordView = (EditText) findViewById(R.id.activity_login_password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.activity_login_user
								|| id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});
		
		mLoginFormView = findViewById(R.id.activity_login_form);
		mLoginStatusView = findViewById(R.id.activity_login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.activity_login_status_message);

		findViewById(R.id.activity_login_btnLogin).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(
								mPasswordView.getWindowToken(), 0);
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {

		// Reset errors.
		mUserView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mUser = mUserView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < MIN_SIZE_PASSWORD) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		if (TextUtils.isEmpty(mUser)) {
			mUserView.setError(getString(R.string.error_field_required));
			focusView = mUserView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView
					.setText(R.string.activity_login_progress_login_in);
			showProgress(true);
			DataBaseCommunication.sendGet(new myAvailableUserDao());
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	private class UserViewReceiver extends APersitantRecuperator<Intervention> {

		private String username, password;
		
		public UserViewReceiver(String username, String password) {
			super(Intervention.class, "agetacpp", "connexion", username+"|"+password);
			this.username = username;
			this.password = password;
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			showProgress(false);
			Log.e("LoginActivity", error.getMessage() == null ? error.toString() : error.getMessage() );
		}
		
		@Override
		public void onResponse(JSONObject json){
			AgetacppApplication.setIntervention(null);
			AgetacppApplication.setListIntervention(null);
			AgetacppApplication.setRole(null);
			try {
				JSONArray row = (JSONArray) json.get("rows");
				List<Intervention> interventions = new ArrayList<Intervention>();
				for(int i=0; i<row.length(); i++){
					Log.d("d",row.toString());
					JSONObject o = row.getJSONObject(i);
					o = o.getJSONObject("value");
					String role = o.getString("role");
					if(role.equals("codis")){
						AgetacppApplication.setRole(Role.codis);
					}
					else{
						AgetacppApplication.setRole(Role.intervenant);
					}
					if((Boolean) o.get("ok")) {
						//User ayant une/des interventions
						JSONObject value = o.getJSONObject("res");
						Intervention interv = (Intervention) JsonSerializer.deserialize(Intervention.class, value);
						interventions.add(interv);
					}
					else{
						//User sans intervention
						JSONObject value = o.getJSONObject("res");
						User u = (User) JsonSerializer.deserialize(User.class, value);
						AgetacppApplication.setUser(u);
						if(u.getRole() == Role.codis){
							AgetacppApplication.setListIntervention(new ArrayList<Intervention>());
						}
					}
				}
				onResponse(interventions);
			} catch (JSONException e) {
				Log.e("LoginActivity", e.toString());
			}
		}

		@Override
		public void onResponse(List<Intervention> interventions) {
			mUserView.setError(null);
			mPasswordView.setError(null);
			if(!interventions.isEmpty()){
				if(AgetacppApplication.getRole() == Role.intervenant) {
					AgetacppApplication.setIntervention(interventions.get(0));
					//Recherche de l'utilisateur dans l'intervention :
					AgetacppApplication.setUser( findUser(interventions.get(0), username, password ));
				}
				else {
					AgetacppApplication.setListIntervention(interventions);
					AgetacppApplication.setUser(interventions.get(0).getCodis());
				}
			}
			showProgress(false);
			if(AgetacppApplication.getRole() != null) {
				// un utilisateur existant
				if(AgetacppApplication.getRole() == Role.codis) {
					CodisActivity.launchActivity(LoginActivity.this);
				}
				else {
					if(AgetacppApplication.getIntervention() == null) {
						// intervenant sans intervention => vacances
						ImageView imageView = new ImageView(LoginActivity.this);
						imageView.setImageResource(R.drawable.vacances);
						AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
						builder.setMessage(R.string.activity_login_intervenant_message);
						builder.setTitle(R.string.activity_login_intervenant_titre);
						builder.setCancelable(false);
						builder.setView(imageView);
						builder.setPositiveButton(R.string.activity_login_intervenant_button, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						});
						AlertDialog alert = builder.create();
						alert.show();
					}
					else {
						ContainerActivity.launchActivity(MODE.INTERVENANT, LoginActivity.this);
					}
				}
			}
			else {
				// pas un utilisateur
				mUserView.setError(getString(R.string.error_incorrect_login));
				mUserView.requestFocus();
			}
		}

		private IUser findUser(Intervention intervention, String username2,
				String password2) {
			for(User user : intervention.getIntervenants()){
				if( user.getName().equals(username) && user.getPassword().equals(password)) return user;
			}	
			return null;
		}
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
			CouchDBUtils.getFromCouch(new UserViewReceiver(mUser, mPassword));
		}
		
		@Override
		public void onErrorResponse(VolleyError error) {
			Log.e("LoginActivity", error.toString());
		}
		
	}
}
