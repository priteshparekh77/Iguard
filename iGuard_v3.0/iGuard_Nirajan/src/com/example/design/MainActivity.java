package com.example.design;

import com.example.androidlocation.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter myAdapter;
	private ActionBar actionBar;
	
	private static final String[] TAB_NAMES={"SMS", "Location", "Hospital"};
	
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
			
		// Initializing the fields
		viewPager= (ViewPager) findViewById(R.id.pager);
		actionBar= getActionBar();
		myAdapter= new TabsPagerAdapter(getSupportFragmentManager());
		
		viewPager.setAdapter(myAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// Adding the tabs 
		for(String name: TAB_NAMES)
			actionBar.addTab(actionBar.newTab().setText(name).setTabListener(this));
	
		
	/**
	 * Implementation of a Listener to select a tab after swiping 
	 */
	viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			actionBar.setSelectedNavigationItem(position);
			
		}
		
	});
	
	}
	
	
	/**
	 * Implementation of the ActionBar.TabListener interface methods
	 */
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
	}

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
}
