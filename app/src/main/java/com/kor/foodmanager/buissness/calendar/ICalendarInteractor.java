package com.kor.foodmanager.buissness.calendar;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.model.HebcalDto;

import java.io.IOException;

public interface ICalendarInteractor {
    EventListDto getEventsForCalendar(int month) throws ServerException, IOException;
    HebcalDto getIsrHolidays(int month) throws IOException;
}
