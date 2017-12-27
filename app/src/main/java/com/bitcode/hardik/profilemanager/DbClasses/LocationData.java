package com.bitcode.hardik.profilemanager.DbClasses;

import java.io.Serializable;

/**
 * Created by ravi on 5/12/17.
 */

public class LocationData implements Serializable {

    int LocationId,Radius;
    String LocationName,Icon,PrifileName;
    double latitude,logitude;

    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int locationId) {
        LocationId = locationId;
    }

    public int getRadius() {
        return Radius;
    }

    public void setRadius(int radius) {
        Radius = radius;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getPrifileName() {
        return PrifileName;
    }

    public void setPrifileName(String prifileName) {
        PrifileName = prifileName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLogitude() {
        return logitude;
    }

    public void setLogitude(double logitude) {
        this.logitude = logitude;
    }
}
