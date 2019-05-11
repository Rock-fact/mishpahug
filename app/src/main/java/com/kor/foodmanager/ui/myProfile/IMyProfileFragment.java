package com.kor.foodmanager.ui.myProfile;

import com.arellomobile.mvp.MvpView;
import com.kor.foodmanager.data.model.StaticfieldsDto;

public interface IMyProfileFragment extends MvpView {
    void updateStaticFields(StaticfieldsDto staticFields);
    void viewMode();
    void inputMode();
    void fillInput();
    void fillView();
    void hideProgressBar();
    void showProgressBar();
}
