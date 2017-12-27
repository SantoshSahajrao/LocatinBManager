package com.bitcode.hardik.profilemanager.ModelClasses;

import java.io.Serializable;

/**
 * Created by hardik on 6/12/17.
 */

public class Event implements Serializable {

    String eventName,profileName;

    public Event(String eventName, String profileName) {
        this.eventName = eventName;
        this.profileName = profileName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
}
