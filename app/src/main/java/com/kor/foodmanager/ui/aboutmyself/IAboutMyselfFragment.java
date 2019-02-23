package com.kor.foodmanager.ui.aboutmyself;

import com.arellomobile.mvp.MvpView;
import com.kor.foodmanager.data.model.StaticfieldsDto;

public interface IAboutMyselfFragment extends MvpView {
    void updateStaticFields(StaticfieldsDto staticFields);
}
