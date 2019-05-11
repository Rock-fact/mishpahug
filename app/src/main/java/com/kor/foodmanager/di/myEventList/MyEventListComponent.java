package com.kor.foodmanager.di.myEventList;

import com.kor.foodmanager.ui.myEventList.MyEventListPresenter;

import dagger.Subcomponent;

@MyEventListScope
@Subcomponent(modules = {MyEventListModule.class})
public interface MyEventListComponent {
    void inject(MyEventListPresenter presenter);
}
