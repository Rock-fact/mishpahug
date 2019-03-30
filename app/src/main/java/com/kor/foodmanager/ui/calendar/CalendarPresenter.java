package com.kor.foodmanager.ui.calendar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.calendar.ICalendarInteractor;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.EventListDto;
import com.kor.foodmanager.data.model.HebcalDto;
import com.kor.foodmanager.data.model.HebcalItemDto;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

import static com.kor.foodmanager.ui.MainActivity.ADD_EVENT_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.EVENT_LIST_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.GUEST_EVENT_INFO_DONE_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.GUEST_EVENT_INFO_INPROGRESS_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.GUEST_EVENT_INFO_PENDING_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.MY_EVENT_INFO_DONE_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.MY_EVENT_INFO_INPROGRESS_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.MY_EVENT_INFO_PENDING_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.MY_EVENT_LIST_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.PARTICIPATION_LIST_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.TAG;

@InjectViewState
public class CalendarPresenter extends MvpPresenter<ICalendar> {
    @Inject
    ICalendarInteractor interactor;
    @Inject
    Router router;
    private Collection<CalendarDay> myEvents = new HashSet<>();
    private Collection<CalendarDay> subscribedEvents = new HashSet<>();
    List<EventDto> myEventsDto;
    List<EventDto> subscribedEventsDto;
    HashMap<CalendarDay, EventDto> eventsMap = new HashMap<>();
    private List<HebcalItemDto> isrHolidaysList;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    public CalendarPresenter() {
        App.get().calendarComponent().inject(this);
    }

    public void showMonth(int month) {
        getViewState().showProgressFrame();
        new GetEventsForCalendarTask(month + 1).execute();
        new GetIsrHolidays(month + 1).execute();
        getViewState().addCalendarListener();
    }

    @Override
    public void onDestroy() {
        App.get().clearCalendarComponent();
        super.onDestroy();
    }

    public void onDateSelected(CalendarDay date) {
        String message = "Today is: ";
        String stringDate = format.format(date.getDate());
        Log.d(TAG, "dateSelectedDialog: " + stringDate);
        for (HebcalItemDto itemDto : isrHolidaysList
        ) {
            Log.d(TAG, "isr holiday: " + itemDto.getDate() + ", " + itemDto.getMemo());
            if (itemDto.getDate().equals(stringDate)) {
                Log.d(TAG, "dateSelectedDialog: " + itemDto.getMemo());
                message = message + itemDto.getMemo();
            }
        }
        if (message.equals("Today is: ")) {
            message = "No holidays today";
        }
        getViewState().showDateDialog(date, stringDate, message, myEventsDto, subscribedEventsDto);
    }

    public void goToAddEventScreen(Calendar calendar) {
        router.navigateTo(ADD_EVENT_SCREEN, calendar);
    }

    public void goToEventListScreen() {
        router.navigateTo(EVENT_LIST_SCREEN);
    }

    private class GetEventsForCalendarTask extends AsyncTask<Void, Void, EventListDto> {
        private int month;
        private Boolean isSuccess;
        private String error;

        public GetEventsForCalendarTask(int month) {
            this.month = month;
        }

        @Override
        protected EventListDto doInBackground(Void... voids) {
            EventListDto res = null;
            try {
                res = interactor.getEventsForCalendar(month);
                isSuccess = true;
            } catch (IOException e) {
                error = "Connection failed!";
                isSuccess = false;
            } catch (ServerException e) {
                error = e.getMessage();
                isSuccess = false;
            }
            return res;
        }

        @Override
        protected void onPostExecute(EventListDto res) {
            getViewState().hideProgressFrame();

            if (isSuccess) {
                myEventsDto = res.getMyEvents();
                subscribedEventsDto = res.getSubscribedEvents();

                // TODO: 30.03.2019 kostil, remove after Dimas backend fix
                if(myEventsDto == null){
                    myEventsDto = new ArrayList<>();
                }
                if(subscribedEventsDto == null){
                    subscribedEventsDto = new ArrayList<>();
                }

                for (EventDto eventDto :
                        subscribedEventsDto) {
                    try {
                        subscribedEvents.add(CalendarDay.from(format.parse(eventDto.getDate())));
                        Log.d(TAG, "subscribedEvent: " + eventDto.getDate());
                    } catch (ParseException e) {
                        Log.d(TAG, "parse error: " + e.getMessage());
                        router.showSystemMessage(e.getMessage());
                    }
                }

                for (EventDto eventDto :
                        myEventsDto) {
                    try {
                        CalendarDay day = CalendarDay.from(format.parse(eventDto.getDate()));
                        myEvents.add(day);
                        Log.d(TAG, "myEvent: " + eventDto.getDate());
                        eventsMap.put(day, eventDto);
                    } catch (ParseException e) {
                        router.showSystemMessage(e.getMessage());
                    }
                }

                getViewState().showCalendar(myEvents, subscribedEvents);
            } else {
                router.showSystemMessage(error);
            }
        }
    }

    private class GetIsrHolidays extends AsyncTask<Void, Void, HebcalDto> {
        private int month;
        private Boolean isSuccess;
        private String error;

        public GetIsrHolidays(int month) {
            this.month = month;
        }

        @Override
        protected HebcalDto doInBackground(Void... voids) {
            HebcalDto res = null;
            try {
                res = interactor.getIsrHolidays(month);
                isSuccess = true;
            } catch (IOException e) {
                error = "Connection failed!";
                isSuccess = false;
            }
            return res;
        }

        @Override
        protected void onPostExecute(HebcalDto res) {
            getViewState().hideProgressFrame();
            if (isSuccess) {
                isrHolidaysList = res.getItems();
                Log.d(TAG, "onPostExecute: " + isrHolidaysList.size());
                for (HebcalItemDto itemDto : isrHolidaysList
                ) {
                    Log.d(TAG, "holiday : " + itemDto.getDate() + itemDto.getMemo());
                }
                getViewState().decorateCalendar(res);
            } else {
                router.showSystemMessage(error);
            }
        }
    }
}
