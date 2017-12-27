package com.bitcode.hardik.profilemanager.Map;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import com.lapism.searchview.SearchView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    private ArrayList<com.bitcode.hardik.profilemanager.ModelClasses.Location> mArrLocationList;
    private LatLng location;
    private GoogleMap mMap;
    private com.lapism.searchview.SearchView searchView;
    Button mBtnNext;
    private LatLng latLng;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mBtnNext = (Button) findViewById(R.id.btnNext);

        searchView = (com.lapism.searchview.SearchView) findViewById(R.id.searchView);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy( Criteria.ACCURACY_FINE );
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);


        final Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        final double latitude = location.getLatitude();
        final double longitude = location.getLongitude();



        latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLngmarker) {

                mMap.clear();
                latLng = latLngmarker;
                mMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).draggable(true).visible(true));


            }
        });

        searchView.setOnQueryTextListener(new com.lapism.searchview.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                List<Address> addressList = null;

                if (query != null ||  !query.equals("")){
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(query, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(latLng).title(query));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
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
            public void onClick(View view) {

                FragmentLocationParameter fragmentLocationParameter=new FragmentLocationParameter();
                Bundle bundle = new Bundle();
                bundle.putParcelable("latlng", latLng);
                LocationData locationData = new LocationData();
                locationData.setLatitude(latitude);
                locationData.setLogitude(longitude);
                bundle.putSerializable("Data",locationData);

               // Bundle bundle1=new Bundle();

                   fragmentLocationParameter.setArguments(bundle);

                fragmentLocationParameter.setEnterTransition(new Slide(Gravity.RIGHT));
                FragmentManager fragmentManager1=getSupportFragmentManager();
                getSupportFragmentManager().beginTransaction().replace(R.id.MapContainer,fragmentLocationParameter).commit();


            }
        });




    }

    @Override
    public void onMapLongClick(LatLng latLng) {

//        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
//        mMap.clear();
//        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
//            @Override
//            public void onMapLongClick(LatLng latLng) {
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).draggable(true).visible(true));
//            }
//        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(MapsActivity.this, marker.getPosition().latitude+"",Toast.LENGTH_LONG).show();

        return true;
    }
}
//