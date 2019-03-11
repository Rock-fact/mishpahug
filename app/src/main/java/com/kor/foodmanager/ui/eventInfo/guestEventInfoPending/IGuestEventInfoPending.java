package com.kor.foodmanager.ui.eventInfo.guestEventInfoPending;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface IGuestEventInfoPending extends MvpView {
    void showProgressFrame();
    void hideProgressFrame();
}
