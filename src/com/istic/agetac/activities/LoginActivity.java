package com.istic.agetac.activities;

import java.util.List;

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
import com.istic.agetac.api.communication.IViewReceiver;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.agetac.controllers.dao.UserDao;
import com.istic.agetac.fragments.PagerFragment.MODE;
import com.istic.agetac.model.CreationBase;
import com.istic.agetac.model.Intervenant;
import com.istic.agetac.model.User;

public class LoginActivity extends Activity {

	private static final int MIN_SIZE_PASSWORD = 4;
	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserDao mAuthTask = null;

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
		
		CreationBase.recupIntervention("b3bf73f3-99cf-40df-b6ca-110beb97d190");
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
			mAuthTask = new UserDao(new UserViewReceiver());
			mAuthTask.findAll();
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
	public class UserViewReceiver implements IViewReceiver<User> {

		@Override
		public void notifyResponseSuccess(List<User> objects) {
			mUserView.setError(null);
			mPasswordView.setError(null);
			boolean find = false;
			for(User user : objects){
				if(user.getUsername().equals(mUser)){
					find = true;
					showProgress(false);
					if(user.getPassword().equals(mPassword)){
						AgetacppApplication.setUser(user);
						if(user.getRole().equals(Role.codis)){
							CodisActivity.launchActivity(LoginActivity.this);
						}
						else if(user.getRole().equals(Role.intervenant)){
							Intervenant intervenant = (Intervenant)user;
							if(intervenant.getIntervention() == null){
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
							else{
								ContainerActivity.launchActivity(MODE.INTERVENANT, LoginActivity.this);
							}
						}
					}
					else{
						mPasswordView.setError(getString(R.string.error_incorrect_password));
						mPasswordView.requestFocus();
					}
				}
			}
			if(!find){
				showProgress(false);
				mUserView.setError(getString(R.string.error_incorrect_login));
				mUserView.requestFocus();
			}
		}

		@Override
		public void notifyResponseFail(VolleyError error) {
			showProgress(false);
			Log.e("LoginActivity", error.getMessage() == null ? error.toString() : error.getMessage() );
		}
		
	}
}
