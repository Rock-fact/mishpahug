package com.kor.foodmanager.ui.login;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface ILogin extends MvpView {
    void showError(String error);
    void showEmailError(String error);
    void showPasswordError(String error);
    void showProgressFrame();
    void hideProgressFrame();
}
