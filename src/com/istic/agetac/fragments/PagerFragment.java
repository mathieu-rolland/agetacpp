/**
 * 
 */
package com.istic.agetac.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.istic.agetac.R;
import com.istic.agetac.activities.MessageActivity;

/**
 * @author Christophe
 *
 */
public class PagerFragment extends Fragment {
	
	private static final String ARGS_MODE = "args_mode";
	
	public static enum MODE {
		CODIS, INTERVENANT
	}
	
	public static PagerFragment newInstance(MODE mode) {
		PagerFragment fragment = new PagerFragment();
		Bundle args = new Bundle();
		args.putString(ARGS_MODE, mode.toString());
		fragment.setArguments(args);
		return fragment;
	}
	
	private ViewPager mViewPager;
	private SuggestionsPagerAdapter mPagerAdapter;
	private final String[] titlesCodis = new String[]{"Creation Intervention"};
	private final String[] titlesIntervenant = new String[]{"Sitac", "Tableau des Moyens", "Messages"};
	
	String[] titles = new String[]{"init"};
	
	// Fragments
	private List<Fragment> tabFragments;
	
	private ActionBar mActivityActionBar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.fragment_pager, null);
		
		mActivityActionBar = getActivity().getActionBar();
		mActivityActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		mViewPager = (ViewPager) view.findViewById(R.id.pager);
		
		/*PagerTabStrip pagerTabStrip = (PagerTabStrip) view.findViewById(R.id.pager_header);
		pagerTabStrip.setDrawFullUnderline(true);
		pagerTabStrip.setTabIndicatorColor(Color.RED);*/
		
		TabListener tabListener = new TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				mViewPager.setCurrentItem(tab.getPosition());
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {}
		};
		
		tabFragments = new ArrayList<Fragment>();
		Bundle args = getArguments();
		if(args != null) {
			String modeStr = args.getString(ARGS_MODE);
			if(modeStr.equals(MODE.CODIS.toString())) {
				/*titles = titlesCodis;
				tabFragments.add(InterventionFragment.newInstance());*/
			} else if(modeStr.equals(MODE.INTERVENANT.toString())) {
				titles = titlesIntervenant;
				tabFragments.add(SitacFragment.newInstance());
				tabFragments.add(TableauMoyenFragment.newInstance());
				tabFragments.add(MessageActivity.newInstance());
			} 
		}
		
		mActivityActionBar.removeAllTabs();
		for(int i = 0 ; i < 3 ; i++) {
			mActivityActionBar.addTab(mActivityActionBar.newTab().setText(titles[i]).setTabListener(tabListener));
		}
		
		mViewPager.setOffscreenPageLimit(tabFragments.size());
		mPagerAdapter = new SuggestionsPagerAdapter(getChildFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				mActivityActionBar.setSelectedNavigationItem(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return view;
	}
	
	public class SuggestionsPagerAdapter extends FragmentStatePagerAdapter {

		private static final int PAGE_COUNT = 3;
		
		public SuggestionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			return tabFragments.get(i);
		}

		@Override
		public int getCount() {
			return PAGE_COUNT;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}
		
		@Override
		public int getItemPosition(Object object) {
			return PagerAdapter.POSITION_NONE;
		}
	}
}