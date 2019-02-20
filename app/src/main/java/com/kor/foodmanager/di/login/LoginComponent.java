package com.kor.foodmanager.di.login;

import com.kor.foodmanager.ui.login.LoginPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {LoginModule.class})
@LoginScope
public interface LoginComponent {
    void inject(LoginPresenter presenter);
}
