package com.kor.foodmanager.ui.calendar;

import android.content.Context;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.HebcalDto;
import com.kor.foodmanager.ui.calendar.calendar_dialog.CalendarDialog;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Collection;
import java.util.List;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface ICalendar extends MvpView {
    void showCalendar(Collection<CalendarDay> myEvents, Collection<CalendarDay> subscribedEvents);
    void addCalendarListener();
    void decorateCalendar(HebcalDto isrHolidays);
    void showProgressFrame();
    void hideProgressFrame();
    void showCalendarDialog(CalendarDialog dialog);
}
