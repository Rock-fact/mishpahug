package com.kor.foodmanager.ui.eventList;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.data.model.EventsInProgressRequestDto;
import com.kor.foodmanager.data.userData.IUserDataRepository;
import com.kor.foodmanager.ui.MainActivity;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class FiltersPresenter extends MvpPresenter<IFilters> {
    @Inject Router router;
    @Inject IUserDataRepository dataRepository;
    private EventsInProgressRequestDto filters;

    public void apply(){
        if (filters!=null) {
            router.navigateTo(MainActivity.EVENT_LIST_SCREEN, filters);
        } else {
            router.navigateTo(MainActivity.EVENT_LIST_SCREEN);
        }
    }
}
