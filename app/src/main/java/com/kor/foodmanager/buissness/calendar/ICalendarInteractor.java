package com.kor.foodmanager.buissness.calendar;

import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.model.HebcalDto;
import com.kor.foodmanager.data.model.IsrCalendar;

import java.io.IOException;
import java.util.List;

public interface ICalendarInteractor {
    EventListDto getEventsForCalendar(int month) throws ServerException, IOException;
    HebcalDto getIsrHolidays(int month) throws IOException;
    IsrCalendar getIsrMonths(String year) throws IOException;
}
