package com.kor.foodmanager.ui.eventList;

import java.sql.Time;
import java.util.Date;

class MyEvent {
    private String familyName, eventTitle, eventDate, status;
    private int eventId, duration;
    private Date date;
    private Time time;


    public MyEvent(String familyName, String eventTitle, String eventDate) {
        this.familyName = familyName;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "MyEvent{" +
                "familyName='" + familyName + '\'' +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventDate='" + eventDate + '\'' +
                '}';
    }
}
