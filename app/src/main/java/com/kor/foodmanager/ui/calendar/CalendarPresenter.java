package com.kor.foodmanager.ui.calendar;

import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.calendar.ICalendarInteractor;
import com.kor.foodmanager.data.event.ServerException;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.EventListDto;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

import static com.kor.foodmanager.ui.MainActivity.GUEST_EVENT_INFO_DONE_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.GUEST_EVENT_INFO_INPROGRESS_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.GUEST_EVENT_INFO_PENDING_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.MY_EVENT_INFO_DONE_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.MY_EVENT_INFO_INPROGRESS_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.MY_EVENT_INFO_PENDING_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.TAG;

@InjectViewState
public class CalendarPresenter extends MvpPresenter<ICalendar> {
    @Inject
    ICalendarInteractor interactor;
    @Inject
    Router router;
    private Collection<CalendarDay> myEvents = new HashSet<>();
    private Collection<CalendarDay> subscribedEvents = new HashSet<>();
    HashMap<CalendarDay,EventDto> eventsMap = new HashMap<>();

    public CalendarPresenter() {
        App.get().calendarComponent().inject(this);
    }

    public void showMonth(int month) {
        new GetEventsForCalendarTask(month+1).execute();
    }

    @Override
    public void onDestroy() {
        App.get().clearCalendarComponent();
        super.onDestroy();
    }

    public void onDateSelected(CalendarDay date) {
        if(myEvents.contains(date)){
            EventDto event = eventsMap.get(date);
            switch (event.getStatus()){
                case "Pending":
                    router.navigateTo(MY_EVENT_INFO_PENDING_SCREEN, event);
                    break;
                case "In progress":
                    router.navigateTo(MY_EVENT_INFO_INPROGRESS_SCREEN, event);
                    break;
                case "Done":
                    router.navigateTo(MY_EVENT_INFO_DONE_SCREEN, event);
                    break;
            }
        }else if(subscribedEvents.contains(date)){
            EventDto event = eventsMap.get(date);
            switch (event.getStatus()){
                case "Pending":
                    router.navigateTo(GUEST_EVENT_INFO_PENDING_SCREEN, event);
                    break;
                case "In progress":
                    router.navigateTo(GUEST_EVENT_INFO_INPROGRESS_SCREEN, event);
                    break;
                case "Done":
                    router.navigateTo(GUEST_EVENT_INFO_DONE_SCREEN, event);
                    break;
            }
        }else {
            router.showSystemMessage("Empty day!");
        }
    }

    private class GetEventsForCalendarTask extends AsyncTask<Void, Void, EventListDto> {
        private int month;
        private Boolean isSuccess;
        private String error;

        public GetEventsForCalendarTask(int month) {
            this.month = month;
        }

        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
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
                List<EventDto> myEventsDto = res.getMyEvents();
                List<EventDto> subscribedEventsDto = res.getSubscribedEvents();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                for (EventDto eventDto :
                        myEventsDto) {
                    try {
                        CalendarDay day = CalendarDay.from(format.parse(eventDto.getDate()));
                        myEvents.add(day);
                        eventsMap.put(day,eventDto);
                    } catch (ParseException e) {
                        router.showSystemMessage(e.getMessage());
                    }
                }

                for (EventDto eventDto :
                        subscribedEventsDto) {
                    try {
                        subscribedEvents.add(CalendarDay.from(format.parse(eventDto.getDate())));
                        Log.d(TAG, "subscribedEvent: "+eventDto.getDate());
                    } catch (ParseException e) {
                        Log.d(TAG, "parse error: " + e.getMessage());
                        router.showSystemMessage(e.getMessage());
                    }
                }
                getViewState().showCalendar(myEvents, subscribedEvents);
            }else {
                router.showSystemMessage(error);
            }
        }
    }
}
