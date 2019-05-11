package com.kor.foodmanager.data.model;

import android.icu.util.HebrewCalendar;
import android.icu.util.ULocale;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.kor.foodmanager.ui.MainActivity.TAG;

public class IsrCalendar {
    private int year;
    private List<IsrMonth> months;
    private Calendar cachedDay = Calendar.getInstance();

    public IsrCalendar(int year, List<IsrMonth> months) {
        this.year = year;
        this.months = months;
    }

    public IsrCalendar() {
        this.months = new ArrayList<>();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<IsrMonth> getMonths() {
        return months;
    }

    public void setMonths(List<IsrMonth> months) {
        this.months = months;
    }

    public int size(){
        return months.size();
    }

    public IsrMonth getMonth(int index){
        if(index == -1){
            return getMonths().get(size()-1);
        }
        return getMonths().get(index);
    }

    public void addMonth(IsrMonth m){
        Calendar lastDay = Calendar.getInstance();
        lastDay = (Calendar) m.getFirstDay().clone();
        lastDay.add(Calendar.DATE,-1);
        if(size()!=0){
            if(getMonth(size()-1).getName().equals(m.getName())){
                months.remove(size()-1);
            }
            getMonth(size()-1).setLastDay(lastDay);
        }else {
            Log.d(TAG, "first month add: "+lastDay);
            cachedDay.setTime(lastDay.getTime());
        }
        m.setLastDay(cachedDay);
        months.add(m);
    }

    public List<IsrMonth> getIsrMonths(int month){
        List<IsrMonth> list = new ArrayList<>();
        for (IsrMonth m: months
             ) {
            if(m.getFirstDay().get(Calendar.MONTH)==month||m.getLastDay().get(Calendar.MONTH)==month){
                Log.d(TAG, "getIsrMonths: "+m.toString());
                list.add(m);
            }
            Log.d(TAG, "MONTHS: "+m.toString());
        }
        return list;
    }

    @Override
    public String toString() {
        return "IsrCalendar{" +
                "year=" + year +
                ", months=" + months +
                '}';
    }
}
