package com.example.workingtimewfh.ui.admin.edit;

public class EditStruct {
    String time_end;
    String date_start;
    String date_end;
    String description;
    String status;
    String title;
    String time_start;

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public EditStruct(String time_end, String date_start, String date_end, String description, String status, String title, String time_start) {
        this.time_end = time_end;
        this.date_start = date_start;
        this.date_end = date_end;
        this.description = description;
        this.status = status;
        this.title = title;
        this.time_start = time_start;
    }

    public EditStruct() {
    }
}
