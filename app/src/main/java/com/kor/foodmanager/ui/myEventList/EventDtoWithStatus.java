package com.kor.foodmanager.ui.myEventList;

import com.kor.foodmanager.data.model.EventDto;

public class EventDtoWithStatus {
    public static final String EVENT = "EVENT";
    public static final String STATUS = "STATUS";
    private String type;
    private EventDto event=new EventDto();

    public EventDtoWithStatus(String status) {
        this.type=STATUS;
        this.event.setStatus(status);
    }
    public EventDtoWithStatus(EventDto eventDto){
        this.type=EVENT;
        this.event=eventDto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public EventDto getEvent() {
        return event;
    }

    public void setEvent(EventDto event) {
        this.event = event;
    }




}
