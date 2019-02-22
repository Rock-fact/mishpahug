package com.kor.foodmanager.data.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class EventDto {
    private String title, status, holiday, confession, description;
    private int duration;
    private Long eventId;
    private Date date;
    private Time time;
    private List<UserDto> participants;
    private List<String> food;
    private UserDto owner;

    public EventDto() {
    }
}



//TODO