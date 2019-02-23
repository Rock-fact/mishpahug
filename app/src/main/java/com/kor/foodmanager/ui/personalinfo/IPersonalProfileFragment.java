package com.kor.foodmanager.ui.personalinfo;

import com.arellomobile.mvp.MvpView;
import com.kor.foodmanager.data.model.StaticfieldsDto;

public interface IPersonalProfileFragment extends MvpView {

    void updateStaticFields(StaticfieldsDto staticFields);

}
