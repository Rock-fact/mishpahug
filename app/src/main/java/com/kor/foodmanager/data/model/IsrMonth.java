package com.kor.foodmanager.data.model;

import com.prolificinteractive.materialcalendarview.DayViewDecorator;

import java.util.Calendar;
import java.util.Date;

public class IsrMonth {
    private String name;
    private Calendar firstDay;
    private Calendar lastDay;

    public IsrMonth(String name, Calendar firstDay) {
        this.name = name;
        this.firstDay = firstDay;
    }

    public IsrMonth() {
    }

    public Calendar getLastDay() {
        return lastDay;
    }

    public void setLastDay(Calendar lastDay) {
        this.lastDay = lastDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(Calendar firstDay) {
        this.firstDay = firstDay;
    }

    @Override
    public String toString() {
        return name + ", " + (firstDay.get(Calendar.MONTH)+1)+"."+firstDay.get(Calendar.DATE)+ " - " + (lastDay.get(Calendar.MONTH)+1)+"."+lastDay.get(Calendar.DATE);
    }
}
