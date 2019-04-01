package com.kor.foodmanager.ui.calendar;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.HebcalDto;
import com.kor.foodmanager.data.model.HebcalItemDto;
import com.kor.foodmanager.data.model.IsrMonth;
import com.kor.foodmanager.ui.IToolbar;
import com.kor.foodmanager.ui.calendar.calendar_dialog.CalendarDialog;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.MonthDay;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.kor.foodmanager.ui.MainActivity.TAG;

public class CalendarFragment extends MvpAppCompatFragment implements ICalendar {
    @InjectPresenter CalendarPresenter presenter;
    @BindView(R.id.calendarView) MaterialCalendarView calendarView;
    @BindView(R.id.progressFrame) FrameLayout progressFrame;
    @BindView(R.id.myEventCount) TextView myEventCount;
    @BindView(R.id.mySubCount) TextView mySubCount;
    @BindView(R.id.month1) TextView month1view;
    @BindView(R.id.month2) TextView month2view;
    @BindView(R.id.month3) TextView month3view;
    private Unbinder unbinder;
    private OnDateSelectedListener listener;
    private IToolbar iToolbar;
    private Boolean isMonth1Decorated = false;
    private Boolean isMonth2Decorated = false;
    private Boolean isMonth3Decorated = false;

    public static CalendarFragment getDatePicker(OnDateSelectedListener pickerListener){
        CalendarFragment fragment = new CalendarFragment();
        fragment.listener = pickerListener;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        unbinder = ButterKnife.bind(this,view);
        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Calendar",false,true,true);
        presenter.showMonth(calendarView.getCurrentDate().getMonth());
        progressFrame.setOnClickListener(null);
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void showCalendar(Collection<CalendarDay> myEvents, Collection<CalendarDay> subscribedEvents){
        calendarView.setDynamicHeightEnabled(true);
        calendarView.addDecorator(new EventDecorator(R.drawable.calendar_my_event,myEvents));
        calendarView.addDecorator(new EventDecorator(R.drawable.calendar_subscribed_event,subscribedEvents));
//        TitleFormatter customTitleFormatter = new TitleFormatter() {
//            @Override
//            public CharSequence format(CalendarDay day) {
//                SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy");
//                DateFormatTitleFormatter dateFormatTitleFormatter = new DateFormatTitleFormatter(format);
//                String[] isrMonths ={"Tevet/Shevat",
//                        "Shevat/Adar",
//                        "Adar/Nisan",
//                        "Nisan/Iyyar",
//                        "Iyyar/Sivan",
//                        "Sivan/Tammuz",
//                        "Tammuz/Av",
//                        "Av/Elul",
//                        "Elul/Tishrei",
//                        "Tishrei/Heshvan",
//                        "Heshvan/Kislev",
//                        "Kislev/Tevet"};
//                DateFormatSymbols isrMonthsSym  = new DateFormatSymbols();
//                isrMonthsSym.setMonths(isrMonths);
//                SimpleDateFormat isrFormat = new SimpleDateFormat("MMMM", isrMonthsSym);
//                DateFormatTitleFormatter isrFormatter = new DateFormatTitleFormatter(isrFormat);
////                MonthArrayTitleFormatter monthArrayTitleFormatter = new MonthArrayTitleFormatter(isrMonths);
//                int isrYear = day.getYear()+3760;
//                return dateFormatTitleFormatter.format(day)+" - "+isrFormatter.format(day)+" "+isrYear;
//            }
//        };
//        calendarView.setTitleFormatter(customTitleFormatter);
        calendarView.setOnMonthChangedListener((widget, date) -> {
            presenter.showMonth(date.getMonth());
                });
//        calendarView.setDateSelected(Calendar.getInstance(),true);        
        // TODO: 19.03.2019 something strange in behavior, if i select current date 
    }

    @Override
    public void addCalendarListener(){
        if(listener!=null){
            calendarView.setOnDateChangedListener(listener);
        }else {
            calendarView.setOnDateChangedListener((widget, date, selected) -> {
                presenter.onDateSelected(date);
            });
        }
    }

    @Override
    public void decorateCalendar(HebcalDto hebcalDto){
        Collection<CalendarDay> isrHolidays = new HashSet<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (HebcalItemDto hebcalItemDto :
                hebcalDto.getItems()) {
            try {
                isrHolidays.add(CalendarDay.from(format.parse(hebcalItemDto.getDate())));
                Log.d(TAG, "subscribedEvent: " + hebcalItemDto.getDate());
            } catch (ParseException e) {
                Log.d(TAG, "parse error: " + e.getMessage());
            }
        }
        calendarView.addDecorator(new HolidayDecorator(ContextCompat.getColor(getActivity(), R.color.colorAccent), isrHolidays));
        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                if(day.isBefore(CalendarDay.today())){
                    return true;
                }else {
                    return false;
                }
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.setDaysDisabled(true);
            }
        });
    }

    @Override
    public void showProgressFrame() {
        progressFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressFrame() {
        progressFrame.setVisibility(View.GONE);
    }

    @Override
    public void showCalendarDialog(CalendarDialog dialog) {
        dialog.show(getChildFragmentManager(),"DIALOG");
    }

    @Override
    public void showIsrM(List<IsrMonth> list) {
        if(list.size()==0){
            // TODO: 01.04.2019  
            return;
        }
        IsrMonth month1 = list.get(0);
        month1view.setText(month1.getName());
        IsrMonthDecorator month1Decor = new IsrMonthDecorator(month1);

        month1view.setOnClickListener(v -> {
            if(isMonth1Decorated){
                calendarView.removeDecorator(month1Decor);
                showMonthBtns(list.size());
                isMonth1Decorated = false;
            }else {
                calendarView.addDecorator(month1Decor);
                hideMonthBtns(list.size(),1);
                isMonth1Decorated = true;
            }
        });

        if (list.size()>1){
            IsrMonth month2 = list.get(1);
            month2view.setText(month2.getName());
            IsrMonthDecorator month2Decor = new IsrMonthDecorator(month2);

            month2view.setOnClickListener(v -> {
                if(isMonth2Decorated){
                    calendarView.removeDecorator(month2Decor);
                    showMonthBtns(list.size());
                    isMonth2Decorated = false;
                }else {
                    calendarView.addDecorator(month2Decor);
                    hideMonthBtns(list.size(),2);
                    isMonth2Decorated = true;
                }
            });
        }
        if (list.size()>2){
            IsrMonth month3 = list.get(2);
            month3view.setText(month3.getName());
            IsrMonthDecorator month3Decor = new IsrMonthDecorator(month3);

            month3view.setOnClickListener(v -> {
                if(isMonth3Decorated){
                    calendarView.removeDecorator(month3Decor);
                    showMonthBtns(list.size());
                    isMonth2Decorated = false;
                }else {
                    calendarView.addDecorator(month3Decor);
                    hideMonthBtns(list.size(),3);
                    isMonth3Decorated = true;
                }
            });
        }

        showMonthBtns(list.size());
        // TODO: 01.04.2019 decorators behavion after month changing 
    }

    private void showMonthBtns(int count){
            month1view.setVisibility(View.VISIBLE);
            if(count>1){
            month2view.setVisibility(View.VISIBLE);
            }
            if (count>2){
                month3view.setVisibility(View.VISIBLE);
            }
    }

    private void hideMonthBtns(int count, int curr){
        if(count==2){
            if(curr==1){
                month2view.setVisibility(View.GONE);
            }else if(curr==2){
                month1view.setVisibility(View.GONE);
            }
        }
        if (count==3){
            if(curr==1){
                month2view.setVisibility(View.GONE);
                month3view.setVisibility(View.GONE);
            }else if(curr==2){
                month1view.setVisibility(View.GONE);
                month3view.setVisibility(View.GONE);
            }else if(curr==3){
                month1view.setVisibility(View.GONE);
                month2view.setVisibility(View.GONE);
            }
        }
    }
}
