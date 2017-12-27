package com.bitcode.hardik.profilemanager.Map;


import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bitcode.hardik.profilemanager.DbClasses.LocationData;
import com.bitcode.hardik.profilemanager.Fragments.FragmentLocationParameter;
import com.bitcode.hardik.profilemanager.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {


    private ArrayList<com.bitcode.hardik.profilemanager.ModelClasses.Location> mArrLocationList;
    private LatLng location;
    private GoogleMap mMapFragment;
    private com.lapism.searchview.SearchView searchView;
    Button mBtnNext;
    public LatLng latLng;


    SupportMapFragment mapFragment;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        mBtnNext = (Button) view.findViewById(R.id.btnNext);

        searchView = (com.lapism.searchview.SearchView) view.findViewById(R.id.searchView);


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMapFragment = googleMap;

        LocationManager locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
       // criteria.setAccuracy(1);


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMapFragment.setMyLocationEnabled(true);



//        final Location location = locationManager.getLastKnownLocation(locationManager
//                .getBestProvider(criteria, false));
//        final double latitude = location.getLatitude();
//        final double longitude = location.getLongitude();

     Location   location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);




        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMapFragment.addMarker(new MarkerOptions().position(latLng).title(""));
        mMapFragment.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mMapFragment.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMapFragment.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLngmarker) {

                mMapFragment.clear();
                latLng = latLngmarker;
                mMapFragment.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).draggable(true).visible(true));
                mMapFragment.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            }
        });


        searchView.setOnQueryTextListener(new com.lapism.searchview.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                List<Address> addressList = null;

                if (query != null ||  !query.equals("")){
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressList = geocoder.getFromLocationName(query, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    mMapFragment.clear();
                    mMapFragment.addMarker(new MarkerOptions().position(latLng).title(query));
                    mMapFragment.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        
        
        
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //FragmentLocationParameter fragmentLocationParameter=new FragmentLocationParameter();
                Bundle bundle = new Bundle();
                bundle.putParcelable("latlng", latLng);
//                LocationData locationData = new LocationData();
//                locationData.setLatitude(latitude);
//                locationData.setLogitude(longitude);
//                bundle.putSerializable("Data",locationData);




                FragmentManager fragmentManager=getFragmentManager();
                FragmentLocationParameter fragmentLocationParameter=new FragmentLocationParameter();
                fragmentLocationParameter.setArguments(bundle);
                fragmentLocationParameter.setEnterTransition(new Slide(Gravity.RIGHT));
                fragmentManager.beginTransaction().replace(R.id.content_frame,fragmentLocationParameter).commit();


            }
        });

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
