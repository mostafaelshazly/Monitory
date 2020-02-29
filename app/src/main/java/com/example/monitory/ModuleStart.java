package com.example.monitory;

public class ModuleStart {
    private double locationLatitudeStart ,locationLongitudeStart;
    int minuteStart,hourStart;

    public ModuleStart(double locationLatitude, double locationLongitude, int minute, int hour) {
        this.locationLatitudeStart = locationLatitude;
        this.locationLongitudeStart = locationLongitude;
        this.minuteStart = minute;
        this.hourStart = hour;
    }

    public double getLocationLatitudeStart() {
        return locationLatitudeStart;
    }

    public double getLocationLongitudeStart() {
        return locationLongitudeStart;
    }
    public int getminuteStart() {
        return minuteStart;
    }

    public int gethourStart() {
        return hourStart;
    }
}
