package com.kor.foodmanager.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IsrCalendar {
    private int year;
    private List<IsrMonth> months;

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
        Date cachedDay = null;
        if(size()!=0){
            if(getMonth(size()-1).getName().equals(m.getName())){
                months.remove(size()-1);
            }
            getMonth(size()-1).setLastDay(new Date(m.getFirstDay().getTime()-24*60*60*1000));
        }else {
            cachedDay = new Date(m.getFirstDay().getTime()-24*60*60*1000);
        }
        m.setLastDay(cachedDay);
        months.add(m);
    }

    @Override
    public String toString() {
        return "IsrCalendar{" +
                "year=" + year +
                ", months=" + months +
                '}';
    }
}
