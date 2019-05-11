package com.kor.foodmanager.di.MyEventInProgress;

import com.kor.foodmanager.ui.eventInfo.myEventInfoInProgress.MyEventInfoInProgressPresenter;

import dagger.Subcomponent;

@MyEventInProgressScope
@Subcomponent(modules = MyEventInProgressModule.class)
public interface MyEventInProgressComponent {
    void inject(MyEventInfoInProgressPresenter presenter);
}
