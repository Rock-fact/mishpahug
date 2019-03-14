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
import static com.kor.foodmanager.ui.MainActivity.MY_EVENT_LIST_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.MY_PROFILE_FRAGMENT_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.NOTIFICATIONS_SCREEN;
import static com.kor.foodmanager.ui.MainActivity.PARTICIPATION_LIST_SCREEN;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<IMain> {
    @Inject Router router;
    @Inject NavigatorHolder navigatorHolder;
    @Inject IAuthRepository authRepository;
    private Boolean isStarted = false;

    public MainActivityPresenter(){
        App.get().mainComponent().inject(this);
    }

    public void startWork() {
        if(isStarted == false){
            if(authRepository.getToken()==null){
                isStarted = true;
                showLoginScreen();
            }else {
                isStarted = true;
                showEventListScreen();
            }
        }
    }

    public void showLoginScreen(){
        router.newRootScreen(LOGIN_SCREEN);
    }

    public void showEventListScreen(){
        router.newRootScreen(EVENT_LIST_SCREEN);
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
        router.navigateTo(NOTIFICATIONS_SCREEN);
    }

    public void calendar(){
        router.showSystemMessage("calendar screen");
    }

    public void participation(){
        router.navigateTo(PARTICIPATION_LIST_SCREEN);
    }

    public void myProfile(){
        router.navigateTo(MY_PROFILE_FRAGMENT_SCREEN,authRepository.getUser());
    }

    public void myEventList(){
        router.navigateTo(MY_EVENT_LIST_SCREEN);
    }

    public void logOut(){
        authRepository.clearToken();
        router.newRootScreen(LOGIN_SCREEN);
    }

}
