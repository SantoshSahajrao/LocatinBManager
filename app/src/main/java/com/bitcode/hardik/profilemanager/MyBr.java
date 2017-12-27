package com.bitcode.hardik.profilemanager;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.bitcode.hardik.profilemanager.DbClasses.Dbutil;
import com.bitcode.hardik.profilemanager.DbClasses.LocationData;
import com.bitcode.hardik.profilemanager.DbClasses.ProfileData;
import com.bitcode.hardik.profilemanager.GeoFencingServiceClasses.GeofenceTransitionIntentService;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Map;


public class MyBr extends BroadcastReceiver {


    private GeofencingClient mGeofencingClient;
    ArrayList<Geofence> mGeofenceList = new ArrayList<>();
    private PendingIntent mGeofencePendingIntent;
    ArrayList<LocationData>profileData;
    Context mContex;


    public static final String GPS_ENABLED_CHANGE_ACTION = "android.location.GPS_ENABLED_CHANGE";


    @Override
    public void onReceive(Context context, Intent intent) {

        mContex = context;

        if (intent.getAction().matches(GPS_ENABLED_CHANGE_ACTION)) {
            boolean enabled = intent.getBooleanExtra("enabled",false);

            Toast.makeText(context, "GPS GF : " + enabled,
                    Toast.LENGTH_SHORT).show();

            mGeofencingClient = LocationServices.getGeofencingClient(context);


            Dbutil dbutil = Dbutil.getInstance(mContex);
            profileData = dbutil.getAllLocationList();
            dbutil.close();


            if (profileData !=null) {


                populateGeofenceList();

                Log.e("Geofence br", "postpopulate");

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                Log.e("Geofence br", "permission");

                mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent(context))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Geofences added
                                // ...

                                Log.e("GeofenceAdded br", "Success");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to add geofences
                                // ...

                                Log.e("GeofenceAdded br", "Failed");
                            }
                        });
            }
        }
//        else {
//            if( intent.getAction().equals("in.bitcode.download.COMPLETE")) {
//                Toast.makeText( context, intent.getStringExtra("path"), Toast.LENGTH_LONG).show();
//            }
//            else {
//                Toast.makeText( context, intent.getAction(), Toast.LENGTH_LONG).show();
//            }
//
//        }


    }


    public void populateGeofenceList() {



        Dbutil dbutil = Dbutil.getInstance(mContex);
        profileData = dbutil.getAllLocationList();
        dbutil.close();

        if (profileData != null) {
            for (int i = 0; i < profileData.size(); i++) {
                LocationData profileData1 = profileData.get(i);

                mGeofenceList.add(new Geofence.Builder()
                        .setRequestId(profileData1.getLocationName())
                        .setCircularRegion(profileData1.getLatitude(), profileData1.getLogitude(), profileData1.getRadius())
                        .setExpirationDuration(-1)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                        .build());

                Log.e("Geofence br", "populate");

            }

        }


    }





    private GeofencingRequest getGeofencingRequest() {


            GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
            builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);


                builder.addGeofences(mGeofenceList);

                Log.e("Geofence br", "gfrequest");




            return builder.build();

    }

    private PendingIntent getGeofencePendingIntent(Context context) {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            Log.e("Geofence br", "PI not null");
            return mGeofencePendingIntent;
        }
        Log.e("Geofence br", "PI else");

        Intent intent = new Intent(context, GeofenceTransitionIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        mGeofencePendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        Log.e("Geofence br", "PI");
        return mGeofencePendingIntent;
    }

}
