package com.kor.foodmanager.ui.myEventList;

import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.UserDto;

import java.util.List;


public class TitleRow {
    public static final String EVENT = "EVENT";
    public static final String STATUS = "STATUS";
    private String type;
    private String title, status,description;
    private String date;
    private int amountOfParticipants;
    private List<UserDto> participants;
    private long eventId;

    public TitleRow(String status) {
        this.type=STATUS;
        this.status=setStatus(status);
    }
    public TitleRow(EventDto eventDto){
        this.type=EVENT;
        this.title=eventDto.getTitle();
        this.status=setStatus(eventDto.getStatus());
        this.description=eventDto.getDescription();
        this.date=eventDto.getDate();
        this.amountOfParticipants=eventDto.getParticipants().size();
        this.participants=eventDto.getParticipants();
        this.eventId=eventDto.getEventId();
    }

    public long getEventId() {
        return eventId;
    }


    public static String setStatus(String status){
        switch (status){
            case "In progress":
                return MyEventListAdapter.INPROGRESS;
            case "Pending":
                return MyEventListAdapter.PENDING;
            case "Done":
                return MyEventListAdapter.DONE;
        }
        return "IsAbsent";
    }

    public static String getEVENT() {
        return EVENT;
    }

    public static String getSTATUS() {
        return STATUS;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getAmountOfParticipants() {
        return amountOfParticipants;
    }

    public List<UserDto> getParticipants() {
        return participants;
    }
}
