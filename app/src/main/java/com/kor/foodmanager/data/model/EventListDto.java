package com.kor.foodmanager.data.model;

import java.util.List;

public class EventListDto {
    private List<EventDto> myEvents;
    private List<EventDto> subscribedEvents;

    //for calender

    public EventListDto() {
    }

    public EventListDto(List<EventDto> myEvents, List<EventDto> subscribedEvents) {
        this.myEvents = myEvents;
        this.subscribedEvents = subscribedEvents;
    }

    public List<EventDto> getMyEvents() {
        return myEvents;
    }

    public void setMyEvents(List<EventDto> myEvents) {
        this.myEvents = myEvents;
    }

    public List<EventDto> getSubscribedEvents() {
        return subscribedEvents;
    }

    public void setSubscribedEvents(List<EventDto> subscribedEvents) {
        this.subscribedEvents = subscribedEvents;
    }
}
