package com.kor.foodmanager.ui;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.data.auth.IAuthRepository;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import static com.kor.foodmanager.ui.MainActivity.HOME_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.LOGIN_SCREEN;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<IMain> {
    @Inject Router router;
    @Inject NavigatorHolder navigatorHolder;
    @Inject IAuthRepository authRepository;

    public MainActivityPresenter(){
        App.get().mainComponent().inject(this);
    }

    public void startWork() {
        if(authRepository.getToken()==null){
            showLoginScreen();
        }else {
            showHomeScreen();
        }
    }

    public void showLoginScreen(){
        router.navigateTo(LOGIN_SCREEN);
    }

    public void showHomeScreen(){
        router.navigateTo(HOME_SCREEN);
    }

    public void setNavigator(Navigator navigator) {
        navigatorHolder.setNavigator(navigator);
    }

    public void removeNavigator() {
        navigatorHolder.removeNavigator();
    }

}
