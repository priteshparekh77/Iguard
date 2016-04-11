package com.example.design;

import com.example.androidlocation.HospitalFragment;
import com.example.androidlocation.LocationFragment;
import com.example.automatedtexts.AutomatedText;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * This class extends to FragmentPagerAdapter class
 * to provide views to the tab fragments.
 * @author Nirajan Thapa
 *
 */
public class TabsPagerAdapter extends FragmentPagerAdapter{

	public TabsPagerAdapter(FragmentManager fm) {
		// TODO Auto-generated constructor stub
		super(fm);
	}

	
	@Override
	public Fragment getItem(int index) {

		switch(index){
		case 0:
			return new AutomatedText();
		case 1:
			return new LocationFragment();
		
		
		case 2:
			return new HospitalFragment();
		}
		return null;
	}

	@Override
	/**
	 * @return the number of tabs
	 */
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

}
