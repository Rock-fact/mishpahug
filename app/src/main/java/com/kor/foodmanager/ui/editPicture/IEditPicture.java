package com.kor.foodmanager.ui.editPicture;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface IEditPicture extends MvpView {
    void showProgressFrame();
    void hideProgressFrame();
    void loadAvatarPicture(String uri);
    void loadEvenerBannerPicture(String uri);
    void loadImages();
}
