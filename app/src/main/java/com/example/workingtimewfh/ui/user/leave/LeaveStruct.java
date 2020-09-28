package com.example.workingtimewfh.ui.user.leave;

import java.util.Map;

public class LeaveStruct {

    /*String title;
    String date_start;
    String time_start;
    String date_end;
    String time_end;
    String status;
    String description;

    public LeaveStruct() {
    }

    public LeaveStruct(String title, String date_start, String time_start, String date_end, String time_end, String status, String description) {
        this.title = title;
        this.date_start = date_start;
        this.time_start = time_start;
        this.date_end = date_end;
        this.time_end = time_end;
        this.status = status;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }*/


    Map<String, Object> data;

    public LeaveStruct() {
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public LeaveStruct(Map<String, Object> data) {
        this.data = data;
    }
}
