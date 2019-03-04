package com.kor.foodmanager.di.eventInfo;

import com.kor.foodmanager.ui.eventInfo.guestEventInfo.GuestEventInfoPresenter;

import dagger.Subcomponent;

@Subcomponent (modules = {GuestEventInfoModule.class})
@GuestEventInfoScope
public interface GuestEventInfoComponent {
    void inject(GuestEventInfoPresenter guestEventInfoPresenter);
}
