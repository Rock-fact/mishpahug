package com.kor.foodmanager.buissness.calendar;

import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.calendar.ICalendarRepository;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.exteral.IExternalRepository;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.model.HebcalDto;
import com.kor.foodmanager.data.model.HebcalItemDto;
import com.kor.foodmanager.data.model.IsrCalendar;
import com.kor.foodmanager.data.model.IsrMonth;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public IsrCalendar getIsrMonths(String year) throws IOException {
        HebcalDto data = externalRepository.getIsrMonths(year);
        List<HebcalItemDto> isrMonthsList = data.getItems();
        IsrCalendar isrCalendar = new IsrCalendar();
        String dataTitle = data.getTitle();
        isrCalendar.setYear(Integer.valueOf(dataTitle.substring(dataTitle.length()-4)));
        //add parse exceptions
        List<IsrMonth> list = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (HebcalItemDto itemDto : isrMonthsList
        ) {
            if(itemDto.getCategory().equals("roshchodesh")){
                String monthName = itemDto.getTitle().replace("Rosh Chodesh ","");
                Date monthDate = null;
                try {
                    monthDate = format.parse(itemDto.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                IsrMonth isrMonth = new IsrMonth(monthName,monthDate);
                isrCalendar.addMonth(isrMonth);
            }else if(itemDto.getTitle().substring(0,itemDto.getTitle().length()-4).equals("Rosh Hashana")){
                Date monthDate = null;
                try {
                    monthDate = format.parse(itemDto.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                IsrMonth isrMonth = new IsrMonth("Tishrei",monthDate);
                isrCalendar.addMonth(isrMonth);
            }
        }
        return isrCalendar;
    }
}
