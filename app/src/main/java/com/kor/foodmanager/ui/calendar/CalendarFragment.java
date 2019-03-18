package com.kor.foodmanager.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Collection;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CalendarFragment extends MvpAppCompatFragment implements ICalendar {
    @InjectPresenter CalendarPresenter presenter;
    @BindView(R.id.calendarView) MaterialCalendarView calendarView;
    @BindView(R.id.progressFrame) FrameLayout progressFrame;
    @BindView(R.id.myEventCount) TextView myEventCount;
    @BindView(R.id.mySubCount) TextView mySubCount;
    private Unbinder unbinder;
    private OnDateSelectedListener listener;

    public static CalendarFragment getDatePicker(OnDateSelectedListener pickerListener){
        CalendarFragment fragment = new CalendarFragment();
        fragment.listener = pickerListener;
        return fragment;
    }

    // TODO: 18.03.2019 add toolbar with menu adn Back like in MyProfile 

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        unbinder = ButterKnife.bind(this,view);
        presenter.showMonth(calendarView.getCurrentDate().getMonth());
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void showCalendar(Collection<CalendarDay> myEvents, Collection<CalendarDay> subscribedEvents){
        if(listener!=null){
            calendarView.setOnDateChangedListener(listener);
        }else {
            calendarView.setOnDateChangedListener((widget, date, selected) -> {
                presenter.onDateSelected(date);
            });
        }

        calendarView.addDecorator(new EventDecorator(R.drawable.calendar_my_event,myEvents));
        calendarView.addDecorator(new EventDecorator(R.drawable.calendar_subscribed_event,subscribedEvents));
        calendarView.setOnMonthChangedListener((widget, date) -> {
            presenter.showMonth(date.getMonth());
                });
//        calendarView.setDateSelected(Calendar.getInstance(),true);        
        // TODO: 19.03.2019 something strange in behavior, if i select current date 
    }

    @Override
    public void showProgressFrame() {
        progressFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressFrame() {
        progressFrame.setVisibility(View.GONE);
    }

}
