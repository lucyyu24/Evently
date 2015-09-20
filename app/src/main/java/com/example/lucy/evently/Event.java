package com.example.lucy.evently;

import java.util.Date;

/**
 * Created by Cindy on 9/19/2015.
 */
public class Event {
    private String description;
    private String date;
    private String startTime;
    private String endTime;
    private double latitude;
    private double longitude;

    Event(String desc, String dat, String start, String end, double lat, double lon) {
        this.description = desc;
        this.date = dat;
        this.startTime = start;
        this.endTime = end;
        this.latitude = lat;
        this.longitude = lon;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDate() {
        return this.date;
    }

    public String getStartTime() {return this.startTime;}

    public String getEndTime() {return this.endTime;}

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }
}
