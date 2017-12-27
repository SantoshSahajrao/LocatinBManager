package com.bitcode.hardik.profilemanager.Fragments;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bitcode.hardik.profilemanager.DbClasses.Dbutil;
import com.bitcode.hardik.profilemanager.DbClasses.LocationData;
import com.bitcode.hardik.profilemanager.DbClasses.ProfileData;
import com.bitcode.hardik.profilemanager.GeoFencingServiceClasses.GeofenceTransitionIntentService;
import com.bitcode.hardik.profilemanager.ModelClasses.Location;
import com.bitcode.hardik.profilemanager.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.Random;

public class FragmentLocationParameter extends Fragment {

    private GoogleApiClient mGoogleApiClient;
    private GeofencingClient mGeofencingClient;
    private ArrayList<Geofence> mGeofenceList = new ArrayList<>();
    private PendingIntent mGeofencePendingIntent;
    private LatLng latLng;
    private TextView mTxtLocationName, mTxtProfile, mTxtRadius, mTxtSeekProgress;
    private EditText mEditLocationName;
    private Spinner mSpinner;
    private BubbleSeekBar mSeekBar;
    Button mBtnSave, mBtnCancel;
    int Radius;

    double lat,lng;
    LocationData locationData;
String mProfileName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationData = new LocationData();
        Bundle bundle = getArguments();


        //   Bundle bundle = getArguments().getParcelable("bundle");
        latLng = bundle.getParcelable("latlng");
//        locationData = (LocationData) bundle.getSerializable("Data");
//        lat = locationData.getLatitude();
//        lng = locationData.getLogitude();

        //Bundle bundle=getArguments();

    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_location_parameter, container, false);

        mTxtLocationName = (TextView) view.findViewById(R.id.txtLocationName);
        mTxtProfile = (TextView) view.findViewById(R.id.txtprofilelocation);
        mTxtRadius = (TextView) view.findViewById(R.id.txtradius);
        mEditLocationName = (EditText) view.findViewById(R.id.locationName);
        mSpinner = (Spinner) view.findViewById(R.id.spinnerProfiles);
        mSeekBar = (BubbleSeekBar) view.findViewById(R.id.bubleSeekBar);
        mTxtSeekProgress = (TextView) view.findViewById(R.id.txtSeek);
        mBtnSave = (Button) view.findViewById(R.id.btnSaveLocation);
        mBtnCancel = (Button) view.findViewById(R.id.btnCancelLocation);

        mGeofencingClient = LocationServices.getGeofencingClient(getContext());

//        Bundle bundle;
//        bundle = getArguments().getParcelable("bundle");
//        latLng = bundle.getParcelable("latlng");
        setHasOptionsMenu(true);

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
//                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
//                .addApi(LocationServices.API)
//                .build();
//
//        mGoogleApiClient.connect();


        final ArrayList<String> profiles =new ArrayList<>();
        profiles.add("Normal");
        profiles.add("Silent");
        profiles.add("Vibrate");
        Dbutil dbutil = Dbutil.getInstance(getContext());
        ArrayList<ProfileData> mProfileData = dbutil.getAllProfileList();
        dbutil.close();
        for (int i=0;i<mProfileData.size();i++)
        {
            ProfileData profileData = mProfileData.get(i);
            String proName = profileData.getProfileName();
            profiles.add(proName);

        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, profiles);
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mProfileName = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        mSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress, float progressFloat) {

                mTxtSeekProgress.setText(String.format(String.valueOf(progress)));
                Radius = progress;
            }

            @Override
            public void getProgressOnActionUp(int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(int progress, float progressFloat) {

            }
        });


        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dbutil dbutil = Dbutil.getInstance(getActivity());
                LocationData locationData = new LocationData();
                locationData.setLocationId(3);
                locationData.setLocationName(mEditLocationName.getText().toString());
                locationData.setLatitude(latLng.latitude);
                locationData.setLogitude(latLng.longitude);
                locationData.setRadius(Radius);
                locationData.setPrifileName(mProfileName);

               // int LocationId = dbutil.getLocationId();

                Random random = new Random();
                int i = random.nextInt(900-10)+10;


                long count= dbutil.AddLocation(i,mEditLocationName.getText().toString(),locationData.getLatitude(),locationData.getLogitude(),locationData.getRadius(),R.drawable.ic_menu_camera,locationData.getPrifileName());

                if (count>0)
                {
                    Toast.makeText(getActivity(), "Data Save .....", Toast.LENGTH_SHORT).show();
                    Log.e("Messge","Save");
                }

                dbutil.showData();
                dbutil.close();

                populateGeofenceList();

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    getFragmentManager().popBackStack();
                    return;
                }
                mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Geofences added
                                // ...

                                Log.e("GeofenceAdded", "Success");
                            }
                        })
                        .addOnFailureListener(getActivity(), new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to add geofences
                                // ...

                                Log.e("GeofenceAdded", "Failed");
                            }
                        });

                FragmentLocation fragmentLocation=new FragmentLocation();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,fragmentLocation).commit();

            }
        });


        return view;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    //    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_location_parameter, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.save:
//
//                populateGeofenceList();
//
//                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return true;
//                }
//                mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
//                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                // Geofences added
//                                // ...
//
//                                Log.e("GeofenceAdded", "Success");
//                            }
//                        })
//                        .addOnFailureListener(getActivity(), new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Failed to add geofences
//                                // ...
//
//                                Log.e("GeofenceAdded", "Failed");
//                            }
//                        });
//
////                FragmentManager fragmentManager=getSupportFragmentManager();
////                LocationFragment locationFragment=new LocationFragment();
////                fragmentManager.beginTransaction().replace()
//        }
//
//        return true;
//    }

    public void populateGeofenceList() {

        int i = 1;

        i++;

        mGeofenceList.add(new Geofence.Builder()
                .setRequestId(mEditLocationName.getText().toString())
                .setCircularRegion(latLng.latitude, latLng.longitude, mSeekBar.getProgress())
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .setExpirationDuration(-1)
                .build());

        Log.e("Geofence", "populate" + mSeekBar.getProgress());

    }


    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);

        Log.e("Geofence", "gfrequest");
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            Log.e("Geofence", "PI not null");
            return mGeofencePendingIntent;
        }
        Log.e("Geofence", "PI else");

        Intent intent = new Intent(getActivity(), GeofenceTransitionIntentService.class);


        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().


             mGeofencePendingIntent = PendingIntent.getService(getActivity(), 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        Log.e("Geofence", "PI");
        return mGeofencePendingIntent;
//    }

    }
}
