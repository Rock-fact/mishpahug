package com.kor.foodmanager.buissness.calendar;

import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.calendar.ICalendarRepository;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.exteral.IExternalRepository;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.model.HebcalDto;

import java.io.IOException;

public class CalendarInteractor implements ICalendarInteractor {
    private IAuthRepository authRepository;
    private ICalendarRepository calendarRepository;
    private IExternalRepository externalRepository;

    public CalendarInteractor(IAuthRepository authRepository, ICalendarRepository calendarRepository, IExternalRepository externalRepository) {
        this.authRepository = authRepository;
        this.calendarRepository = calendarRepository;
        this.externalRepository = externalRepository;
    }

    @Override
    public EventListDto getEventsForCalendar(int month) throws ServerException, IOException {
        String token = authRepository.getToken();
        return calendarRepository.getEventsForCalendar(token, month);
    }

    @Override
    public HebcalDto getIsrHolidays(int month) throws IOException {
        return externalRepository.getIsrHolidays(month);
    }
}
