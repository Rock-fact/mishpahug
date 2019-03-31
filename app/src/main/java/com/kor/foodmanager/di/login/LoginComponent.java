package com.kor.foodmanager.di.login;

import com.kor.foodmanager.buissness.login.ILoginInteractor;
import com.kor.foodmanager.data.login.ILoginRepository;
import com.kor.foodmanager.ui.aboutmyself.AboutMyselfPresenter;
import com.kor.foodmanager.ui.contactinfo.ContactInfoPresenter;
import com.kor.foodmanager.ui.login.LoginPresenter;
import com.kor.foodmanager.ui.registration.RegistrationPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {LoginModule.class})
@LoginScope
public interface LoginComponent {
    void inject(LoginPresenter presenter);
    ILoginInteractor getLoginInteractor();
}
