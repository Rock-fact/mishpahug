package com.kor.foodmanager.data.model;

import java.util.Arrays;

public class MessageDtoUpdatedText {
    private String[] Duraton, Confession, Description, Address, Holiday, Title, Time, Date, Food;

    public MessageDtoUpdatedText() {
    }

    public MessageDtoUpdatedText(String[] duraton, String[] confession, String[] description, String[] address, String[] holiday, String[] title, String[] time, String[] date, String[] food) {
        Duraton = duraton;
        Confession = confession;
        Description = description;
        Address = address;
        Holiday = holiday;
        Title = title;
        Time = time;
        Date = date;
        Food = food;
    }

    public String[] getDuraton() {
        return Duraton;
    }

    public void setDuraton(String[] duraton) {
        Duraton = duraton;
    }

    public String[] getConfession() {
        return Confession;
    }

    public void setConfession(String[] confession) {
        Confession = confession;
    }

    public String[] getDescription() {
        return Description;
    }

    public void setDescription(String[] description) {
        Description = description;
    }

    public String[] getAddress() {
        return Address;
    }

    public void setAddress(String[] address) {
        Address = address;
    }

    public String[] getHoliday() {
        return Holiday;
    }

    public void setHoliday(String[] holiday) {
        Holiday = holiday;
    }

    public String[] getTitle() {
        return Title;
    }

    public void setTitle(String[] title) {
        Title = title;
    }

    public String[] getTime() {
        return Time;
    }

    public void setTime(String[] time) {
        Time = time;
    }

    public String[] getDate() {
        return Date;
    }

    public void setDate(String[] date) {
        Date = date;
    }

    public String[] getFood() {
        return Food;
    }

    public void setFood(String[] food) {
        Food = food;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if(Duraton!=null){
            res.append(Arrays.toString(Duraton));
        }
        if(Confession!=null){
            res.append(Arrays.toString(Confession));
        }
        if(Description!=null){
            res.append(Arrays.toString(Description));
        }
        if(Address!=null){
            res.append(Arrays.toString(Address));
        }
        if(Holiday!=null){
            res.append(Arrays.toString(Holiday));
        }
        if(Title!=null){
            res.append(Arrays.toString(Title));
        }
        if(Time!=null){
            res.append(Arrays.toString(Time));
        }
        if(Date!=null){
            res.append(Arrays.toString(Date));
        }
        if(Food!=null){
            res.append(Arrays.toString(Food));
        }
        return res.toString();
    }
}
