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
    private Unbinder unbinder;
    private OnDateSelectedListener listener;
    private IToolbar iToolbar;

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
        iToolbar.setTitleToolbarEnable("Calendar",false,true,false);
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
        calendarView.addDecorator(new EventDecorator(R.drawable.calendar_my_event,myEvents));
        calendarView.addDecorator(new EventDecorator(R.drawable.calendar_subscribed_event,subscribedEvents));
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

        TitleFormatter customTitleFormatter = new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy");
                DateFormatTitleFormatter dateFormatTitleFormatter = new DateFormatTitleFormatter(format);
                String[] isrMonths ={"Tevet/Shevat",
                        "Shevat/Adar",
                        "Adar/Nisan",
                        "Nisan/Iyyar",
                        "Iyyar/Sivan",
                        "Sivan/Tammuz",
                        "Tammuz/Av",
                        "Av/Elul",
                        "Elul/Tishrei",
                        "Tishrei/Heshvan",
                        "Heshvan/Kislev",
                        "Kislev/Tevet"};
                DateFormatSymbols isrMonthsSym  = new DateFormatSymbols();
                isrMonthsSym.setMonths(isrMonths);
                SimpleDateFormat isrFormat = new SimpleDateFormat("MMMM", isrMonthsSym);
                DateFormatTitleFormatter isrFormatter = new DateFormatTitleFormatter(isrFormat);
//                MonthArrayTitleFormatter monthArrayTitleFormatter = new MonthArrayTitleFormatter(isrMonths);
                int isrYear = day.getYear()+3760;
                return dateFormatTitleFormatter.format(day)+" - "+isrFormatter.format(day)+" "+isrYear;
            }
        };
        calendarView.setTitleFormatter(customTitleFormatter);
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
    public void showDateDialog(CalendarDay date, String stringDate, String message, List<EventDto> myEventsDto, List<EventDto> subscribedEventsDto) {
        Bundle args = new Bundle();
        args.putString("TITLE", stringDate);
        args.putString("MESSAGE", message);
        if(myEventsDto.size()!=0){
            args.putString("MY_EVENTS","You have " + myEventsDto.size() + " event today");
        }
        if(subscribedEventsDto.size()!=0){
            args.putString("MY_SUBS","You subscribe " + myEventsDto.size() + " event today");
        }
        CalendarDialog dialog = CalendarDialog.newInstance(args);
        Log.d(TAG, "showDateDialog: "+stringDate+", "+message);
        dialog.setCancelable(true);
        dialog.show(getChildFragmentManager(), "DIALOG");

//        new AlertDialog.Builder(getActivity())
//                .setView(R.layout.calendar_dialog)
//                .setTitle(stringDate)
//                .setMessage(message)
//                .setPositiveButton("Add new event", (dialog, which) -> {
//                    presenter.goToAddEventScreen(date.getCalendar());
//                })
//                .setNeutralButton("Find event", ((dialog, which) -> {
//                    presenter.goToEventListScreen();
//                    // TODO: 27.03.2019 constructor + date for filter
//                }))
//                .setNegativeButton("Cancel",null)
//                .setCancelable(true)
//                .create()
//                .show();
    }
}
