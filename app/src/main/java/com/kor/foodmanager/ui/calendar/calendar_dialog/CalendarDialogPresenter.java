package com.kor.foodmanager.ui.calendar.calendar_dialog;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;

import java.util.Calendar;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

import static com.kor.foodmanager.ui.MainActivity.ADD_EVENT_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.EVENT_LIST_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.MY_EVENT_LIST_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.PARTICIPATION_LIST_SCREEN;

@InjectViewState
public class CalendarDialogPresenter extends MvpPresenter<ICalendarDialog> {
    @Inject
    Router router;

    public CalendarDialogPresenter(){
        App.get().calendarComponent().inject(this);
    }

    @Override
    public void onDestroy() {
        App.get().clearCalendarComponent();
        super.onDestroy();
    }

    public void showEvents() {
        router.replaceScreen(EVENT_LIST_SCREEN);
    }

    public void showMyEvents() {
        router.replaceScreen(MY_EVENT_LIST_SCREEN);
    }

    public void showMySubs() {
        router.replaceScreen(PARTICIPATION_LIST_SCREEN);
    }

    public void addNewEvent(Calendar date) {
        router.replaceScreen(ADD_EVENT_SCREEN,date);
        // TODO: 30.03.2019 test behavior of toolbar in this case 
    }
}
