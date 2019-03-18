package com.kor.foodmanager.buissness.calendar;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventListDto;

import java.io.IOException;

public interface ICalendarInteractor {
    EventListDto getEventsForCalendar(int month) throws ServerException, IOException;
}
