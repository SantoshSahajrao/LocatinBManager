package com.bitcode.hardik.profilemanager.GeoFencingServiceClasses;

import android.Manifest;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;

import com.bitcode.hardik.profilemanager.DbClasses.Dbutil;
import com.bitcode.hardik.profilemanager.DbClasses.DefaultProfileData;
import com.bitcode.hardik.profilemanager.DbClasses.ProfileData;
import com.bitcode.hardik.profilemanager.MainActivity;
import com.bitcode.hardik.profilemanager.Map.MapsActivity;
import com.bitcode.hardik.profilemanager.MyBr;
import com.bitcode.hardik.profilemanager.R;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hardik on 6/12/17.
 */

public class GeofenceTransitionIntentService extends IntentService {

    protected static final String TAG = "GeofenceTransitionsIS";


    private GeofencingClient mGeofencingClient;
    Dbutil dbutil ;
    ProfileData profileData,mDefaultProfile;
    MainActivity mainActivity;
    int CustomProfile;
    DefaultProfileData defaultProfileData;
    Bitmap bitmap22,bitmap;


    Bitmap setImge;

    private BroadcastReceiver mBr;

    public GeofenceTransitionIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "service 0");

        mBr = new MyBr();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.location.GPS_ENABLED_CHANGE");



        mainActivity = new MainActivity();
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceErrorMessage.getErrorString(this,
                    geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);

            if (geofencingEvent.getErrorCode() == GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE)
            {
                Log.e(TAG, "Re register Geofence");

               // unregisterReceiver(mBr);

                mGeofencingClient = LocationServices.getGeofencingClient(this);



                registerReceiver( mBr,  intentFilter );

            }

            return;
        }

//        unregisterReceiver(mBr);

        Log.e(TAG, "service 1");

        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        Log.e(TAG, "service 2");




        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(this, geofenceTransition, triggeringGeofences);

            dbutil = Dbutil.getInstance(getApplicationContext());
            Geofence geo1 = triggeringGeofences.get(0);
            String LocationName = geo1.getRequestId();
            String ProfileName = dbutil.searchLocationData(LocationName);
            mDefaultProfile = dbutil.getDefaulteProfileList();
            dbutil.close();



            if (ProfileName.equals("Normal")) {

                    CustomProfile = 1;
            }
           else if (ProfileName.equals("Silent"))
            {

                CustomProfile = 2;

            }
         else    if(ProfileName.equals("Vibrate"))
            {

                CustomProfile = 3;
            }
            else {

                Dbutil dbutil1 = Dbutil.getInstance(getApplicationContext());
                profileData = dbutil1.searchProfileData(ProfileName);
                dbutil1.close();


            }



//
//            profileData = dbutil.searchProfileData(ProfileName);
//            dbutil.close();




            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER)
            {


                if(CustomProfile != 0)
                {
                    if (CustomProfile ==1)
                    {


                        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

//                        am.setStreamVolume(AudioManager.STREAM_RING,10,0);
//
//                        AudioManager audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//                        audioManager.setStreamVolume(AudioManager.STREAM_RING,50,0);
//

                        setWifi(0);
                        setBluetooth(0);
                   //     setVibration(1);

                    }

                else     if (CustomProfile == 2)
                    {


                        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        setWifi(0);
                        setBluetooth(0);

                    }

                   else if (CustomProfile ==3)
                    {

                        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        setWifi(0);
                        setBluetooth(0);


                    }

                }

                else {


                    setProfile();
                }


            }
            else
            {

               setDefulProfile();
            }




            // Send notification and log the transition details.
            sendNotification(geofenceTransitionDetails);
            Log.i(TAG, geofenceTransitionDetails);
        } else {
            // Log the error.
            Log.e(TAG, "getString(R.string.geofence_transition_invalid_type, geofenceTransition)");
        }
    }

    private String getGeofenceTransitionDetails(Context context, int geofenceTransition, List<Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        ArrayList triggeringGeofencesIdsList = new ArrayList();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);

        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }

    private void sendNotification(String notificationDetails) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MapsActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Define the notification settings.
        builder.setSmallIcon(com.bitcode.hardik.profilemanager.R.mipmap.ic_launcher)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        com.bitcode.hardik.profilemanager.R.mipmap.ic_launcher))
                .setColor(Color.RED)
                .setContentTitle(notificationDetails)
                .setContentText("Geofence_transition")
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }

    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return "Geofence entered";
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return "Geofence exited";
            default:
                return "unknown_geofence_transition";
        }
    }





    public void setWallpaper(String Wallper)
    {


        if (Wallper != null) {
//            String c = Wallper;
//
//
//            String con = "content://media";
//            String s = c;
//            String ur = con + s;
//

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            // Toast.makeText(MainActivity.this, " "+s, Toast.LENGTH_SHORT).show();
          //  Uri uri = Uri.parse(ur);



//
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try {
                wallpaperManager.setBitmap(BitmapFactory.decodeFile(Wallper));
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }


    public  void setRingtone(String Ringtone)
    {


        String con = "content://media";
        String  s= Ringtone;
        String ur = con+s;


        Uri uri = Uri.parse(ur);
        settingPermision();
        RingtoneManager.setActualDefaultRingtoneUri(getApplicationContext(),RingtoneManager.TYPE_RINGTONE,uri);

    }

    public void settingPermision()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {

            if (!Settings.System.canWrite(getApplicationContext()))
            {

                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,Uri.parse("package:"+getPackageName()));
               // startActivityForResult(intent,200);
                mainActivity.startActivityForResult(intent,200);
            }
        }


    }


    public void setAlrmtone(String AlrmTone)
    {



        String con = "content://media";
        String  s= AlrmTone;
        String ur = con+s;



        Uri uri = Uri.parse(ur);
        settingPermision();
        RingtoneManager.setActualDefaultRingtoneUri(getApplicationContext(),RingtoneManager.TYPE_ALARM,uri);



    }

    public  void setNotificationTone(String NotificationTone)
    {


        String con = "content://media";
        String  s= NotificationTone;
        String ur = con+s;



        Uri uri = Uri.parse(ur);
        settingPermision();
        RingtoneManager.setActualDefaultRingtoneUri(getApplicationContext(),RingtoneManager.TYPE_NOTIFICATION,uri);


    }

    public void setMediaVolume(int MediaVollume)
    {
        AudioManager audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, MediaVollume, 0);

    }

    public void setAlarmVolume(int AlrmVollume)
    {
        AudioManager audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM,AlrmVollume,0);

    }

    public void setNotificationVolume(int NotificationVolumr)
    {
        AudioManager audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,NotificationVolumr,0);

    }

    public void setRingerVolume(int RingerVolume)
    {
        AudioManager audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_RING,RingerVolume,0);

    }

    public  void setWifi(int Wifi)
    {

        if (Wifi==1) {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);

        }
        else
        {
            WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(false);

        }
    }


    public void setBluetooth(int Bluetooth)
    {

        if (Bluetooth == 1)
        {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothAdapter.enable();


        }

        else{


            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothAdapter.disable();


        }



    }


    public void setVibration(int Vibrate)
    {
      if (Vibrate == 1)
      {
          AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
          am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);


      }


    }





    public  void setProfile(){

       setWallpaper(profileData.getWallpaper());
        setRingtone(profileData.getRingtone());
        setAlarmVolume(profileData.getAlramVolume());
        setAlrmtone(profileData.getAlarmTone());
        setNotificationVolume(profileData.getNotificationVolume());
        setNotificationTone(profileData.getNotificationTone());
        setMediaVolume(profileData.getMediaVolume());
        setRingerVolume(profileData.getRingVolume());
        setWifi(profileData.getWifi());
        setBluetooth(profileData.getBluetooth());
        setVibration(profileData.getVibration());



    }

    public  void setDefulProfile()
    {


        setWallpaper(mDefaultProfile.getWallpaper());
        setRingtone(mDefaultProfile.getRingtone());
        setAlarmVolume(mDefaultProfile.getAlramVolume());
        setAlrmtone(mDefaultProfile.getAlarmTone());
        setNotificationVolume(mDefaultProfile.getNotificationVolume());
        setNotificationTone(mDefaultProfile.getNotificationTone());
        setMediaVolume(mDefaultProfile.getMediaVolume());
        setRingerVolume(mDefaultProfile.getRingVolume());
        setWifi(mDefaultProfile.getWifi());
        setBluetooth(mDefaultProfile.getBluetooth());
        setVibration(mDefaultProfile.getVibration());



    }












    @Override
    public void onCreate() {
        super.onCreate();
    }
}
