package com.example.lucy.evently;

import java.util.Date;

/**
 * Created by Cindy on 9/19/2015.
 */
public class Event {
    private String description;
    private String date;
    private double latitude;
    private double longitude;

    Event(String desc, String dat, double lat, double lon) {
        this.description = desc;
        this.date = dat;
        this.latitude = lat;
        this.longitude = lon;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDate() {
        return this.date;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }
}
