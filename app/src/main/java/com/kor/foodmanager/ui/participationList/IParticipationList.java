package com.kor.foodmanager.ui.participationList;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface IParticipationList extends MvpView {
    void showProgressFrame();
    void hideProgressFrame();
}
