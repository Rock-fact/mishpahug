package com.kor.foodmanager.di.calendar;

import com.kor.foodmanager.ui.calendar.CalendarPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {CalendarModule.class})
@CalendarScope
public interface CalendarComponent {
    void inject(CalendarPresenter calendarPresenter);
}
