package com.kor.foodmanager.ui.eventInfo.guestEventInfo;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface IGuestEventInfo extends MvpView {
    void showProgressFrame();
    void hideProgressFrame();
    void showToast(String s);
    void hideJoinBtn();
}
