package com.kor.foodmanager.di.notification;

import com.kor.foodmanager.ui.notificationList.NotificationListPresenter;

import dagger.Subcomponent;

@Subcomponent (modules = {NotificationModule.class})
@NotificationScope
public interface NotificationComponent {
    void inject(NotificationListPresenter presenter);
}
