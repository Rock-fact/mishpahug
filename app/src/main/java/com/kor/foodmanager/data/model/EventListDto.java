package com.kor.foodmanager.data.model;

import java.util.List;

public class EventListDto {
    private List<EventDto> myEvents; //for calender --family side TODO in one time?
    private List<EventDto> subscribedEvents;//for calender --user side
    private List<EventDto> events; //getMyEventList(pending, in process), historyList(done) --family side
    private List<EventDto> content; // --user side

    public EventListDto() {
    }




    //TODO
}
