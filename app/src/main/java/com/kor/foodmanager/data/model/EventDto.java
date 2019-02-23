package com.kor.foodmanager.data.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class EventDto {
    private String title, status, holiday, confession, description;
    private int duration;
    private long eventId;
    private String date;
    private Time time;
    private List<UserDto> participants;
    private List<String> food;
    private UserDto owner;
    private AddressDto address;

    public EventDto(String title, String status, String holiday, String confession, String description, Long eventId, int duration, String date, Time time, List<UserDto> participants, List<String> food, UserDto owner, AddressDto address) {
        this.title = title;
        this.status = status;
        this.holiday = holiday;
        this.confession = confession;
        this.description = description;
        this.eventId = eventId;
        this.duration = duration;
        this.date = date;
        this.time = time;
        this.participants = participants;
        this.food = food;
        this.owner = owner;
        this.address = address;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public EventDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getConfession() {
        return confession;
    }

    public void setConfession(String confession) {
        this.confession = confession;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public List<UserDto> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UserDto> participants) {
        this.participants = participants;
    }

    public List<String> getFood() {
        return food;
    }

    public void setFood(List<String> food) {
        this.food = food;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }
}