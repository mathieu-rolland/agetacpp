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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.istic.agetac.R;

/**
 * @author Christophe
 *
 */
public class PagerFragment extends Fragment {
	private ViewPager mViewPager;
	private SuggestionsPagerAdapter mPagerAdapter;
	private final String[] titles = new String[]{"Sitac", "Moyens", "Messages"};
	
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
		
		mActivityActionBar.removeAllTabs();
		for(int i = 0 ; i < 3 ; i++) {
			mActivityActionBar.addTab(mActivityActionBar.newTab().setText(titles[i]).setTabListener(tabListener));
		}
		
		tabFragments = new ArrayList<Fragment>();
		tabFragments.add(DemandeDeMoyensFragment.newInstance());
		tabFragments.add(TableauMoyenFragment.newInstance());
		tabFragments.add(DemandeDeMoyensFragment.newInstance());
		/*
		tabFragments.add(SitacFragment.newInstance());
		tabFragments.add(DemandeDeMoyensFragment.newInstance());
		tabFragments.add(MessageFragment.newInstance());
		*/
		
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