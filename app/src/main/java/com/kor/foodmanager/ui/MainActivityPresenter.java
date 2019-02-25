package com.kor.foodmanager.ui;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.data.auth.IAuthRepository;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

import static com.kor.foodmanager.ui.MainActivity.EVENT_LIST_SCREEN;
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
            showEventListScreen();
        }
    }

    public void showLoginScreen(){
        router.navigateTo(LOGIN_SCREEN);
    }

    public void showEventListScreen(){
        router.navigateTo(EVENT_LIST_SCREEN);
    }

    public void setNavigator(Navigator navigator) {
        navigatorHolder.setNavigator(navigator);
    }

    public void removeNavigator() {
        navigatorHolder.removeNavigator();
    }

    public void eventList() {
        router.navigateTo(EVENT_LIST_SCREEN);
    }

    public void notice(){
        router.showSystemMessage("notice screen");
    }

    public void calendar(){
        router.showSystemMessage("calendar screen");
    }

    public void participation(){
        router.showSystemMessage("participations screen");
    }

    public void myProfile(){
        router.showSystemMessage("profile screen");
    }

    public void myEventList(){
        router.showSystemMessage("my event list");
    }

    public void logOut(){
        authRepository.clearToken();
        router.newRootScreen(LOGIN_SCREEN);
    }

}
