package com.example.workingtimewfh.ui.admin.home_admin.AdapterTask;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class TaskStruct {
    private String time;
    private String head;
    private String location;
    private String detail;
    private List<Bitmap> img;
    private double latitude;
    private double longtitude;

    public TaskStruct(String time, String head, String location, String detail, List<Bitmap> img, double latitude, double longtitude) {
        this.time = time;
        this.head = head;
        this.location = location;
        this.detail = detail;
        this.img = img;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<Bitmap> getImg() {
        return img;
    }

    public void setImg(List<Bitmap> img) {
        this.img = img;
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
