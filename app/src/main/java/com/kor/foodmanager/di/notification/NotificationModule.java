package com.kor.foodmanager.di.notification;

import com.google.gson.Gson;
import com.kor.foodmanager.buissness.notification.INotificationInteractor;
import com.kor.foodmanager.buissness.notification.NotificationInteractor;
import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.data.notification.INotificationRepository;
import com.kor.foodmanager.data.notification.NotificationRepository;
import com.kor.foodmanager.data.provider.web.Api;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationModule {
    @Provides @NotificationScope
    INotificationInteractor provideNotificationInteractor(INotificationRepository notificationRepository, IAuthRepository authRepository){
        return new NotificationInteractor(notificationRepository, authRepository);
    }

    @Provides @NotificationScope
    INotificationRepository provideNotificationRepository(Api api, Gson gson){
        return new NotificationRepository(api, gson);
    }
}
