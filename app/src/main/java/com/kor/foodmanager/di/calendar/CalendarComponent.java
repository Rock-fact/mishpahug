package com.kor.foodmanager.di.calendar;

import com.kor.foodmanager.ui.calendar.CalendarPresenter;
import com.kor.foodmanager.ui.calendar.calendar_dialog.CalendarDialogPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {CalendarModule.class})
@CalendarScope
public interface CalendarComponent {
    void inject(CalendarPresenter calendarPresenter);
    void inject(CalendarDialogPresenter calendarDialogPresenter);
}
