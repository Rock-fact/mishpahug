package com.kor.foodmanager.ui.contactinfo;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.login.ILoginInteractor;
import com.kor.foodmanager.buissness.login.validator.EmailValidException;
import com.kor.foodmanager.buissness.login.validator.PasswordValidException;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.ui.MainActivity;
import com.kor.foodmanager.ui.login.LoginPresenter;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class ContactInfoPresenter extends MvpPresenter<IContactInfoFragment> {

    @Inject
    Router router;

    ILoginInteractor interactor;

    public ContactInfoPresenter(){
        App.get().mainComponent().inject(this);
        interactor=App.get().loginComponent().getLoginInteractor();
    }

    public void startAboutMyself(UserDtoWithEmail user){
        router.navigateTo(MainActivity.ABOUTMYSELF_FRAGMENT_NEW, user);
    }
    public void validate(String email, String password) throws EmailValidException, PasswordValidException {
            interactor.validate(email,password);
    }
    public void validate(String email) throws EmailValidException, PasswordValidException {
        interactor.validate(email);
    }
}
