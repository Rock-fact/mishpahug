package com.kor.foodmanager.ui.home;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.buissness.home.IHomeInteractor;
import javax.inject.Inject;
import ru.terrakok.cicerone.Router;
import static com.kor.foodmanager.ui.MainActivity.LOGIN_SCREEN;

@InjectViewState
public class HomePresenter extends MvpPresenter<IHome> {
    @Inject IHomeInteractor interactor;
    @Inject Router router;

    public HomePresenter(){
        App.get().homeComponent().inject(this);
    }

    public void startWork(){
        String name = interactor.getName();
        getViewState().showHello(name);
    }

    @Override
    public void onDestroy() {
        App.get().clearHomeComponent();
        super.onDestroy();
    }

    public void logout() {
        interactor.logout();
        router.navigateTo(LOGIN_SCREEN);
    }

}
