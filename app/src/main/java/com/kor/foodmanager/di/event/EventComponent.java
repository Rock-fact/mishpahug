package com.kor.foodmanager.di.event;

import com.kor.foodmanager.ui.addEvent.AddEventPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {EventModule.class})
@EventScope
public interface EventComponent {
    void inject(AddEventPresenter addEventPresenter);
}
