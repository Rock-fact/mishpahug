package com.kor.foodmanager.ui.eventInfo.myEventInfoInProgress;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;


@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface IMyEventInfoInProgress extends MvpView {
    void showProgressFrame();
    void hideProgressFrame();
}
