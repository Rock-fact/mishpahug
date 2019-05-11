package com.kor.foodmanager.ui.eventInfo.myEventInfoPending;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;


@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface IMyEventInfoPending extends MvpView {
    void showProgressFrame();
    void hideProgressFrame();
}
