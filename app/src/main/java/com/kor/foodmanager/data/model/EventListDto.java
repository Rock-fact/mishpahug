package com.kor.foodmanager.data.model;

import java.util.List;

public class EventListDto {
    private List<EventDto> myEvents; //for calender --family side TODO in one time?
    private List<EventDto> subscribedEvents;//for calender --user side
    private List<EventDto> events; //getMyEventList(pending, in process), historyList(done) --family side

    // for List of Events in progress

    private List<EventDto> content; // --user side
    private int totalElements, totalPages, size, number, numberOfElements;
    private boolean first, last;

    public EventListDto() {
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

    public List<EventDto> getEvents() {
        return events;
    }

    public void setEvents(List<EventDto> events) {
        this.events = events;
    }

    public List<EventDto> getContent() {
        return content;
    }

    public void setContent(List<EventDto> content) {
        this.content = content;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

//TODO
}

//        "sort": null
//        } --??????
