package com.bitcode.hardik.profilemanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bitcode.hardik.profilemanager.Fragments.FragmentEvent;
import com.bitcode.hardik.profilemanager.Fragments.FragmentHomeScreen;
import com.bitcode.hardik.profilemanager.Fragments.FragmentLocation;
import com.bitcode.hardik.profilemanager.Fragments.FragmentProfile;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public FragmentManager fragmentManager;
    FragmentHomeScreen mFragmentHomeScreen;
    FragmentLocation mFragmentLocation;
    FragmentProfile mFragmentProfile;
    FragmentEvent mFragmentEvent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

         mFragmentHomeScreen=new FragmentHomeScreen();
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content_frame,mFragmentHomeScreen).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fragmentManager=getSupportFragmentManager();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_location) {
            mFragmentLocation=new FragmentLocation();
            fragmentManager.beginTransaction().replace(R.id.content_frame,mFragmentLocation,null).commit();

        } else if (id == R.id.nav_profile) {
            FragmentProfile fragmentProfile=new FragmentProfile();
            fragmentManager.beginTransaction().replace(R.id.content_frame,fragmentProfile,null).commit();

        } else if (id == R.id.nav_events) {
            FragmentEvent fragmentEvent=new FragmentEvent();
            fragmentManager.beginTransaction().replace(R.id.content_frame,fragmentEvent).commit();

        } else if (id == R.id.settings) {

        }else if (id == R.id.help) {

        } else if (id == R.id.contact) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
