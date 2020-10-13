package com.example.workingtimewfh.ui.admin.home_admin;

public class TimeStruct {
    private String type;
    private String time;
    private double latitude;
    private double longtitude;

    public TimeStruct(String type, String time, double latitude, double longtitude) {
        this.type = type;
        this.time = time;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
