package com.example.workingtimewfh.ui.admin.add_user;

public class subDatapage4 {
    private String company;
    private String position;
    private String start;
    private String end;

    public subDatapage4() {
    }

    public subDatapage4(String company, String position, String start, String end) {
        this.company = company;
        this.position = position;
        this.start = start;
        this.end = end;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
