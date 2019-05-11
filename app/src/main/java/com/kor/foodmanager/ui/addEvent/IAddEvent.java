package com.kor.foodmanager.ui.addEvent;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface IAddEvent extends MvpView {
    void setHolidaySpinner(List<String> holiday);
    void showSuccess();
}
