package com.bitcode.hardik.profilemanager;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by ravi on 14/12/17.
 */

public class Service1 extends IntentService {


    public Service1() {
        super("Servii");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(this, "Hello I am SerVice", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {




        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {



    }
}
