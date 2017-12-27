package com.bitcode.hardik.profilemanager.ModelClasses;

import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by hardik on 6/12/17.
 */

public class Profile implements Serializable
{
    String profileName;

    public Profile(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
}
