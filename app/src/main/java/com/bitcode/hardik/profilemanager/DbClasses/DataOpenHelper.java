package com.bitcode.hardik.profilemanager.DbClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ravi on 3/12/17.
 */

public class DataOpenHelper extends SQLiteOpenHelper {


    public DataOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create table LocationData(LocationId integer primary key,LocationName String,Latitude double,Logitude double,Radius integer,LocationIcon String,ProfileName String)");
        db.execSQL("Create table ProfileData( ProfileId integer primary key,ProfileName String , WiFiSetting boolean,BluetoothSetting boolean,MediaVolumeSetting integer,NotificationVolumeSetting integer,MediaVolumeSetting integer,AlarmVolumeSetting integer,RingtoneData String,WallPaperData String)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
