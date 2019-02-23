package com.kor.foodmanager;

import android.app.Application;


import com.kor.foodmanager.di.application.DaggerMainComponent;
import com.kor.foodmanager.di.application.MainComponent;
import com.kor.foodmanager.di.application.MainModule;
import com.kor.foodmanager.di.home.HomeComponent;
import com.kor.foodmanager.di.home.HomeModule;
import com.kor.foodmanager.di.login.LoginComponent;
import com.kor.foodmanager.di.login.LoginModule;

public class App extends Application {
    private static App app;
    private MainComponent mainComponent;
    private LoginComponent loginComponent;
    private HomeComponent homeComponent;

    public App(){
        app = this;
    }

    public static App get(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mainComponent = DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build();

    }

    public MainComponent mainComponent(){
        return mainComponent;
    }

    public LoginComponent loginComponent(){
        if(loginComponent == null){
            loginComponent = mainComponent.plus(new LoginModule());
        }
        return loginComponent;
    }

    public void clearLoginComponent(){
        loginComponent = null;
    }

    public HomeComponent homeComponent(){
        if(homeComponent == null){
            homeComponent = mainComponent.plus(new HomeModule());
        }
        return homeComponent;
    }

    public void clearHomeComponent(){
        homeComponent = null;
    }
}
