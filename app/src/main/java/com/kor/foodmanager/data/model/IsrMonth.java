package com.kor.foodmanager.data.model;

import com.prolificinteractive.materialcalendarview.DayViewDecorator;

import java.util.Date;

public class IsrMonth {
    private String name;
    private Date firstDay;
    private Date lastDay;

    public IsrMonth(String name, Date firstDay) {
        this.name = name;
        this.firstDay = firstDay;
    }

    public IsrMonth() {
    }

    public Date getLastDay() {
        return lastDay;
    }

    public void setLastDay(Date lastDay) {
        this.lastDay = lastDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(Date firstDay) {
        this.firstDay = firstDay;
    }

    @Override
    public String toString() {
        return name + ", " + firstDay.toString() + " - " + lastDay.toString();
    }
}
