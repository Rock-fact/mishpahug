package com.kor.foodmanager.ui.contactinfo;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface IContactInfoFragment extends MvpView {
    void showEmailError(String error);
    void showPasswordError(String error);
}
