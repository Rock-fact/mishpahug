package com.kor.foodmanager.ui.notificationInfo;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.notification.INotificationInteractor;

import javax.inject.Inject;

@InjectViewState
public class NotificationInfoPresenter extends MvpPresenter<INotificationInfo>{
    @Inject INotificationInteractor interactor;

    public NotificationInfoPresenter() {
        App.get().notificationComponent().inject(this);
    }

    @Override
    public void onDestroy() {
        App.get().clearNotificationComponent();
        super.onDestroy();
    }

    public void startWork() {
        // TODO: 02.03.2019 put here asyncTask
    }
}
