package com.kor.foodmanager.di.application;

import com.kor.foodmanager.di.event.EventComponent;
import com.kor.foodmanager.di.event.EventModule;
import com.kor.foodmanager.di.login.LoginComponent;
import com.kor.foodmanager.di.login.LoginModule;
import com.kor.foodmanager.ui.MainActivityPresenter;
import com.kor.foodmanager.ui.aboutmyself.AboutMyselfPresenter;
import com.kor.foodmanager.ui.contactinfo.ContactInfoPresenter;
import com.kor.foodmanager.ui.personalinfo.PersonalProfilePresenter;
import com.kor.foodmanager.ui.registration.RegistrationPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {MainModule.class})
@Singleton
public interface MainComponent{
    void inject(MainActivityPresenter presenter);
    LoginComponent plus(LoginModule module);
    EventComponent plus(EventModule module);
    void inject(RegistrationPresenter presenter);
    void inject(PersonalProfilePresenter presenter);
    void inject(AboutMyselfPresenter presenter);
    void inject(ContactInfoPresenter presenter);
}
