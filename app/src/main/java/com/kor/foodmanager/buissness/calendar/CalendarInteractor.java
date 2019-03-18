package com.kor.foodmanager.buissness.calendar;

import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.calendar.ICalendarRepository;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventListDto;

import java.io.IOException;

public class CalendarInteractor implements ICalendarInteractor {
    private IAuthRepository authRepository;
    private ICalendarRepository calendarRepository;

    public CalendarInteractor(IAuthRepository authRepository, ICalendarRepository calendarRepository) {
        this.authRepository = authRepository;
        this.calendarRepository = calendarRepository;
    }

    @Override
    public EventListDto getEventsForCalendar(int month) throws ServerException, IOException {
        String token = authRepository.getToken();
        return calendarRepository.getEventsForCalendar(token, month);
    }
}
