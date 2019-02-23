package com.kor.foodmanager.ui;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

// TODO: 23.02.2019 think about strategies
@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface IMain extends MvpView {
    void showProgressFrame();
    void hideProgressFrame();
    void showError(String error);
}
