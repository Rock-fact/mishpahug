package com.kor.foodmanager.ui.aboutmyself;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kor.foodmanager.data.model.StaticfieldsDto;


@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface IAboutMyselfFragment extends MvpView {
    void updateStaticFields(StaticfieldsDto staticFields);
    void showError(String error);
    void showProgressFrame();
    void hideProgressFrame();
}
