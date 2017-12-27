package com.bitcode.hardik.profilemanager.DbClasses;

import java.io.Serializable;

/**
 * Created by ravi on 14/12/17.
 */

public class EventData implements Serializable {

    int Day, Month ,Year, FHour,FMin, THour ,TMin;
    String EventNAME;

    public String getProfileName() {
        return ProfileName;
    }

    public void setProfileName(String profileName) {
        ProfileName = profileName;
    }

    String ProfileName;

    public String getEventNAME() {
        return EventNAME;
    }

    public void setEventNAME(String eventNAME) {
        EventNAME = eventNAME;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getFHour() {
        return FHour;
    }

    public void setFHour(int FHour) {
        this.FHour = FHour;
    }

    public int getFMin() {
        return FMin;
    }

    public void setFMin(int FMin) {
        this.FMin = FMin;
    }

    public int getTHour() {
        return THour;
    }

    public void setTHour(int THour) {
        this.THour = THour;
    }

    public int getTMin() {
        return TMin;
    }

    public void setTMin(int TMin) {
        this.TMin = TMin;
    }
}
