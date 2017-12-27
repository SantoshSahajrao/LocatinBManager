package com.bitcode.hardik.profilemanager.ModelClasses;

import java.io.Serializable;

/**
 * Created by hardik on 5/12/17.
 */

public class Location implements Serializable {
    String LocationName, ProfileName;

    public Location(String locationName, String profileName) {
        LocationName = locationName;
        ProfileName = profileName;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public String getProfileName() {
        return ProfileName;
    }

    public void setProfileName(String profileName) {
        ProfileName = profileName;
    }


}
