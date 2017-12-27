package com.bitcode.hardik.profilemanager.AdapterClasses;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bitcode.hardik.profilemanager.Fragments.FragmentEvent;
import com.bitcode.hardik.profilemanager.Fragments.FragmentLocation;
import com.bitcode.hardik.profilemanager.Fragments.FragmentProfile;

/**
 * Created by hardik on 5/12/17.
 */

public class AdapterPager extends FragmentStatePagerAdapter {
    int noOfTabs;

    public AdapterPager(FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                FragmentLocation location=new FragmentLocation();
                return location;
            case 1:
                FragmentProfile profiles=new FragmentProfile();
                return profiles;
            case 2:
                FragmentEvent events=new FragmentEvent();
                return events;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}