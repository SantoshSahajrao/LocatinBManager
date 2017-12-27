package com.bitcode.hardik.profilemanager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitcode.hardik.profilemanager.AdapterClasses.AdapterPager;
import com.bitcode.hardik.profilemanager.R;

public class FragmentHomeScreen extends Fragment implements FragmentLocation.OnFragmentInteractionListener,FragmentProfile.OnFragmentInteractionListener,FragmentEvent.OnFragmentInteractionListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home_screen,null);

        mTabLayout= (TabLayout) view.findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Location").setIcon(R.drawable.icon_location));
        mTabLayout.addTab(mTabLayout.newTab().setText("Profiles").setIcon(R.drawable.icon_profile));
        mTabLayout.addTab(mTabLayout.newTab().setText("Events").setIcon(R.drawable.icon_event));

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager= (ViewPager) view.findViewById(R.id.pager);

        final PagerAdapter AdapterPager=new AdapterPager(getFragmentManager(),mTabLayout.getTabCount());
        mViewPager.setAdapter(AdapterPager);
        mViewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
