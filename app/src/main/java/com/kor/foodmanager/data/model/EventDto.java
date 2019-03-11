package com.kor.foodmanager.data.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class EventDto {
    private String title, status, holiday, confession, description;
    private int duration;
    private long eventId;
    private String date;
    private String time;
    private List<UserDto> participants;
    private List<String> food;
    private UserDto owner;
    private AddressDto address;


    public EventDto() {
    }


    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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

    @Override
    public String toString() {
        return "EventDto{" +
                "title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", holiday='" + holiday + '\'' +
                ", confession='" + confession + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", eventId=" + eventId +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", participants=" + participants +
                ", food=" + food +
                ", owner=" + owner +
                ", address=" + address +
                '}';
    }


}