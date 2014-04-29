package com.istic.agetac.activities;

import com.istic.agetac.R;
import com.istic.agetac.fragments.AddUserFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

public class UserActivity extends FragmentActivity{

	public static void launchActivity(Context context) {
		Intent intent = new Intent(context, UserActivity.class);
		context.startActivity(intent);
	}
	
	public UserActivity()
	{
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		FrameLayout frameHome = (FrameLayout) findViewById(R.id.user_activity_fragment);
        
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(frameHome != null) {
        	ft.replace(R.id.user_activity_fragment, new AddUserFragment());
        }
        ft.commit();
	}

		
}
