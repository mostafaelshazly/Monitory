package com.example.monitory;

public class ModuleEnd {

    private double locationLatitudeEnd ,locationLongitudeEnd;
    int minuteEnd,hourEnd;

    public ModuleEnd(double locationLatitude, double locationLongitude, int minute, int hour) {
        this.locationLatitudeEnd = locationLatitude;
        this.locationLongitudeEnd = locationLongitude;
        this.minuteEnd = minute;
        this.hourEnd = hour;
    }

    public double getLocationLatitudeEnd() {
        return locationLatitudeEnd;
    }

    public double getLocationLongitudeEnd() {
        return locationLongitudeEnd;
    }
    public int getminuteEnd() {
        return minuteEnd;
    }

    public int gethourEnd() {
        return hourEnd;
    }
}
