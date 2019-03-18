package com.kor.foodmanager.ui.calendar;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Collection;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface ICalendar extends MvpView {
    void showCalendar(Collection<CalendarDay> myEvents, Collection<CalendarDay> subscribedEvents);
    void showProgressFrame();
    void hideProgressFrame();
}
