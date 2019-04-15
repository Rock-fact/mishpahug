package com.kor.foodmanager.ui.personalinfo;

import com.arellomobile.mvp.MvpView;
import com.kor.foodmanager.data.model.StaticfieldsDto;

import java.util.List;

public interface IPersonalProfileFragment extends MvpView {

    void updateStaticFields(StaticfieldsDto staticFields);

    void setUserPics(List<String> notLoadedUriList);
}
