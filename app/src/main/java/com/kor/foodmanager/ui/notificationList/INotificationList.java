package com.kor.foodmanager.ui.notificationList;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface INotificationList extends MvpView {
    void showNotificationList(NotificationListAdapter adapter);
    void showProgressFrame();
    void hideProgressFrame();
}
