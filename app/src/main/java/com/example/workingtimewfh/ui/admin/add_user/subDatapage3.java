package com.example.workingtimewfh.ui.admin.add_user;

public class subDatapage3 {
    private String Level;
    private String name;
    private int year;
    private double grade;

    public subDatapage3() {
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public subDatapage3(String level, String name, int year, double grade) {
        Level = level;
        this.name = name;
        this.year = year;
        this.grade = grade;
    }
}
