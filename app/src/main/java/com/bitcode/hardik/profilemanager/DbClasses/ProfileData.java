package com.bitcode.hardik.profilemanager.DbClasses;

import java.io.Serializable;

/**
 * Created by ravi on 5/12/17.
 */

public class ProfileData implements Serializable {
    public int getRingVolume() {
        return RingVolume;
    }

    public void setRingVolume(int ringVolume) {
        RingVolume = ringVolume;
    }

    int PrifileId,MediaVolume,NotificationVolume,MessageVolume,AlramVolume,RingVolume;
    String ProfileName;
    String Wallpaper;
    String Ringtone;
    String AlarmTone;

    public String getAlarmTone() {
        return AlarmTone;
    }

    public void setAlarmTone(String alarmTone) {
        AlarmTone = alarmTone;
    }

    public String getNotificationTone() {
        return NotificationTone;
    }

    public void setNotificationTone(String notificationTone) {
        NotificationTone = notificationTone;
    }

    public int getBluetooth() {
        return Bluetooth;
    }

    public int getWifi() {
        return Wifi;
    }

    public int getVibration() {
        return Vibration;
    }

    public void setVibration(int vibration) {
        Vibration = vibration;
    }

    String NotificationTone;
    int Bluetooth;
    int Wifi;
    int Vibration;

    public int getPrifileId() {
        return PrifileId;
    }

    public void setPrifileId(int prifileId) {
        PrifileId = prifileId;
    }

    public int getMediaVolume() {
        return MediaVolume;
    }

    public void setMediaVolume(int mediaVolume) {
        MediaVolume = mediaVolume;
    }

    public int getNotificationVolume() {
        return NotificationVolume;
    }

    public void setNotificationVolume(int notificationVolume) {
        NotificationVolume = notificationVolume;
    }

    public int getMessageVolume() {
        return MessageVolume;
    }

    public void setMessageVolume(int messageVolume) {
        MessageVolume = messageVolume;
    }

    public int getAlramVolume() {
        return AlramVolume;
    }

    public void setAlramVolume(int alramVolume) {
        AlramVolume = alramVolume;
    }


    public String getProfileName() {
        return ProfileName;
    }

    public void setProfileName(String profileName) {
        ProfileName = profileName;
    }

    public String getWallpaper() {
        return Wallpaper;
    }

    public void setWallpaper(String wallpaper) {
        Wallpaper = wallpaper;
    }

    public String getRingtone() {
        return Ringtone;
    }

    public void setRingtone(String ringtone) {
        Ringtone = ringtone;
    }

    public int isBluetooth() {
        return Bluetooth;
    }

    public void setBluetooth(int bluetooth) {
        Bluetooth = bluetooth;
    }

    public int isWifi() {
        return Wifi;
    }

    public void setWifi(int wifi) {
        Wifi = wifi;
    }
}
