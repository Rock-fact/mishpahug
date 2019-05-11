package com.kor.foodmanager.di.application;

import com.kor.foodmanager.di.MyEventInProgress.MyEventInProgressComponent;
import com.kor.foodmanager.di.MyEventInProgress.MyEventInProgressModule;
import com.kor.foodmanager.di.calendar.CalendarComponent;
import com.kor.foodmanager.di.calendar.CalendarModule;
import com.kor.foodmanager.di.event.EventComponent;
import com.kor.foodmanager.di.event.EventModule;
import com.kor.foodmanager.di.eventInfo.GuestEventInfoComponent;
import com.kor.foodmanager.di.eventInfo.GuestEventInfoModule;
import com.kor.foodmanager.di.login.LoginComponent;
import com.kor.foodmanager.di.login.LoginModule;
import com.kor.foodmanager.di.myEventList.MyEventListComponent;
import com.kor.foodmanager.di.myEventList.MyEventListModule;
import com.kor.foodmanager.di.notification.NotificationComponent;
import com.kor.foodmanager.di.notification.NotificationModule;
import com.kor.foodmanager.di.participationList.ParticipationListComponent;
import com.kor.foodmanager.di.participationList.ParticipationListModule;
import com.kor.foodmanager.ui.MainActivityPresenter;
import com.kor.foodmanager.ui.aboutmyself.AboutMyselfPresenter;
import com.kor.foodmanager.ui.contactinfo.ContactInfoPresenter;
import com.kor.foodmanager.ui.editPicture.EditPicturePresenter;
import com.kor.foodmanager.ui.eventInfo.guestEventInfo.GuestEventInfoPresenter;
import com.kor.foodmanager.ui.eventInfo.myEventInfoPending.MyEventInfoPendingPresenter;
import com.kor.foodmanager.ui.eventList.EventListPresenter;
import com.kor.foodmanager.ui.eventList.FiltersPresenter;
import com.kor.foodmanager.ui.myEventList.MyEventListPresenter;
import com.kor.foodmanager.ui.myProfile.MyProfilePresenter;
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
    NotificationComponent plus(NotificationModule module);
    GuestEventInfoComponent plus(GuestEventInfoModule module);
    void inject(RegistrationPresenter presenter);
    void inject(PersonalProfilePresenter presenter);
    void inject(AboutMyselfPresenter presenter);
    void inject(ContactInfoPresenter presenter);
    void inject(EventListPresenter presenter);
    void inject(MyEventInfoPendingPresenter presenter);
    void inject(FiltersPresenter presenter);
    void inject(MyProfilePresenter presenter);
    ParticipationListComponent plus(ParticipationListModule participationListModule);
    MyEventListComponent plus(MyEventListModule myEventListModule);
    MyEventInProgressComponent plus(MyEventInProgressModule myEventInProgressModule);
    CalendarComponent plus(CalendarModule module);
    void inject(EditPicturePresenter editPicturePresenter);
}
