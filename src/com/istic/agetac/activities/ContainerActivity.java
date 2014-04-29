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
import android.view.Window;
import android.widget.FrameLayout;

import com.istic.agetac.R;
import com.istic.agetac.fragments.PagerFragment;
import com.istic.agetac.fragments.PagerFragment.MODE;
import com.istic.sit.framework.view.MapFragment.MapMenuListener;

/**
 * @author Christophe
 * 
 */
public class ContainerActivity extends FragmentActivity implements MapMenuListener{

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
		

		//getActionBar().setDisplayHomeAsUpEnabled(true);
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

}
