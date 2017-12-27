package com.bitcode.hardik.profilemanager.Fragments;


import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.bitcode.hardik.profilemanager.DbClasses.Dbutil;
import com.bitcode.hardik.profilemanager.R;

import java.util.Random;


public class FragmentDefaultProfile extends Fragment {


    private TextView mTxtMedia, mTxtAlarm, mTxtNotification, mTxtRing, mTxtMessage, mTxtPhoneRingtone, mTxtNotifcationRingtone,
            mTxtMessageRingtone, mTxtWallpaper, mTxtVibrate, mTxtWifi, mTxtBluetooth, mTxtMobileData, mTxtProfileName;
    private EditText mEdtProfileName;
    private Switch mSwitchVibrate, mSwitchWifi, mSwitchBluetooth, mSwitchMobileData;
    private SeekBar mSeekBarMedia, mSeekBarAlarm, mSeekBarNotification, mSeekBarRing, mSeekBarMessage;


    int MediaVolume, AlarmVolume, NotificationVolume, RingVolume;
    int Vibrate;
    int Wifi;
    int Bluetooth;
    String RingTone, NotificationRingtone, AlarmRingtone, WallPaper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_default_profile, container, false);

        mTxtMedia = (TextView) view.findViewById(R.id.txtViewDefaultMediaVolume);
        mTxtAlarm = (TextView) view.findViewById(R.id.txtViewDefaultAlarmVolume);
        mTxtNotification = (TextView) view.findViewById(R.id.txtDefaultNotificationVolume);
        mTxtRing = (TextView) view.findViewById(R.id.txtViewDefaultRingVolume);
        mTxtMessage = (TextView) view.findViewById(R.id.txtViewDefaultMessageVolume);
        mTxtPhoneRingtone = (TextView) view.findViewById(R.id.txtViewDefaultPhoneRingtone);
        mTxtNotifcationRingtone = (TextView) view.findViewById(R.id.txtViewDefaultNotificationRingtone);
        mTxtMessageRingtone = (TextView) view.findViewById(R.id.txtViewDefaultMessageRingtone);
        mTxtWallpaper = (TextView) view.findViewById(R.id.txtViewDefaultWallpaper);
        mTxtVibrate = (TextView) view.findViewById(R.id.txtViewDefaultVibrateOnCalls);
        mTxtWifi = (TextView) view.findViewById(R.id.txtViewDefaultWifi);
        mTxtBluetooth = (TextView) view.findViewById(R.id.txtViewDefaultBluetooth);
//        mTxtMobileData= (TextView) view.findViewById(R.id.txtViewMobileData);
        mTxtProfileName = (TextView) view.findViewById(R.id.txtDefaultProfileName);
        mSwitchVibrate = (Switch) view.findViewById(R.id.switchDefaultVibrateForCalls);
        mSwitchWifi = (Switch) view.findViewById(R.id.switchDefaultWifi);
        mSwitchBluetooth = (Switch) view.findViewById(R.id.switchDefaultBluetooth);
//        mSwitchMobileData= (Switch) view.findViewById(R.id.switchMobileData);

        mEdtProfileName = (EditText) view.findViewById(R.id.edtTxtDefaultProfileName);

        mSeekBarMedia = (SeekBar) view.findViewById(R.id.seekBarDefaultMediaVolume);
        mSeekBarAlarm = (SeekBar) view.findViewById(R.id.seekBarDefaultAlarmVolume);
        mSeekBarNotification = (SeekBar) view.findViewById(R.id.seekBarDefaultNotification);
        mSeekBarRing = (SeekBar) view.findViewById(R.id.seekBarDefaultRingVolume);
        mSeekBarMessage = (SeekBar) view.findViewById(R.id.seekBarDefaultMessageVolume);


        mSeekBarMedia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MediaVolume = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mSeekBarAlarm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                AlarmVolume = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mSeekBarNotification.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                NotificationVolume = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mSeekBarRing.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                RingVolume = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mSwitchVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b == true) {
                    Vibrate = 1;
                } else {
                    Vibrate = 0;
                }
            }
        });


        mSwitchWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b == true) {
                    Wifi = 1;
                } else {
                    Wifi = 0;
                }
            }
        });


        mSwitchBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b == true) {
                    Bluetooth = 1;
                } else {
                    Bluetooth = 0;
                }
            }
        });


        mTxtPhoneRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                startActivityForResult(intent, 66);

            }
        });


        mTxtNotifcationRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);

                startActivityForResult(intent, 55);

            }
        });


        mTxtMessageRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);

                startActivityForResult(intent, 65);


            }
        });


        mTxtWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Re"), 1);

            }
        });


        setHasOptionsMenu(true);


        Bundle bundle = getArguments();
        setHasOptionsMenu(true);


        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_default_profile, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveDefaultProfile:

                Dbutil dbutil1 = Dbutil.getInstance(getActivity());


                dbutil1.UpdateDefaultProfile(1, mEdtProfileName.getText().toString(), Wifi, Bluetooth, MediaVolume, NotificationVolume, AlarmVolume, RingTone, WallPaper, NotificationRingtone, AlarmRingtone, Vibrate, RingVolume);

                dbutil1.showProfileData();
                dbutil1.close();


                FragmentProfile fragmentProfile = new FragmentProfile();

//                Bundle bundle=new Bundle();
//                bundle.putSerializable("profile",mEdtProfileName.getText().toString());

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentProfile).commit();
        }

        return true;
    }

}
