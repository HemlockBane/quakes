package com.example.android.test.models;

public class Quakes {

    private double magnitude;

    private String location;

    private long time;

    private String url;

    public Quakes(double magnitude, String location, long time, String url){

        this.magnitude = magnitude;
        this.location = location;
        this.time = time;
        this.url = url;

    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
