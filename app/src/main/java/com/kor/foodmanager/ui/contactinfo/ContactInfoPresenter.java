package com.kor.foodmanager.ui.contactinfo;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.ui.MainActivity;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class ContactInfoPresenter extends MvpPresenter<IContactInfoFragment> {

    @Inject
    Router router;

    public ContactInfoPresenter(){
        App.get().mainComponent().inject(this);
    }

    public void startAboutMyself(UserDto user){
        router.navigateTo(MainActivity.ABOUTMYSELF_FRAGMENT_NEW, user);
    }
}
