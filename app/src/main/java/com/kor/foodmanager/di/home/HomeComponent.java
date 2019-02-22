package com.kor.foodmanager.di.home;

import com.kor.foodmanager.ui.addEvent.AddEventPresenter;
import com.kor.foodmanager.ui.home.HomePresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {HomeModule.class})
@HomeScope
public interface HomeComponent {
    void inject(HomePresenter presenter);
    void inject(AddEventPresenter addEventPresenter);
}
