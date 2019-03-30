package com.kor.foodmanager.ui.calendar;

import com.kor.foodmanager.App;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.IsrMonth;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import static android.support.v4.content.ContextCompat.getDrawable;

public class IsrMonthDecorator implements DayViewDecorator {
    IsrMonth curr;

    public IsrMonthDecorator(IsrMonth curr){
        this.curr = curr;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if(day.isInRange(CalendarDay.from(curr.getFirstDay()),
                CalendarDay.from(curr.getLastDay()))){
            return true;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(getDrawable(App.get().getApplicationContext(), R.drawable.selected));
    }
}
