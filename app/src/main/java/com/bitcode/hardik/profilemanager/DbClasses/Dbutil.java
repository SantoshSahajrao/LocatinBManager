package com.bitcode.hardik.profilemanager.DbClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ravi on 5/12/17.
 */

public class Dbutil {


    private static Dbutil mDbutil = null;
    private static Context mcontext = null;

    public static Dbutil getInstance(Context context)
    {
        if (mDbutil ==null)
        {
            mDbutil = new Dbutil(context);

            mcontext = context;

        }

        return mDbutil;
    }


    public SQLiteDatabase mdb;
    private static final String DB_name = "LoCBaseProfile_DB";

    private Dbutil(Context context)
    {
        mdb = new DataOpenHelper(context,DB_name,null,1).getWritableDatabase();
    }


    public ArrayList<LocationData> getAllLocationList()
    {

        ArrayList<LocationData> mLocationData = new ArrayList<>();
        Cursor cursor = mdb.query("LocationData",null,null,null,null,null,null);

        while (cursor.moveToNext())
        {
            LocationData locationData = new LocationData();
            locationData.setLocationId(cursor.getInt(0));
            locationData.setLocationName(cursor.getString(1));
            locationData.setLatitude(cursor.getDouble(2));
            locationData.setLogitude(cursor.getDouble(3));
            locationData.setRadius(cursor.getInt(4));
            locationData.setIcon(cursor.getString(5));
            locationData.setPrifileName(cursor.getString(6));

            mLocationData.add(locationData);

        }

        cursor.close();
        return mLocationData;

    }

    public int Count()
    {
        int count =0;
        Cursor cursor = mdb.query("LocationData",null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            count = cursor.getInt(0);

        }
        return count;

    }


    public void showData()
    {

        ArrayList<LocationData> mLocationData = new ArrayList<>();
        Cursor cursor = mdb.query("LocationData",null,null,null,null,null,null);

        while (cursor.moveToNext())
        {
            LocationData locationData = new LocationData();
            locationData.setLocationId(cursor.getInt(0));
            locationData.setLocationName(cursor.getString(1));
            locationData.setLatitude(cursor.getDouble(2));
            locationData.setLogitude(cursor.getDouble(3));
            locationData.setRadius(cursor.getInt(4));
            locationData.setIcon(cursor.getString(5));
            locationData.setPrifileName(cursor.getString(6));

            Log.e("Data", String.valueOf(cursor.getInt(0)));
            Log.e("Data",cursor.getString(1));
            Log.e("Data", String.valueOf(cursor.getDouble(2)));
            Log.e("Data", String.valueOf(cursor.getDouble(3)));
            Log.e("Data", String.valueOf(cursor.getInt(4)));
            Log.e("Data", cursor.getString(5));
            Log.e("Data", cursor.getString(6));






            mLocationData.add(locationData);

        }

        cursor.close();


    }











    public long AddLocation(int LocationId, String LocationName, double Latitude, double Logitude, int Radius, int Icon, String ProfileName)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("LocationId",LocationId);
        contentValues.put("LocationName",LocationName);
        contentValues.put("Latitude",Latitude);
        contentValues.put("Logitude",Logitude);
        contentValues.put("Radius",Radius);
        contentValues.put("LocationIcon",Icon);
        contentValues.put("ProfileName",ProfileName);

        long count= mdb.insert("LocationData",null,contentValues);

        if (count>0)
        {
            Log.e("Data","Save");
        }

        return count;
    }


    public boolean UpdateLocationData(int LocationId, String LocationName, double Latitude, double Logitude, int Radius, String Icon, String ProfileName)
    {

        ContentValues contentValues = new ContentValues();
        contentValues.put("LocationId",LocationId);
        contentValues.put("LocationName",LocationName);
        contentValues.put("Latitude",Latitude);
        contentValues.put("Logitude",Logitude);
        contentValues.put("Radius",Radius);
        contentValues.put("LocationIcon",Icon);
        contentValues.put("ProfileName",ProfileName);

        mdb.update("LocationData",contentValues,"LocationId = ?",new String[]{String.valueOf(LocationId)});

        return  true;

    }

    public boolean DeleteLocationData(int LocationId)
    {

        mdb.delete("LocationData","LocationId = ?",new String[]{String.valueOf(LocationId)});
        return true;
    }






    public ArrayList<ProfileData> getAllProfileList()
    {

        ArrayList<ProfileData> mProfileData = new ArrayList<>();
        Cursor cursor = mdb.query("ProfileData",null,null,null,null,null,null);

        while (cursor.moveToNext()){

          ProfileData profileData = new ProfileData();
          profileData.setPrifileId(cursor.getInt(0));
          profileData.setProfileName(cursor.getString(1));
          profileData.setWifi(cursor.getInt(2));
          profileData.setBluetooth(cursor.getInt(3));
          profileData.setMediaVolume(cursor.getInt(4));
          profileData.setNotificationVolume(cursor.getInt(5));
          profileData.setAlramVolume(cursor.getInt(6));
          profileData.setRingtone(cursor.getString(7));
          profileData.setWallpaper(cursor.getString(8));

            mProfileData.add(profileData);

        }

        cursor.close();
        return mProfileData;

    }


    public long AddProfile(int ProfileId, String ProfileName,int  Wifi,int Bluetooth,int MediaVolume,int NotificationVolume,int AlarmVolume,String Ringtone,String Wallpaper,String NotificationTone,String AlarmTone ,int VibrationData ,int RingVolume)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ProfileId",ProfileId);
        contentValues.put("ProfileName",ProfileName);
        contentValues.put("WiFiSetting",Wifi);
        contentValues.put("BluetoothSetting",Bluetooth);
        contentValues.put("MediaVolume",MediaVolume);
        contentValues.put("NotificationVolume",NotificationVolume);
        contentValues.put("AlarmVolume",AlarmVolume);
        contentValues.put("RingtoneData",Ringtone);
        contentValues.put("WallpaperData",Wallpaper);
        contentValues.put("NotificationTone",NotificationTone);
        contentValues.put("AlarmTone",AlarmTone);
        contentValues.put("VibratinData",VibrationData);
        contentValues.put("RingtoneVolume",RingVolume);


        long count= mdb.insert("ProfileData",null,contentValues);
        if (count > 0)
        {
            Log.e("Dataaa",""+count);
        }

        return count;
    }


    public boolean UpdateProfileData(int ProfileId, String ProfileName,int  Wifi,int Bluetooth,int MediaVolume,int NotificationVolume,int AlarmVolume,String Ringtone,String Wallpaper)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ProfileId",ProfileId);
        contentValues.put("ProfileName",ProfileName);
        contentValues.put("WiFiSetting",Wifi);
        contentValues.put("BluetoothSetting",Bluetooth);
        contentValues.put("MediaVolume",MediaVolume);
        contentValues.put("NotificationVolume",NotificationVolume);
        contentValues.put("AlarmVolume",AlarmVolume);
        contentValues.put("RingtoneData",Ringtone);
        contentValues.put("WallpaperData",Wallpaper);

        mdb.update("ProfileData",contentValues,"LocationId = ?",new String[]{String.valueOf(ProfileId)});

        return  true;

    }

    public boolean DeleteProfileData(String ProfileName)
    {

        mdb.delete("ProfileData","ProfileName = ?",new String[]{ProfileName});
        return true;
    }





    public void showProfileData()
    {

        ArrayList<LocationData> mLocationData = new ArrayList<>();
        Cursor cursor = mdb.query("ProfileData",null,null,null,null,null,null);

        while (cursor.moveToNext())
        {
            LocationData locationData = new LocationData();
            locationData.setLocationId(cursor.getInt(0));
            locationData.setLocationName(cursor.getString(1));
            locationData.setLatitude(cursor.getDouble(2));
            locationData.setLogitude(cursor.getDouble(3));
            locationData.setRadius(cursor.getInt(4));
            locationData.setIcon(cursor.getString(5));
            locationData.setPrifileName(cursor.getString(6));

            Log.e("Data", String.valueOf(cursor.getInt(0)));
            Log.e("Data",cursor.getString(1));
            Log.e("Data", String.valueOf(cursor.getDouble(2)));
            Log.e("Data", String.valueOf(cursor.getDouble(3)));
            Log.e("Data", String.valueOf(cursor.getInt(4)));
            Log.e("Data", cursor.getString(5));
            Log.e("Data", cursor.getString(6));






            mLocationData.add(locationData);

        }

        cursor.close();


    }













    public void close()
    {
        mdb.close();
        mDbutil = null;

    }









    public LocationData getLocationId()
    {
        LocationData locationData = new LocationData();
        Cursor cursor = mdb.query("LocationData",null,null,null,null,null,null);
        while (cursor.moveToLast()){
            locationData.setLocationId(cursor.getInt(0));
            locationData.setLocationName(cursor.getString(1));
            locationData.setLatitude(cursor.getDouble(2));
            locationData.setLogitude(cursor.getDouble(3));
            locationData.setRadius(cursor.getInt(4));
            locationData.setIcon(cursor.getString(5));
            locationData.setPrifileName(cursor.getString(6));
        }

        cursor.close();
        return locationData;


    }




    public LocationData getProfileId()
    {

        LocationData locationData = new LocationData();
        Cursor cursor = mdb.query("ProfileData",null,null,null,null,null,null);
        while (cursor.moveToLast()){

            locationData.setLocationId(cursor.getInt(0));
            locationData.setLocationName(cursor.getString(1));
            locationData.setLatitude(cursor.getDouble(2));
            locationData.setLogitude(cursor.getDouble(3));
            locationData.setRadius(cursor.getInt(4));
            locationData.setIcon(cursor.getString(5));
            locationData.setPrifileName(cursor.getString(6));


        }


        cursor.close();

        return locationData;


    }



    public String searchLocationData(String LocationName)
    {

        String ProfileName= null;
       // Cursor cursor = mdb.query("LocationData",new String[]{"ProfileName"},"LocationName = ?",new String[]{LocationName},null,null,null);
       Cursor cursor = mdb.query("LocationData",null,"LocationName =?",new String[]{LocationName},null,null,null,null);

        while (cursor.moveToNext())
        {
            ProfileName = cursor.getString(6);
        }

        cursor.close();
        return ProfileName;



    }

    public ProfileData searchProfileData(String ProfileName)
    {
        ProfileData profileData = new ProfileData();
        Cursor cursor = mdb.query("ProfileData",null,"ProfileName =?",new String[]{ProfileName},null,null,null,null);
        while (cursor.moveToNext())
        {
            profileData.setPrifileId(cursor.getInt(0));
            profileData.setProfileName(cursor.getString(1));
            profileData.setWifi(cursor.getInt(2));
            profileData.setBluetooth(cursor.getInt(3));
            profileData.setMediaVolume(cursor.getInt(4));
            profileData.setNotificationVolume(cursor.getInt(5));
            profileData.setAlramVolume(cursor.getInt(6));
            profileData.setRingtone(cursor.getString(7));
            profileData.setWallpaper(cursor.getString(8));
            profileData.setNotificationTone(cursor.getString(9));
            profileData.setAlarmTone(cursor.getString(10));
            profileData.setVibration(cursor.getInt(11));
            profileData.setRingVolume(cursor.getInt(12));
        }
        return profileData;
    }

    public void UpdateDefaultProfile(int ProfileId, String ProfileName,int  Wifi,int Bluetooth,int MediaVolume,int NotificationVolume,int AlarmVolume,String Ringtone,String Wallpaper,String NotificationTone,String AlarmTone ,int VibrationData ,int RingVolume)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ProfileId",1);
        contentValues.put("ProfileName",ProfileName);
        contentValues.put("WiFiSetting",Wifi);
        contentValues.put("BluetoothSetting",Bluetooth);
        contentValues.put("MediaVolume",MediaVolume);
        contentValues.put("NotificationVolume",NotificationVolume);
        contentValues.put("AlarmVolume",AlarmVolume);
        contentValues.put("RingtoneData",Ringtone);
        contentValues.put("WallpaperData",Wallpaper);
        contentValues.put("NotificationTone",NotificationTone);
        contentValues.put("AlarmTone",AlarmTone);
        contentValues.put("VibratinData",VibrationData);
        contentValues.put("RingtoneVolume",RingVolume);

        long count= mdb.update("DefaultProfileData",contentValues,null,null);
        Toast.makeText(mcontext, "Update Successful", Toast.LENGTH_SHORT).show();

    }


    public ProfileData getDefaulteProfileList()
    {

        ProfileData profileData = new ProfileData();
        Cursor cursor = mdb.query("DefaultProfileData",null,null,null,null,null,null);

        while (cursor.moveToNext()){


            profileData.setPrifileId(cursor.getInt(0));
            profileData.setProfileName(cursor.getString(1));
            profileData.setWifi(cursor.getInt(2));
            profileData.setBluetooth(cursor.getInt(3));
            profileData.setMediaVolume(cursor.getInt(4));
            profileData.setNotificationVolume(cursor.getInt(5));
            profileData.setAlramVolume(cursor.getInt(6));
            profileData.setRingtone(cursor.getString(7));
            profileData.setWallpaper(cursor.getString(8));


        }

        cursor.close();
        return profileData;

    }





    public  void  AddEvent(String EventName,int Day, int Month, int Year, int FHour, int FMin, int THour , int TMin , String profileName)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("EventName",EventName);
        contentValues.put("Day",Day);
        contentValues.put("Month",Month);
        contentValues.put("Year",Year);
        contentValues.put("FHours",FHour);
        contentValues.put("FMinitue",FMin);
        contentValues.put("THour",THour);
        contentValues.put("TMinitue",TMin);
        contentValues.put("ProfileName",profileName);

        long r =mdb.insert("EventData",null,contentValues);
        if (r >0)
        {

           Log.e("Data"," Data Inserted");
            // Toast.makeText(, "", Toast.LENGTH_SHORT).show();
        }




    }
















    public class DataOpenHelper extends SQLiteOpenHelper {


        public DataOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }




        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("Create table LocationData(LocationId integer primary key,LocationName String,Latitude double,Logitude double,Radius integer,LocationIcon integer,ProfileName String)");
            db.execSQL("Create table ProfileData( ProfileId integer primary key,ProfileName String , WiFiSetting integer,BluetoothSetting integer,MediaVolume integer,NotificationVolume integer,AlarmVolume integer,RingtoneData String,WallPaperData String,NotificationTone String,AlarmTone String,VibratinData integer,RingtoneVolume integer)");
            db.execSQL("Create table DefaultProfileData(ProfileId integer primary key,ProfileName String , WiFiSetting integer,BluetoothSetting integer,MediaVolume integer,NotificationVolume integer,AlarmVolume integer,RingtoneData String,WallPaperData String,NotificationTone String,AlarmTone String,VibratinData integer,RingtoneVolume integer)");
            db.execSQL("Create table EventData(EventName String primary key,Day integer,Month integer,Year integer,FHours integer,FMinitue integer,THour integer,TMinitue integer,ProfileName String)");



            ContentValues contentValues = new ContentValues();
            contentValues.put("ProfileId",1);
            contentValues.put("ProfileName","DefaultProfile");
            contentValues.put("WiFiSetting",0);
            contentValues.put("BluetoothSetting",0);
            contentValues.put("MediaVolume",8);
            contentValues.put("NotificationVolume",8);
            contentValues.put("AlarmVolume",8);
            contentValues.put("RingtoneData","");
            contentValues.put("WallpaperData","");
            contentValues.put("NotificationTone","");
            contentValues.put("AlarmTone","");
            contentValues.put("VibratinData","");
            contentValues.put("RingtoneVolume",8);







            db.insert("DefaultProfileData",null,contentValues);



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }




}
