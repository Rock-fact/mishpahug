package com.kor.foodmanager.ui.eventInfo.guestEventInfoInprogress;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface IGuestEventInfoInprogress extends MvpView {
    void showProgressFrame();
    void hideProgressFrame();
    void showDialogToUnsubscribe();
}
