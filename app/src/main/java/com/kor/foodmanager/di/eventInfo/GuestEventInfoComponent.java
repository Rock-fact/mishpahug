package com.kor.foodmanager.di.eventInfo;

import com.kor.foodmanager.ui.eventInfo.guestEventInfo.GuestEventInfoPresenter;
import com.kor.foodmanager.ui.eventInfo.guestEventInfoDone.GuestEventInfoDonePresenter;
import com.kor.foodmanager.ui.eventInfo.guestEventInfoInprogress.GuestEventInfoInprogressPresenter;
import com.kor.foodmanager.ui.eventList.EventListPresenter;

import dagger.Subcomponent;

@Subcomponent (modules = {GuestEventInfoModule.class})
@GuestEventInfoScope
public interface GuestEventInfoComponent {
    void inject(GuestEventInfoPresenter guestEventInfoPresenter);
    void inject(EventListPresenter eventListPresenter);
    void inject(GuestEventInfoInprogressPresenter guestEventInfoInprogressPresenter);
    void inject(GuestEventInfoDonePresenter guestEventInfoDonePresenter);
}
