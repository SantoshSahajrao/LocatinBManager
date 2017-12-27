package com.bitcode.hardik.profilemanager.Fragments;


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
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.bitcode.hardik.profilemanager.R;


public class FragmentEditProfile extends Fragment {

    private TextView mTxtMedia,mTxtAlarm,mTxtNotification,mTxtRing,mTxtMessage,mTxtPhoneRingtone,mTxtNotifcationRingtone,
            mTxtMessageRingtone,mTxtWallpaper,mTxtVibrate,mTxtWifi,mTxtBluetooth,mTxtMobileData,mTxtProfileName;
    private EditText mEdtProfileName;
    private Switch mSwitchVibrate,mSwitchWifi,mSwitchBluetooth,mSwitchMobileData;
    private SeekBar mSeekBarMedia,mSeekBarAlarm,mSeekBarNotification,mSeekBarRing,mSeekBarMessage;


    int MediaVolume,AlarmVolume,NotificationVolume,RingVolume;
    int Vibrate;
    int Wifi;
    int Bluetooth;
    String RingTone,NotificationRingtone,AlarmRingtone,WallPaper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_edit_profile,container,false);


        mTxtMedia= (TextView) view.findViewById(R.id.txtViewEditProfileMediaVolume);
        mTxtAlarm= (TextView) view.findViewById(R.id.txtViewEditProfileAlarmVolume);
        mTxtNotification= (TextView) view.findViewById(R.id.txtEditProfileNotificationVolume);
        mTxtRing= (TextView) view.findViewById(R.id.txtViewEditProfileRingVolume);
        mTxtMessage= (TextView) view.findViewById(R.id.txtViewEditProfileMessageVolume);
        mTxtPhoneRingtone= (TextView) view.findViewById(R.id.txtViewEditProfilePhoneRingtone);
        mTxtNotifcationRingtone= (TextView) view.findViewById(R.id.txtViewEditProfileNotificationRingtone);
        mTxtMessageRingtone= (TextView) view.findViewById(R.id.txtViewEditProfileMessageRingtone);
        mTxtWallpaper= (TextView) view.findViewById(R.id.txtViewEditProfileWallpaper);
        mTxtVibrate= (TextView) view.findViewById(R.id.txtViewEditProfileVibrateOnCalls);
        mTxtWifi= (TextView) view.findViewById(R.id.txtViewEditProfileWifi);
        mTxtBluetooth= (TextView) view.findViewById(R.id.txtViewEditProfileBluetooth);
//        mTxtMobileData= (TextView) view.findViewById(R.id.txtViewMobileData);
        mTxtProfileName= (TextView) view.findViewById(R.id.txtEditProfileName);
        mSwitchVibrate= (Switch) view.findViewById(R.id.switchEditProfileVibrateForCalls);
        mSwitchWifi= (Switch) view.findViewById(R.id.switchEditProfileWifi);
        mSwitchBluetooth= (Switch) view.findViewById(R.id.switchEditProfileBluetooth);
//        mSwitchMobileData= (Switch) view.findViewById(R.id.switchMobileData);

        mEdtProfileName= (EditText) view.findViewById(R.id.edtTxtEditProfileName);

        mSeekBarMedia= (SeekBar) view.findViewById(R.id.seekBarEditProfileMediaVolume);
        mSeekBarAlarm= (SeekBar) view.findViewById(R.id.seekBarEditProfileAlarmVolume);
        mSeekBarNotification= (SeekBar) view.findViewById(R.id.seeBarEditProfileNotification);
        mSeekBarRing= (SeekBar) view.findViewById(R.id.seeBarEditProfileRingVolume);
        mSeekBarMessage= (SeekBar) view.findViewById(R.id.seeBarEditProfileMessageVolume);



        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_profile,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.editProfile:


                FragmentProfile fragmentProfile = new FragmentProfile();

//                Bundle bundle=new Bundle();
//                bundle.putSerializable("profile",mEdtProfileName.getText().toString());

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentProfile).commit();

        }

        return true;
    }
}
