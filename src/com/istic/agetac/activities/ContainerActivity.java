/**
 * 
 */
package com.istic.agetac.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.istic.agetac.R;
import com.istic.agetac.fragments.PagerFragment;
import com.istic.sit.framework.view.MapFragment.MapMenuListener;

/**
 * @author Christophe
 * 
 */
public class ContainerActivity extends FragmentActivity implements MapMenuListener{

	public static void launchActivity(Context context) {
		Intent intent = new Intent(context, ContainerActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_container);
		

		//getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		displayFragment(new PagerFragment());
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

	@Override
	public void onInitializeMapMenu(GridView menu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCreateMapMenu(GridView menu) {
		// TODO Auto-generated method stub
		
	}

}
