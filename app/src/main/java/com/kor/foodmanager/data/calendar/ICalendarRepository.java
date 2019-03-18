package com.kor.foodmanager.data.calendar;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventListDto;

import java.io.IOException;

public interface ICalendarRepository {
    EventListDto getEventsForCalendar(String token, int month) throws ServerException, IOException;
}
