package com.kor.foodmanager.ui.eventInfo.guestEventInfoDone;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface IGuestEventInfoDone extends MvpView {
    void showProgressFrame();
    void hideProgressFrame();
    void hideVoteBtn();
    void showVoteDialog(long eventId);
}
