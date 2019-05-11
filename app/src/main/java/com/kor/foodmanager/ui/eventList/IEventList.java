package com.kor.foodmanager.ui.eventList;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kor.foodmanager.data.model.EventDto;

import java.util.List;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface IEventList extends MvpView {
    void showProgressFrame();

    void hideProgressFrame();

    void swipeRefresh();
    void isLastPage(boolean isLast);
    void isLoading();

}
