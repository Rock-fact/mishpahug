package com.kor.foodmanager.ui.calendar;
import com.kor.foodmanager.App;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import java.util.Collection;
import java.util.HashSet;

import static android.support.v4.content.ContextCompat.getDrawable;

public class EventDecorator implements DayViewDecorator {
    private int drawable;
    private HashSet<CalendarDay> dates;

    public EventDecorator(int color, Collection<CalendarDay> dates) {
        this.drawable = color;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(getDrawable(App.get().getApplicationContext(), drawable));
    }
}
