package com.kor.foodmanager.buissness.participationList;

import com.kor.foodmanager.data.model.EventDto;

import java.util.Comparator;

public class EventComparator implements Comparator <EventDto> {
    private static final String IN_PROGRESS = "In progress";
    private static final String PENDING ="Pending";
    private static final String DONE = "Done";

    @Override
    public int compare(EventDto o1, EventDto o2) {
        return Integer.compare(getPriority(o1.getStatus()), getPriority(o2.getStatus()));
    }

    private int getPriority(String status){
        switch (status){
            case IN_PROGRESS:
                return 1;
            case PENDING:
                return 2;
            case DONE:
                return 3;
                default:
                    return 0;
        }
    }
}
